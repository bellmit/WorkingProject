package com.ruoyi.homewifi.district;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 集团省份编码和丰联省份编码转换枚举类
 *
 * @author chenshenjun
 * @since 2015-03-20
 */
public enum ProvinceCode {


    GD("1", "广东省", "844"),
    SH("2", "上海市", "831"),
    JS("3", "江苏省", "832"),
    ZJ("4", "浙江省", "833"),
    FJ("5", "福建省", "835"),
    SC("6", "四川省", "851"),
    HB("7", "湖北省", "842"),
    HN("8", "湖南省", "843"),
    SAX("9", "陕西省", "861"),
    YN("10", "云南省", "853"),
    AH("11", "安徽省", "834"),
    GX("12", "广西壮族自治区", "845"),
    HLJ("13", "黑龙江省", "823"),
    CQ("14", "重庆市", "850"),
    JX("15", "江西省", "836"),
    GS("16", "甘肃省", "862"),
    GZ("17", "贵州省", "852"),
    NMG("18", "内蒙古自治区", "815"),
    NX("19", "宁夏回族自治区", "864"),
    QH("20", "青海省", "863"),
    XZ("21", "西藏自治区", "854"),
    BJ("22", "北京市", "811"),
//    XG("23", "香港特别行政区", "消息"),
//    AM("24", "澳门特别行政区", "新闻"),
    TJ("25", "天津市", "812"),
    SD("26", "山东省", "837"),
    LN("27", "辽宁省", "821"),
    SX("28", "山西省", "814"),
    JL("29", "吉林省", "822"),
    XJ("30", "新疆维吾尔自治区", "865"),
    HAN("31", "海南省", "846"),
    HEN("32", "河南省", "841"),
    HEB("33", "河北省", "813");
//    UNKOWN("10000", "全国", "10000"),
//    CHINA("10001", "全国", "");

    public static final Map<String, ProvinceCode> data = new HashMap<String, ProvinceCode>();

    static {
        for (ProvinceCode c : ProvinceCode.values()) {
            data.put(c.getCode2(), c);
        }
    }
    /***
     * 全国标准code
     */
    private String code;

    /***
     * 省名称
     */
    private String name;

    /***
     * itms省Code
     */
    private String code2;



    private ProvinceCode(String code, String name, String code2) {
        this.name = name;
        this.code = code;
        this.code2 = code2;
    }



    /***
     * 通过code取出枚举类
     *
     * @return BussinessCode
     */
    public static ProvinceCode parse(String code2) {
        if(StringUtils.isEmpty(code2)||!data.containsKey(code2)) {
            return null;
        }
        return data.get(code2);
    }

    /****
     * 获取 所有的业务代码以及描述，以map形式返回
     *
     * @return Map<String,String>
     */
    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<String, String>();
        for (ProvinceCode provinceCode : ProvinceCode.values()) {
            map.put(provinceCode.code, provinceCode.name);
        }
        return map;
    }

    //枚举转换成list格式
    public static List<Map<String, Object>> toList() {
        List<Map<String, Object>> list = Lists.newArrayList();

        for (ProvinceCode provinceCode : ProvinceCode.values()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", provinceCode.getCode());
            map.put("name", provinceCode.getName());
            map.put("code2", provinceCode.getCode2());
            list.add(map);
        }
        return list;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

}

