package com.ruoyi.homewifi.district;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ruoyi.homewifi.domain.DataDistrictCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询数据库使用地区字典
 * author:yangjun
 * Date:2019/1/22
 */
@Component
public  class DistrictDirc {
    public static Connection  connection = null;
    public static ResultSet resultSet = null;
    public static String driver = null;
    public static String url = null;
    public static String user = null;
    public static String pass = null;

    //districtMap:(万维地名编码,地名) 用来翻译万维编码到地名
    public static Map<String,String> districtMap = Maps.newHashMap();
    //IdTransferMap:(万维地名编码,集团地名编码) 用来实现编码转换
    public static Map<String,String> IdTransferMap = Maps.newHashMap();
    //provMap:(万维省份编码,省份编号) 用来获取省份编码
    public static Map<String,Long> provMap = Maps.newHashMap();
    //cityMap:(万维城市编码,城市上级省份编号) 用来获取城市编码
    public static Map<String,Long> cityMap = Maps.newHashMap();


    private static final Logger logger = LoggerFactory.getLogger(DistrictDirc.class);

    @Value("${spring.datasource.driverClassName}")
    public void setDriver(String driver) {
        DistrictDirc.driver = driver;
    }

    @Value("${spring.datasource.druid.master.url}")
    public void setUrl(String url) {
        DistrictDirc.url = url;
    }

    @Value("${spring.datasource.druid.master.username}")
    public void setUser(String user) {
        DistrictDirc.user = user;
    }

    @Value("${spring.datasource.druid.master.password}")
    public void setPass(String pass) {
        DistrictDirc.pass = pass;
    }

    public static String getDistrict(String wwDistrictId){
        String result = "";
        if(districtMap.size()==0){
            findAllDistrict();
        }
        if(districtMap.containsKey(wwDistrictId)){
            result= districtMap.get(wwDistrictId);
            if("".equals(result)){
                result = "未知";
            }
        }else {
            result="未知";
        }
        return result;
    }


    /**
     * 查询所有城市
     * 加锁防止数据库过载
     * */
    public synchronized static void findAllDistrict() {
        //logger.info("开始查地名*****************************************");
        PreparedStatement prepared = null;
        try {
            if(connection == null){
                connection = getConnection();
            }

            prepared = connection.prepareStatement("select * from data_district_code");
            resultSet = prepared.executeQuery();
            List<JSONObject> jsonObjects = turnRs2JSON(resultSet);

            List<DataDistrictCode> districtList = new ArrayList<>();
            for (JSONObject jsonObject : jsonObjects) {
                DataDistrictCode districtDetail = JSONObject.toJavaObject(jsonObject, DataDistrictCode.class);
                districtList.add(districtDetail);
            }

            for (DataDistrictCode dc:districtList){
                if(!districtMap.containsKey(dc.getStrAreaAuth())){
                    districtMap.put(dc.getStrAreaAuth(),dc.getStrRoleName());
                }
                if(!IdTransferMap.containsKey(dc.getStrAreaAuth())){
                    IdTransferMap.put(dc.getStrAreaAuth(),dc.getDept_id());
                }
                if(dc.getIntLevel()==1 && !provMap.containsKey(dc.getStrAreaAuth())){
                    provMap.put(dc.getStrAreaAuth(),dc.getStrCode());
                }
                if(dc.getIntLevel()==2 && !cityMap.containsKey(dc.getStrAreaAuth())){
                    cityMap.put(dc.getStrAreaAuth(),dc.getStrParentCode());
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                if(resultSet !=null)resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if(prepared !=null)prepared.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if(connection !=null)connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }





    public static Connection getConnection(){
        Connection conn = null;
            DruidDataSource datasource = CreateDataSource();
        try {
            conn = datasource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    private static synchronized DruidDataSource CreateDataSource() {
        DruidDataSource  ffdataSource = new DruidDataSource();
        ffdataSource.setDriverClassName(driver);
        ffdataSource.setUrl(url);
        ffdataSource.setUsername(user);
        ffdataSource.setPassword(pass);
        ffdataSource.setInitialSize(5);
        ffdataSource.setMinIdle(1);
        ffdataSource.setMaxActive(20);
        ffdataSource.setPoolPreparedStatements(false);
        return ffdataSource;
    }

    /**
     * 将结果转换成JSONObject
     * @param rs
     * @return
     * @throws SQLException
     */
    public static  List<JSONObject> turnRs2JSON(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        SimpleDateFormat simpl = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        int columcount = rsmd.getColumnCount();
        List<JSONObject> jsonArray = Lists.newArrayList();
        while (rs.next()) {
            JSONObject json = new JSONObject();
            for (int i = 1; i <=columcount; i++) {
                String columnname = rsmd.getColumnLabel(i);
                int typenum = rsmd.getColumnType(i);
                Object value = null;
                if (typenum == Types.TIMESTAMP) {
                    if(rs.getTimestamp(columnname)!=null) {
                        String value2 = simpl.format(rs.getTimestamp(columnname));
                        json.put(columnname.toUpperCase()+"_str", value2);
                        value = rs.getTimestamp(columnname);
                    }
                }
                else if (typenum == Types.INTEGER) {
                    value = rs.getLong(columnname);
                }
                else if (typenum == Types.BIGINT) {
                    value = rs.getLong(columnname);
                }
                else if (typenum == Types.SMALLINT) {
                    value = rs.getLong(columnname);
                }

                else if (typenum == Types.VARCHAR) {
                    value = rs.getString(columnname);
                }
                else if (typenum == Types.DATE) {
                    if(rs.getDate(columnname)!=null) {
                        String value2 = simpl.format(rs.getDate(columnname));
                        json.put(columnname.toUpperCase()+"_str", value2);
                        value = rs.getDate(columnname);
                    }
                }
                else if (typenum == Types.TINYINT) {
                    value = rs.getLong(columnname);
                }

                else if (typenum == Types.BOOLEAN) {
                    value = rs.getBoolean(columnname);
                }else {
                    value = rs.getString(columnname);
                }

                json.put(columnname.toUpperCase(), value);
            }
            jsonArray.add(json);

        }
        return jsonArray;
    }

}
