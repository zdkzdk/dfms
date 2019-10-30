package cn.dc.fms.dao;

import cn.dc.fms.orm.TZichan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;


public interface ZiChanDao extends JpaRepository<TZichan,Integer>, JpaSpecificationExecutor<TZichan> {
    //增加资产的数量和总价值
    @Query(value = "select count(1) shuliang,ifnull(sum(jiazhi),0) jiazhi from t_zichan where type=0", nativeQuery = true)
    Map<String, Double> zczj();
    //减少资产的数量和总价值
    @Query(value = "select count(1) shuliang,ifnull(sum(jiazhi),0) jiazhi from t_zichan where type=1", nativeQuery = true)
    Map<String, Double> zcjs();
}
