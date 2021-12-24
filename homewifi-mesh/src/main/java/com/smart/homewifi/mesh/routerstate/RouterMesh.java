package com.smart.homewifi.mesh.routerstate;

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
import org.junit.Test;
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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author:Z
 * @Date:2021/11/15 13:49
 * @Version:1.0
 */
@Component
public class RouterMesh {

    @Autowired
    private EsConfig esConfig;
    @Autowired
    private BaseConfig baseConfig;
    //每秒只发出100个令牌
    private RateLimiter rateLimiter = RateLimiter.create(100.0);
    private SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static final Logger logger = LoggerFactory.getLogger(RouterMesh.class);
    private final static String EOL = System.getProperty("line.separator");


    public void getRouterMesh() {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(baseConfig.getCorePoolSize(), baseConfig.getCorePoolSize()+10,
                2, TimeUnit.SECONDS, new LinkedBlockingQueue<>(5000));
        //上传标志位
        int updloadFlag = 0;
        //游标
        String scrollId = null;
        //单次scroll查询结果
        JsonView jsonView = null;
        //批量写入ES库
        while(true){
            try {
                if(TimeUtils.isDayOfMonth(20)){
                    Date changeDate = new Date();
                    logger.info("本月{}修改上传标志位为0",dateFormat.format(changeDate));
                    updloadFlag = 0;
                }
                if(TimeUtils.isDayOfMonth(baseConfig.getRouterUpdateDay()) && updloadFlag ==0){
                    uploadFile();
                    updloadFlag = 1;
                }
                //使用scroll条件查询时间排序
                jsonView = scrollRouterMac(scrollId);
                logger.info("查询本地库获取在线路由器mac {}条",jsonView.getNumber());
                scrollId = jsonView.getScrollId();
                if(jsonView.getNumber() == 0){
                    Date reindexDate = new Date();
                    logger.info("{}，本轮本地库路由器mac查询结束，开始reindex从线上aponline动态库补充数据",dateFormat.format(reindexDate));
                    String taskId = reindexNewRouter();
                    while(!checkTask(taskId)){
                        //reindex未完成就sleep;
                        Thread.sleep(6000);
                    }
                    scrollId = "";
                    jsonView = scrollRouterMac(scrollId);
                    logger.info("reindex结束后，查询本地库获取在线路由器mac {}条",jsonView.getNumber());
                    scrollId = jsonView.getScrollId();
                }
                //logger.info("for循环Scroll查询结果");
                for (JSONObject jsonObject :jsonView.getList()){
                    String macAddress = jsonObject.getString("mac");
                    //logger.info("开始查询mac为{}的路由器",macAddress);
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
                        logger.info("查询路由器mac:{}休眠1秒后线程队列排队{}个,正在执行线程数{}条",macAddress,
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
        Date updateDate = new Date();
        try {
            String scrollId = null;
            boolean flag = true;
            logger.info("{}路由器mesh每月定时上传任务开始执行",dateFormat.format(updateDate));
            String filePath = baseConfig.getLocaldir()+"/Router_Mesh_State_"+ CalendarUtils.getDate()
                    +"_"+CalendarUtils.getLastMonth()+".txt";
            File routerMeshFile = new File(filePath);
            if(!routerMeshFile.exists()){
                try {
                    routerMeshFile.createNewFile();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            while(flag){
                //1、查询网关ES库
                JsonView jsonView = scrollRouterMesh(scrollId);
                scrollId = jsonView.getScrollId();
                if(jsonView.getNumber() == 0){
                    flag = false;
                }else{
                    //2、分批写入文件
                    writeRouterMeshForLine(routerMeshFile,jsonView.getList());
                }
            }
            //3、压缩文件、上传文件
            logger.info("开始压缩文件");
            ScpTransfer scpTransfer = new ScpTransfer();
            logger.info("开始scp上传文件");
            scpTransfer.scpUploadFile(FileZipUtile.GzipFile(filePath,""));
            logger.info("上传结束删除文件");
            routerMeshFile.delete();
            //5、删除原表所有数据、上传标志位置1
            String deleteaskId = deleteAllData();
            while(!checkTask(deleteaskId)){
                //reindex未完成就sleep;
                Thread.sleep(10000);
            }
            //6、reindex一张新表
            logger.info("上传结束，删除原表数据，reindex新表数据");
            String reindexTaskId = reindexNewRouter();
            while(!checkTask(reindexTaskId)){
                //reindex未完成就sleep;
                Thread.sleep(60000);
            }
        } catch (Exception e) {
            logger.error("{} 路由器mesh状态文件上传出错",dateFormat.format(updateDate));
            e.printStackTrace();
        }
    }


    /**
     * 从动态库aponline上补充数据到ES数据库
     */
    public String reindexNewRouter(){
        String reindexString  = "{\n" +
                "    \"conflicts\":\"proceed\",\n" +
                "    \"source\": {\n" +
                "        \"index\": \"aponline\",\n" +
                "        \"size\": 10000,\n" +
                "        \"query\": {\n" +
                "            \"term\": {\n" +
                "                \"PlatformID.keyword\": \"SMARTAP\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"_source\": [\"mac\",\"ADD_TIME\"]\n" +
                "    },\n" +
                "    \"dest\": {\n" +
                "        \"index\": \"aponline_copy\",\n" +
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
                "/aponline_copy/_delete_by_query?refresh&slices=5&wait_for_completion=false";
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
            logger.info("本次从补充/删除路由器数据：total:{}, created:{}",
                    reindexStatus.getString("total"),reindexStatus.getString("created"));
        }
        return completed;
    }

    /**
     * 使用scroll查询网关4.0复制表，获取mac用于接口调用
     * @param scrollId
     * @return
     */
    public JsonView scrollRouterMac(String scrollId){
        String url="http://"+esConfig.getEsAddress()+":"+esConfig.getEsPort();
        JSONObject queryRoot = new JSONObject();
        if(EmptyUtil.isEmpty(scrollId)){
            url+="/aponline_copy/_search?scroll=10m&size=10000";
            JSONObject boolString = new JSONObject();
            JSONArray mustNotString = new JSONArray();
            mustNotString.add(newJSONObject("exists",newJSONObject("field", "meshOpen")));
            mustNotString.add(newJSONObject("term",newJSONObject("meshSupport", 0)));
            boolString.put("must_not", mustNotString);
            queryRoot.put("query",newJSONObject("bool",boolString));
            queryRoot.put("_source", new String[]{"mac","meshSupport"});
            JSONArray sortString = new JSONArray();
            sortString.add(newJSONObject("ADD_TIME",newJSONObject("order", "desc")));
            queryRoot.put("sort",sortString);
        }else{
            url+="/_search/scroll?scroll=10m&scroll_id="+scrollId;
        }
        return handleScrollResult(url,queryRoot);
    }

    /**
     * 使用scroll查询获取网关mesh统计结果
     */
    public JsonView scrollRouterMesh(String scrollId){
        String url="http://"+esConfig.getEsAddress()+":"+esConfig.getEsPort();
        JSONObject queryRoot = new JSONObject();
        if(EmptyUtil.isEmpty(scrollId)){
            url+="/aponline_copy/_search?scroll=10m&size=10000";
            JSONObject boolString = new JSONObject();
            JSONArray mustString = new JSONArray();
            mustString.add(newJSONObject("exists",newJSONObject("field", "meshOpen")));
            boolString.put("must", mustString);
            queryRoot.put("query",newJSONObject("bool",boolString));
            queryRoot.put("_source", new String[]{"mac","meshOpen"});
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

    /**
     *
     */
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
    public void writeRouterMeshForLine(File file, List<JSONObject> list) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file,true));
            for ( JSONObject esObject: list ) {
                String openMeshMac = esObject.getString("mac");
                String meshSupport = esObject.getString("meshSupport");
                writer.write(openMeshMac+"\u0005"+meshSupport+ EOL);//按行写文件，后面追加行分隔符EOL
            }
            //关闭流
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("路由器Mesh开启状态，写入出错");
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
        String macAddress = jsonObject.getString("mac");
        Long addTime = jsonObject.getLong("ADD_TIME");
        Integer meshSupport = jsonObject.getInteger("meshSupport");

        if(meshSupport != null){
            //直接调用路由器mesh开启状态查询接口
            boolean routerMeshOpen = getRouterMeshOpen(macAddress);
            if(routerMeshOpen && meshSupport==1){
                //写入一条数据
                /*String postUrl = "http://"+esConfig.getEsAddress()+":"+esConfig.getEsPort()+
                        "/aponline_copy/messagedb/"+macAddress+"/_update";
                String state = "{\"doc\":{\"meshSupport\": 1,\"meshOpen\": 1}}";
                HttpUtil.post(postUrl,state);*/

                //使用批量写入
                //logger.info("已支持的mac为{}的路由器支持且开启mesh",macAddress);
                JSONObject readJson = new JSONObject();
                readJson.put("mac",macAddress);
                readJson.put("ADD_TIME",addTime);
                readJson.put("meshSupport",1);
                readJson.put("meshOpen",1);
                ElasticSearchOperations.bulkGwOnlineUpdate(readJson,esConfig.getApIndex(),esConfig.getApType(),macAddress);
            }else if (routerMeshOpen && meshSupport==2){
                //logger.info("已支持的mac为{}的路由器支持且开启easyMesh",macAddress);
                JSONObject readJson = new JSONObject();
                readJson.put("mac",macAddress);
                readJson.put("ADD_TIME",addTime);
                readJson.put("meshSupport",2);
                readJson.put("meshOpen",1);
                ElasticSearchOperations.bulkGwOnlineUpdate(readJson,esConfig.getApIndex(),esConfig.getApType(),macAddress);
            }else if(!routerMeshOpen){
                //logger.info("已支持的mac为{}的路由器未开启",macAddress);
            }
        }else{
            //long startTime = System.currentTimeMillis();
            Integer routerMeshSupport = getRouterMeshSupport(macAddress);
            //调用路由器mesh支持状态查询接口
            if (routerMeshSupport != null){
                boolean routerMeshOpen = getRouterMeshOpen(macAddress);
                //logger.info("调用两个接口查询{}，耗时{}ms",macAddress,System.currentTimeMillis()-startTime);
                if(routerMeshOpen){
                    if(routerMeshSupport == 1){
                        //logger.info("未查询的mac为{}的路由器支持且开启mesh",macAddress);
                        JSONObject readJson = new JSONObject();
                        readJson.put("mac",macAddress);
                        readJson.put("ADD_TIME",addTime);
                        readJson.put("meshSupport",1);
                        readJson.put("meshOpen",1);
                        ElasticSearchOperations.bulkGwOnlineUpdate(readJson,esConfig.getApIndex(),esConfig.getApType(),macAddress);
                    }else if(routerMeshSupport == 2){
                        //logger.info("未查询的mac为{}的路由器支持且开启easyMesh",macAddress);
                        JSONObject readJson = new JSONObject();
                        readJson.put("mac",macAddress);
                        readJson.put("ADD_TIME",addTime);
                        readJson.put("meshSupport",2);
                        readJson.put("meshOpen",1);
                        ElasticSearchOperations.bulkGwOnlineUpdate(readJson,esConfig.getApIndex(),esConfig.getApType(),macAddress);
                    }
                }else{
                    if(routerMeshSupport == 0){
                        //logger.info("未查询的mac为{}的路由器不支持mesh",macAddress);
                        JSONObject readJson = new JSONObject();
                        readJson.put("mac",macAddress);
                        readJson.put("ADD_TIME",addTime);
                        readJson.put("meshSupport",0);
                        ElasticSearchOperations.bulkGwOnlineUpdate(readJson,esConfig.getApIndex(),esConfig.getApType(),macAddress);
                    }else if(routerMeshSupport == 1){
                        //logger.info("未查询的mac为{}的路由器支持未开启mesh",macAddress);
                        JSONObject readJson = new JSONObject();
                        readJson.put("mac",macAddress);
                        readJson.put("ADD_TIME",addTime);
                        readJson.put("meshSupport",1);
                        ElasticSearchOperations.bulkGwOnlineUpdate(readJson,esConfig.getApIndex(),esConfig.getApType(),macAddress);
                    }else if(routerMeshSupport == 2){
                        //logger.info("未查询的mac为{}的路由器支持未开启easyMesh",macAddress);
                        JSONObject readJson = new JSONObject();
                        readJson.put("mac",macAddress);
                        readJson.put("ADD_TIME",addTime);
                        readJson.put("meshSupport",2);
                        ElasticSearchOperations.bulkGwOnlineUpdate(readJson,esConfig.getApIndex(),esConfig.getApType(),macAddress);
                    }
                }
            }else {
                logger.info("mac为{}的路由器mesh支持状态查询失败",macAddress);
            }
        }
    }

    /**
     * 调用接口查询路由器是否支持mesh或者easymesh
     * @param macAddress
     * @return
     */
    public Integer getRouterMeshSupport(String macAddress){
        try {
            String supportQueryUrl = "https://apweb1.189cube.com:8443/plugin/post.php?MAC=" +
                    macAddress+"&appid=1000000273534113&secret=4463d6ab2f0a4afc&PluginName&Version=1.0";
            String supportQueryBody = "{\"type\": \"get_status\",\"get\": [{\"name\": \"capability\"}]}";
            String responseString = HttpRetryUtils.postRetryQuery(supportQueryUrl, supportQueryBody);
            //Todo:可使用第三方库JsonPath优化Json嵌套时获取特定的Key进行优化
            if(!EmptyUtil.isEmpty(responseString)){
                JSONObject responseJson = (JSONObject) JSONObject.parse(responseString);
                String returnParameter = responseJson.getString("return_Parameter");
                if(!EmptyUtil.isEmpty(returnParameter)){
                    JSONObject returnJson = (JSONObject) JSONObject.parse(Base64Utils.decode(returnParameter));
                    JSONObject statusJson = returnJson.getJSONObject("status");
                    if(statusJson != null){
                        JSONObject capabilityJson = statusJson.getJSONObject("capability");
                        if(capabilityJson != null){
                            Integer supportMesh = capabilityJson.getInteger("supportMesh");
                            if(supportMesh != null){
                                //支持
                                if(supportMesh == 1){
                                    return 1;
                                }else if(supportMesh == 2){
                                    return 2;
                                }else if(supportMesh == 0){
                                    return 0;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("mac为{}的路由器查询mesh支持接口出错",macAddress);
            //e.printStackTrace();
        }
        return 0;
    }

    /**
     * 调用接口查询路由器是否开启mesh或者easymesh
     * @param macAddress
     * @return
     */
    public boolean getRouterMeshOpen(String macAddress){
        try {
            String openQueryUrl = "https://apweb1.189cube.com:8443/plugin/post.php?MAC=" +
                    macAddress+"&appid=1000000273534113&secret=4463d6ab2f0a4afc&PluginName=&Version=1.0";
            String openQueryBody = "{\"type\": \"ubus_call\"," +
                    "\"object\": \"ctcapd.wifi.mesh\",\"method\": \"get\",\"data\": {}}";
            String responseString = HttpRetryUtils.postRetryQuery(openQueryUrl, openQueryBody);
            if(!EmptyUtil.isEmpty(responseString)){
                JSONObject responseJson = (JSONObject) JSONObject.parse(responseString);
                String returnParameter = responseJson.getString("return_Parameter");
                if(!EmptyUtil.isEmpty(returnParameter)){
                    JSONObject returnJson = (JSONObject) JSONObject.parse(Base64Utils.decode(returnParameter));
                    JSONObject dataJson = returnJson.getJSONObject("data");
                    if(dataJson != null){
                        JSONObject ctcapdJson = dataJson.getJSONObject("ctcapd.wifi.mesh");
                        if(ctcapdJson != null){
                            String status = ctcapdJson.getString("status");
                            if(!EmptyUtil.isEmpty(status) && "yes".equals(status) ){
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("mac为{}的路由器查询mesh开启接口出错",macAddress);
            //e.printStackTrace();
        }
        return false;
    }




}
