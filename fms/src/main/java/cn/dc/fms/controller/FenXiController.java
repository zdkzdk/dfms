package cn.dc.fms.controller;

import cn.dc.fms.dao.FeiYongDao;
import cn.dc.fms.dao.JingYingDao;
import cn.dc.fms.dao.ZiChanDao;
import cn.dc.fms.orm.TFeiyong;
import cn.dc.fms.orm.TJingying;
import cn.dc.fms.orm.TZichan;
import com.alibaba.druid.support.json.JSONUtils;
import org.apache.commons.collections.map.HashedMap;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@RemoteProxy
@Controller
@RequestMapping("fenxi")
public class FenXiController {
    @Autowired
    private ZiChanDao ziChanDao;
    @Autowired
    private JingYingDao jingYingDao;
    @Autowired
    private FeiYongDao feiYongDao;

    @RequestMapping("table")
    public String fenxi(Model model) {
        //增加的资产的数量和总价值
        Map<String, Double> zczj = ziChanDao.zczj();
        //减少的资产的数量和总价值
        Map<String, Double> zcjs = ziChanDao.zcjs();
        //总资产 = 增加资产 - 减少资产
        double zzj = zczj.get("jiazhi") - zcjs.get("jiazhi");
        //投入
        Map<String, Double> jingying = jingYingDao.jingying();

        /*
        费用
         */
        Map<String, Double> map = findFeiyong();

         /*
        年终资产 = 总资产 + 总利润 - 总费用
        总利润在jingying表中已经得出
         */
        Double lirun = jingying.get("lirun");
        double nianzhong = lirun + zzj - map.get("totalCost");
        model.addAttribute("zczj", zczj);
        model.addAttribute("zcjs", zcjs);
        model.addAttribute("zongzichan", zzj);
        model.addAttribute("jingying", jingying);
        model.addAttribute("nz", nianzhong);
        model.addAttribute("totalEarning", map.get("totalEarning"));
        model.addAttribute("totalExpense", map.get("totalExpense"));
        model.addAttribute("totalCost", map.get("totalCost"));


        return "fenxi/fenxi";
    }

    @RemoteMethod
    public String echarts(int year) throws IOException {
        List<TZichan> zichans = ziChanDao.findAll();
        /*
        存放数据的map，key是月份，value是数据
        因为要按月份排序，所以使用treeMap
         */
        Map<Integer, Double> map = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        /*
        查有数据的年份返回给前台，用于前台图表中选择年份的下拉框
         */
        Set<Integer> yearSet = new TreeSet<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });


        /*
        按年分类，将资产算出，然后跟本月所有的资产计算得出本月的资产总额，总额 = 增加 - 减少

         */
        for (TZichan zichan : zichans) {
            LocalDate date = LocalDate.parse(zichan.getShijian());
            if (year == date.getYear()) {
                int m = date.getMonthValue();
                //如果type为0，则q为正数，否则为负数
                double q = Double.parseDouble(zichan.getJiazhi());
                if ("1".equals(zichan.getType())) q = -q;

                //如果这个月的资产没有，就从0开始计算
                double w = map.get(date.getMonthValue()) == null ? 0 : map.get(date.getMonthValue());
                map.put(m, q + w);
            }
            yearSet.add(date.getYear());
        }


       /* List<String> x = new ArrayList<String>() {{
            for (int i = 0;i < 12;i++ ) {
                add((i + 1) + "月");
            }
        }};*/
        Map<String, Collection> map1 = new HashMap<>();

        List<Integer> xAxisData = new ArrayList<>();

        List<Double> yAxisData = new ArrayList<>();
        for (Integer month : map.keySet()) {
            xAxisData.add(month);
            yAxisData.add(map.get(month));
        }

        map1.put("xAxisData", xAxisData);
        map1.put("yAxisData", yAxisData);
        map1.put("yearSet", yearSet);
        return JsonUtil.toJson(map1);
    }


    /**
     * 经营雷达图
     * 按年把投入、收入、利润放到model中返回
     *
     * @return
     */
    @RequestMapping("analyzedJingYingCharts")
    public String jingYingRadarCharts(@RequestParam(value = "year", defaultValue = "2019") int year, Model model) {
        List<TJingying> jingyings = jingYingDao.findAll();
        Double touru = 0d;
        Double shouyi = 0d;
        Double lirun = 0d;
        Set<Integer> set = new TreeSet<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        for (TJingying jingying : jingyings) {
            LocalDate date = LocalDate.parse(jingying.getRiqi());
            if (year == date.getYear()) {
                touru += jingying.getTouru();
                shouyi += jingying.getShouyi();
                lirun += jingying.getLirun();
            }
            set.add(date.getYear());
        }
        model.addAttribute("touru", touru);
        model.addAttribute("shouyi", shouyi);
        model.addAttribute("lirun", lirun);
        model.addAttribute("years", set);
        model.addAttribute("currentYear", year);
        return "fenxi/analyzedJingYingCharts";
    }

    /**
     * 费用饼图
     *
     * @return
     */
    @RequestMapping("analyzedFeiYongCharts")
    public String analyzedFeiYongCharts(@RequestParam(value = "year", defaultValue = "2019") int year, Model model) throws IOException {
        List<TFeiyong> feiyongs = feiYongDao.findAll();
        Set<Integer> set = new TreeSet<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        Map<String, String> map = new HashMap<>();
        for (TFeiyong feiyong : feiyongs) {
            LocalDate date = LocalDate.parse(feiyong.getShijian());
            if (year == date.getYear()) {
                map.put(feiyong.getMingcheng(), feiyong.getFeiyong());
            }
            set.add(date.getYear());
        }

        model.addAttribute("map", JSONUtils.toJSONString(map));
        model.addAttribute("years", set);
        model.addAttribute("currentYear", year);

        return "fenxi/analyzedFeiYongCharts";
    }


    /*
    使用jpa的方式计算费用情况
        总收入
        支出+报销的总额
        费用合计
     */
    private Map<String, Double> findFeiyong() {
        Map<String, Double> map = new HashedMap(3);
        List<TFeiyong> feiyongs = feiYongDao.findAll();
        double totalEarning = 0;
        double totalExpense = 0;
        double totalCost = 0;
        for (TFeiyong feiyong : feiyongs) {
            switch (feiyong.getLeixing()) {
                case "0":
                    totalEarning += Double.parseDouble(feiyong.getFeiyong());
                    break;
                case "1":
                    totalExpense += Double.parseDouble(feiyong.getFeiyong());
                    break;
                case "2":
                    totalExpense += Double.parseDouble(feiyong.getFeiyong());
                    break;
            }
        }
        totalCost = (totalExpense - totalEarning);
        map.put("totalEarning", totalEarning);
        map.put("totalExpense", totalExpense);
        map.put("totalCost", totalCost);
        return map;
    }


}
