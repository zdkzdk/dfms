package cn.dc.fms.dao;

import cn.dc.fms.orm.TJingying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;


public interface JingYingDao extends JpaRepository<TJingying,Integer>, JpaSpecificationExecutor<TJingying> {
    @Query(value = "select 1, ifnull(sum(touru),0) touru,ifnull(sum(shouyi),0) shouyi,ifnull(sum(lirun),0) lirun from t_jingying", nativeQuery = true)
    public Map<String, Double> jingying();

}
