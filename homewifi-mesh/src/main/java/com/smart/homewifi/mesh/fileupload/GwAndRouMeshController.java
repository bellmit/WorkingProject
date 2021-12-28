package com.smart.homewifi.mesh.fileupload;

import com.smart.homewifi.mesh.config.BaseConfig;
import com.smart.homewifi.mesh.es.JsonView;
import com.smart.homewifi.mesh.gatewaystate.GatewayMesh;
import com.smart.homewifi.mesh.routerstate.RouterMesh;
import com.smart.homewifi.mesh.scp.ScpTransfer;
import com.smart.homewifi.mesh.utils.CalendarUtils;
import com.smart.homewifi.mesh.utils.FileZipUtile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/meshStatistic")
public class GwAndRouMeshController {

    @Autowired
    private BaseConfig baseConfig;

    private static final Logger logger = LoggerFactory.getLogger(GwAndRouMeshController.class);
    private SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private final static String EOL = System.getProperty("line.separator");

    @GetMapping("/getCourrentMesh")
    public void getGwAndRouMesh(){
        GatewayMesh gatewayMesh = new GatewayMesh();
        RouterMesh routerMesh = new RouterMesh();
        Date updLoadDate = new Date();
        logger.info("{}get courrent mesh state statistics file.",dateFormat.format(updLoadDate));
        try {
            String scrollId = null;
            boolean flag = true;
            String RouterFilePath = baseConfig.getLocaldir()+"/Courrent_Router_Mesh_State_"+ CalendarUtils.getDate()
                    +"_"+CalendarUtils.getLastMonth()+".txt";
            File routerMeshFile = new File(RouterFilePath);
            if(!routerMeshFile.exists()){
                try {
                    routerMeshFile.createNewFile();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            while(flag){
                //1、查询网关ES库
                JsonView jsonView = routerMesh.scrollRouterMesh(scrollId);
                scrollId = jsonView.getScrollId();
                if(jsonView.getNumber() == 0){
                    flag = false;
                }else{
                    //2、分批写入文件
                    routerMesh.writeRouterMeshForLine(routerMeshFile,jsonView.getList());
                }
            }
            ScpTransfer scpTransfer = new ScpTransfer();
            scpTransfer.scpUploadFile(FileZipUtile.GzipFile(RouterFilePath,""));
            routerMeshFile.delete();
        } catch (Exception e) {
            logger.error("{} 路由器mesh状态文件上传出错",dateFormat.format(updLoadDate));
            e.printStackTrace();
        }

        try {
            String scrollId = null;
            boolean flag = true;
            String GatewayFilePath = baseConfig.getLocaldir()+"/Courrent_GateWay_Mesh_State_"+ CalendarUtils.getDate()
                    +"_"+CalendarUtils.getLastMonth()+".txt";
            File GatewayMeshFile = new File(GatewayFilePath);
            if(!GatewayMeshFile.exists()){
                try {
                    GatewayMeshFile.createNewFile();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            while(flag){
                //1、查询网关ES库
                JsonView jsonView = gatewayMesh.scrollGatewayMesh(scrollId);
                scrollId = jsonView.getScrollId();
                if(jsonView.getNumber() == 0){
                    flag = false;
                }else{
                    //2、分批写入文件
                    gatewayMesh.writeGatewayMeshForLine(GatewayMeshFile,jsonView.getList());
                }
            }
            ScpTransfer scpTransfer = new ScpTransfer();
            scpTransfer.scpUploadFile(FileZipUtile.GzipFile(GatewayFilePath,""));
            GatewayMeshFile.delete();
        } catch (Exception e) {
            logger.error("{}网关mesh状态文件上传出错",dateFormat.format(updLoadDate));
            e.printStackTrace();
        }
    }

}
