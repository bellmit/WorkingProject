package com.smart.homewifi.mesh.gatewaystate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.RateLimiter;
import com.smart.homewifi.mesh.config.BaseConfig;
import com.smart.homewifi.mesh.config.EsConfig;
import com.smart.homewifi.mesh.es.ElasticSearchOperations;
import com.smart.homewifi.mesh.es.EsUtils;
import com.smart.homewifi.mesh.es.JsonView;
import com.smart.homewifi.mesh.scp.ScpTransfer;
import com.smart.homewifi.mesh.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author:Z
 * @Data:2021/11/15 13:48
 * @Version:1.0
 */
@Component
public class GatewayMesh {

    @Autowired
    private EsConfig esConfig;
    @Autowired
    private BaseConfig baseConfig;

    //每秒只发出100个令牌
    private RateLimiter rateLimiter = RateLimiter.create(100.0);
    private SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static final Logger logger = LoggerFactory.getLogger(GatewayMesh.class);
    private final static String EOL = System.getProperty("line.separator");

    public void getGatewayMesh(){
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(baseConfig.getCorePoolSize(), baseConfig.getCorePoolSize()+10,
                2, TimeUnit.SECONDS, new LinkedBlockingQueue<>(5000));
        //上传标志位
        int updloadFlag = 0;
        //游标
        String scrollId = null;
        //单次scroll查询结果
        JsonView jsonView = null;
        while(true){
            try {
                if(TimeUtils.isDayOfMonth(20)){
                    Date changeDate = new Date();
                    logger.info("本月{}修改上传标志位为0",dateFormat.format(changeDate));
                    updloadFlag = 0;
                }
                if(TimeUtils.isDayOfMonth(baseConfig.getGatewayUpdateDay()) && updloadFlag ==0){
                    uploadFile();
                    updloadFlag = 1;
                }
                //使用scroll条件查询时间排序
                jsonView = scrollGatewayMac(scrollId);
                logger.info("查询本地库获取在线网关mac {}条",jsonView.getNumber());
                scrollId = jsonView.getScrollId();
                if(jsonView.getNumber() == 0){
                    Date reindexDate = new Date();
                    logger.info("{}，本轮本地库网关mac查询结束，开始reindex从线上gatewayonline动态库补充数据",dateFormat.format(reindexDate));
                    String taskId = reindexNewGateway();
                    while(!checkTask(taskId)){
                        Thread.sleep(6000);
                    }
                    scrollId = "";
                    jsonView = scrollGatewayMac(scrollId);
                    logger.info("reindex结束后，查询本地库获取在线网关mac {}条",jsonView.getNumber());
                    scrollId = jsonView.getScrollId();
                }
                for (JSONObject jsonObject :jsonView.getList()){
                    String macAddress = jsonObject.getString("MAC_ADDRESS");
                    //以阻塞形式运行，每10ms产生一个令牌，即允许每秒发送100个数据。
                    rateLimiter.acquire(1);
                    poolExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            //todo：后期优化：2、使用接口校验是否在线；3、申请并使用自己的appid；4、指定获取插件名请求的请求体中id随机
                            //开启多线程调用接口查询mesh状态
                           recordMesh(jsonObject);
                        }
                    });
                    while (poolExecutor.getQueue().size() >= 1000){
                        Thread.sleep(1000);
                        logger.info("查询网关mac:{}休眠1秒后线程队列排队{}个,正在执行线程数{}条",macAddress,
                                poolExecutor.getQueue().size(),poolExecutor.getActiveCount());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 每月一号没有上传文件的情况下就上传
     */
    public void uploadFile(){
        Date upLoadDate = new Date();
        try {
            String scrollId = null;
            boolean flag = true;
            logger.info("{}网关mesh每月定时上传任务开始执行",dateFormat.format(upLoadDate));
            String filePath = baseConfig.getLocaldir()+"/GATEWAY_Mesh_State_"+ CalendarUtils.getDate()
                    +"_"+CalendarUtils.getLastMonth()+".txt";
            File gatewayMeshFile = new File(filePath);
            if(!gatewayMeshFile.exists()){
                try {
                    //作用是避免误数据写入时，没有文件夹压缩导致报错。
                    gatewayMeshFile.createNewFile();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            while(flag){
                //1、查询网关ES库
                JsonView jsonView = scrollGatewayMesh(scrollId);
                scrollId = jsonView.getScrollId();
                if(jsonView.getNumber() == 0){
                    flag = false;
                }else{
                    //2、分批写入文件
                    writeGatewayMeshForLine(gatewayMeshFile,jsonView.getList());
                }
            }
            //3、压缩文件、上传文件
            logger.info("开始压缩文件");
            ScpTransfer scpTransfer = new ScpTransfer();
            logger.info("开始scp上传文件");
            scpTransfer.scpUploadFile(FileZipUtile.GzipFile(filePath,""));
            logger.info("上传结束删除文件");
            gatewayMeshFile.delete();
            //5、删除原表所有数据、上传标志位置1
            String deleteaskId = deleteAllData();
            while(!checkTask(deleteaskId)){
                //reindex未完成就sleep;
                Thread.sleep(10000);
            }
            //6、reindex一张新表
            logger.info("上传结束，删除原表数据，reindex新表数据");
            String reindexTaskId = reindexNewGateway();
            while(!checkTask(reindexTaskId)){
                //reindex未完成就sleep;
                Thread.sleep(6000);
            }
        } catch (Exception e) {
            logger.error("{} 网关mesh状态文件上传出错",dateFormat.format(upLoadDate));
            e.printStackTrace();
        }
    }

    /**
     * 从动态库gatewayonline上补充数据到ES数据库
     */
    public String reindexNewGateway(){
        String reindexString  = "{\n" +
                "    \"conflicts\":\"proceed\",\n" +
                "    \"source\": {\n" +
                "        \"index\": \"gatewayonline\",\n" +
                "        \"size\": 10000,\n" +
                "        \"query\": {\n" +
                "            \"prefix\": {\n" +
                "                \"MIDDLEWARE_BASE_VER.keyword\": \"FW2.2\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"_source\": [\"MAC_ADDRESS\", \"ACCESS_TIME\"]\n" +
                "    },\n" +
                "    \"dest\": {\n" +
                "        \"index\": \"gatewayonline_copy\",\n" +
                "        \"op_type\": \"create\"\n" +
                "    }\n" +
                "}";
        String url = "http://"+esConfig.getEsAddress()+":"+esConfig.getEsPort()+
                "/_reindex?refresh&slices=5&wait_for_completion=false";
        ResponseEntity<JSONObject> responseEntity = EsUtils.query(url,reindexString);
        JSONObject taskIdObj=responseEntity.getBody();
        return (String) taskIdObj.get("task");
    }

    /**
     * 删除所有表数据
     */
    public String deleteAllData(){
        JSONObject queryRoot = new JSONObject();
        queryRoot.put("conflicts","proceed");
        queryRoot.put("query",newJSONObject("match_all",new JSONObject()));
        String url = "http://"+esConfig.getEsAddress()+":"+esConfig.getEsPort()+
                "/gatewayonline_copy/_delete_by_query?refresh&slices=5&wait_for_completion=false";
        JSONObject taskIdObj = HttpUtil.post(url, queryRoot);
        return (String) taskIdObj.get("task");
    }

    /**
     * 查看单个任务是否执行结束
     */
    public boolean checkTask(String taskId){
        String url = "http://"+esConfig.getEsAddress()+":"+esConfig.getEsPort()+"/_tasks/"+taskId;
        JSONObject reindexResult = HttpUtil.get(url);
        Boolean completed = (Boolean) reindexResult.get("completed");
        if(completed){
            JSONObject reindexStatus = reindexResult.getJSONObject("task").getJSONObject("status");
            logger.info("本次补充/删除网关数据：total:{}, created:{}",
                    reindexStatus.getString("total"),reindexStatus.getString("created"));
        }
        return completed;
    }

    /**
     * 使用scroll查询网关4.0复制表，获取mac用于接口调用
     * @param scrollId
     * @return
     */
    public JsonView scrollGatewayMac(String scrollId){
            String url="http://"+esConfig.getEsAddress()+":"+esConfig.getEsPort();
            JSONObject queryRoot = new JSONObject();
            if(EmptyUtil.isEmpty(scrollId)){
                url+="/gatewayonline_copy/_search?scroll=10m&size=10000";
                JSONObject boolString = new JSONObject();
                JSONArray mustNotString = new JSONArray();
                mustNotString.add(newJSONObject("exists",newJSONObject("field", "meshOpen")));
                mustNotString.add(newJSONObject("term",newJSONObject("meshSupport", 0)));
                boolString.put("must_not", mustNotString);
                queryRoot.put("query",newJSONObject("bool",boolString));
                queryRoot.put("_source", new String[]{"MAC_ADDRESS"});
                JSONArray sortString = new JSONArray();
                sortString.add(newJSONObject("ACCESS_TIME",newJSONObject("order", "desc")));
                queryRoot.put("sort",sortString);
            }else{
                url+="/_search/scroll?scroll=10m&scroll_id="+scrollId;
            }
        return handleScrollResult(url,queryRoot);
    }


    /**
     * 使用scroll查询获取网关mesh统计结果
     */
    public JsonView scrollGatewayMesh(String scrollId){
        String url="http://"+esConfig.getEsAddress()+":"+esConfig.getEsPort();
        JSONObject queryRoot = new JSONObject();
        if(EmptyUtil.isEmpty(scrollId)){
            url+="/gatewayonline_copy/_search?scroll=10m&size=10000";
            JSONObject boolString = new JSONObject();
            JSONArray mustString = new JSONArray();
            mustString.add(newJSONObject("exists",newJSONObject("field", "meshOpen")));
            boolString.put("must", mustString);
            queryRoot.put("query",newJSONObject("bool",boolString));
            queryRoot.put("_source", new String[]{"MAC_ADDRESS"});
        }else{
            url+="/_search/scroll?scroll=10m&scroll_id="+scrollId;
        }
        return handleScrollResult(url,queryRoot);
    }

    /**
     * 处理scroll查询结果
     * @param url
     * @param queryRoot
     * @return
     */
    public JsonView handleScrollResult(String url,JSONObject queryRoot){
        try {
            //发送http post请求
            JsonView jsonView = new JsonView();
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(queryRoot, headers);
            ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(url, entity, JSONObject.class);
            //获取查询结果
            JSONObject result = responseEntity.getBody();
            String scrollId = result.getString("_scroll_id");
            JSONArray jsonHits = result.getJSONObject("hits").getJSONArray("hits");
            for(int i=0;i<jsonHits.size();i++){
                JSONObject jsonobject = jsonHits.getJSONObject(i);
                JSONObject data= JSONObject.parseObject(jsonobject.getString("_source"));
                data.put("id",jsonobject.getString("_id"));
                jsonView.getList().add(data);
            }
            jsonView.setScrollId(scrollId);
            jsonView.setNumber((long)jsonHits.size());
            return jsonView;
        } catch (RestClientException e) {
            e.printStackTrace();
            logger.error("=====ES读取异常=====" + e);
            return null;
        }
    }

    public static  <V> Map newJSONObject(String key, V value){
        JSONObject jsonObject =new JSONObject();
        try {
            if(key!=null){
                jsonObject.put(key,value);
            }
        }catch (Exception e){
        }
        return jsonObject;
    }

    /**
     * 按行写文件，将操作日志list中的字符串，按行写入file_name文件中
     */
    public void writeGatewayMeshForLine(File file, List<JSONObject> list) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file,true));
            for ( JSONObject esObject: list ) {
                String openMeshMac = (String)esObject.get("MAC_ADDRESS");
                writer.write(openMeshMac + EOL);//按行写文件，后面追加行分隔符EOL
            }
            //关闭流
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("网关Mesh开启状态，写入出错");
        }finally {
            if ( writer != null ) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void recordMesh(JSONObject jsonObject){
        String macAddress = jsonObject.getString("MAC_ADDRESS");
        String accessTime = jsonObject.getString("ACCESS_TIME");
        //1、获取网关插件名称和版本号
        JSONObject plugeData = getPlugeData(macAddress);
        if(plugeData != null){
            //2、根据mac、Plugin_Name、Version三个参数获取网关mesh状态
            JSONObject meshJson = getMeshState(macAddress, plugeData.getString("Plugin_Name"), plugeData.getString("Version"));
            if(meshJson != null){
                //logger.info("mac为{}的网关查询结果为：{}",macAddress,meshJson.toJSONString());
                JSONObject gwMesh = meshJson.getJSONObject("gw");
                Integer meshSupport = gwMesh.getInteger("support");
                Integer meshOpen = gwMesh.getInteger("enable");
                if(meshSupport == 0){
                    //logger.info("mac为{}的网关不支持mesh",macAddress);
                    //给这一条数据添加不支持（不查）标志位。
                    /*String postUrl = "http://"+esConfig.getEsAddress()+":"+esConfig.getEsPort()+
                            "/gatewayonline_copy/messagedb/"+macAddress+"/_update";
                    String state = "{\"doc\":{\"meshSupport\": 0}}";
                    HttpUtil.post(postUrl,state);*/
                    JSONObject resultJson = new JSONObject();
                    resultJson.put("MAC_ADDRESS",macAddress);
                    resultJson.put("ACCESS_TIME",accessTime);
                    resultJson.put("meshSupport",0);
                    ElasticSearchOperations.bulkGwOnlineUpdate(resultJson,esConfig.getGwIndex(),esConfig.getGwType(),macAddress);
                }else if(meshSupport == 1 && meshOpen == 1){
                    logger.info("mac为{}的网关支持且开启mesh",macAddress);
                    JSONObject resultJson = new JSONObject();
                    resultJson.put("MAC_ADDRESS",macAddress);
                    resultJson.put("ACCESS_TIME",accessTime);
                    resultJson.put("meshSupport",1);
                    resultJson.put("meshOpen",1);
                    ElasticSearchOperations.bulkGwOnlineUpdate(resultJson,esConfig.getGwIndex(),esConfig.getGwType(),macAddress);
                }else if(meshSupport == 1 && meshOpen == 0){
                    //logger.info("mac为{}的网关支持未开启mesh",macAddress);
                    JSONObject resultJson = new JSONObject();
                    resultJson.put("MAC_ADDRESS",macAddress);
                    resultJson.put("ACCESS_TIME",accessTime);
                    resultJson.put("meshSupport",1);
                    ElasticSearchOperations.bulkGwOnlineUpdate(resultJson,esConfig.getGwIndex(),esConfig.getGwType(),macAddress);
                }
            }
        }
    }

    //1、获取路由器插件名称和版本号
    public JSONObject getPlugeData(String macAddress){
        try {
            String plugeUrl = "https://nos9.189cube.com/device/listplugin?MAC=" +macAddress
                    +"&token&appid=1000000208455928&secret=f17ddb39a13ad0e7&Plugin_Name=eLinkAP&Version=null";
            String plugeQueryBody = "{\"RPCMethod\": \"ListPlugin\",\"ID\": " +
                    "\"a2b15ce0-50da-11ec-8490-fa163ea2992d\",\"MAC\": \""+macAddress+"\"}";
            String plugeResult = HttpRetryUtils.getRetryQuery(plugeUrl, plugeQueryBody);
            if(!EmptyUtil.isEmpty(plugeResult)){
                JSONObject plugeJson = (JSONObject) JSONObject.parse(plugeResult);
                JSONArray plugeArrayList = plugeJson.getJSONArray("List");
                if(plugeArrayList != null && plugeArrayList.size()>0){
                    for(int i = 0;i<plugeArrayList.size();i++){
                        JSONObject jsonObject = plugeArrayList.getJSONObject(i);
                        String pluginName = jsonObject.getString("Plugin_Name");
                        if(!EmptyUtil.isEmpty(pluginName) && pluginName.contains("com.chinatelecom.all.smartgateway.inter_conndv3")){
                            return jsonObject;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("mac为{}的网关查询中间件版本接口出错",macAddress);
            //e.printStackTrace();
        }
        return null;
    }

    //2、根据mac、Plugin_Name、Version三个参数获取网关mesh状态
    public JSONObject getMeshState(String macAddress,String plugeName,String version){
        try {
            String meshUrl = "https://nos9.189cube.com/plugin/post?appid=1000000273534113&secret=4463d6ab2f0a4afc" +
                    "&PluginName="+plugeName+"&Version="+version+"&MAC="+ macAddress;
            String meshQueryBody = "{\"CmdType\": \"GetMeshStatus\",\"SequenceId\": \"76721\"}";
            String meshResult = HttpRetryUtils.postRetryQuery(meshUrl,meshQueryBody);
            //logger.info("mac为{}的网关nos9平台查询结果：{}",macAddress,meshResult);
            if(!EmptyUtil.isEmpty(meshResult)){
                JSONObject resultJson=(JSONObject) JSONObject.parse(meshResult);
                String return_parameter = resultJson.getString("return_Parameter");
                if(!EmptyUtil.isEmpty(return_parameter)){
                    JSONObject meshJson = (JSONObject) JSONObject.parse(Base64Utils.decode(return_parameter));
                    return  meshJson;
                }
            }
        } catch (Exception e) {
            logger.error("mac为{}的网关查询mesh状态接口出错",macAddress);
            //e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) throws InterruptedException {
        JSONObject reindexResult= HttpUtil.get("http://localhost:9200/_tasks/FIZqbuIqT_SJa-Ai-65uVA:7743");
        Boolean completed = (Boolean) reindexResult.get("completed");
        if(completed){
            JSONObject reindexStatus = reindexResult.getJSONObject("task").getJSONObject("status");
            logger.info("本次从动态库补充网关数据：total:{}, created:{}",
                    reindexStatus.getString("total"),reindexStatus.getString("created"));
        }
    }
}
