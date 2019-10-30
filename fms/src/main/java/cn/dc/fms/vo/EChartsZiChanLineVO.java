package cn.dc.fms.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 为年终资产echarts图的
 */
public class EChartsZiChanLineVO {
    /* *//*
    x轴
        提供x轴的类型和数据，类型必须为type:'category',数据的key为data，值为集合或数组
     *//*
    Map<String, Object> mapX = new HashMap<>();
    Map<String, Object> mapY = new HashMap<>();
    Map<String, Object> series = new HashMap<>();
    List<String> data = new ArrayList<>();
    public EChartsZiChanLineVO() {
        mapX.put("type", "category");
        List<String> month = new ArrayList<String>() {{
            for (int i = 0;i < 12;i++ ) {
                add(i + "月");
            }
        }};
        mapX.put("data", month);
        //==========================
        mapY.put("type", "value");
        //=====================
        series.put("data",data);
        series.put("type", "line");

    }*/
    /*
    xAxisData:x轴显示的数据，集合
    yAxisData:x轴上每个节点对应的数据，集合
    */
    //private Map<String, List> map = new HashMap<>();
    //public EChartsZiChanLineVO(Map<Integer,Double> mapData){
    //    List<Integer> xAxisData = new ArrayList<>();
    //    List<Double> yAxisData = new ArrayList<>();
    //    for (Integer month : mapData.keySet()){
    //        xAxisData.add(month);
    //        yAxisData.add(mapData.get(month));
    //    }
    //
    //    map.put("xAxisData", xAxisData);
    //    map.put("yAxisData", yAxisData);
    //}

}
