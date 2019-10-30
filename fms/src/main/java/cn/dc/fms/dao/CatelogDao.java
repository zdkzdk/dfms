package cn.dc.fms.dao;

import cn.dc.fms.orm.TAdmin;
import cn.dc.fms.orm.TCatelog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface CatelogDao extends JpaRepository<TCatelog,Integer>, JpaSpecificationExecutor<TCatelog> {


}
