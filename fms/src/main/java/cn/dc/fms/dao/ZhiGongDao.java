package cn.dc.fms.dao;

import cn.dc.fms.orm.TAdmin;
import cn.dc.fms.orm.TZhigong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ZhiGongDao extends JpaRepository<TZhigong,Integer>, JpaSpecificationExecutor<TZhigong> {
    String sql = "select ta.*,tb.mingcheng bmmc,tb.xishu from t_zhigong ta,t_bumen tb " +
            "where ta.del='no' and ta.bumen_id=tb.id and ta.id not in " +
            "(select zhigong_id from t_gongzi)";
    @Query(value = sql,nativeQuery = true)
    List<TZhigong> zhigongList();
}
