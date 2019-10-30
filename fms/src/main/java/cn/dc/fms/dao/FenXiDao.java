package cn.dc.fms.dao;

import cn.dc.fms.orm.TAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface FenXiDao extends JpaRepository<TAdmin,Integer>, JpaSpecificationExecutor<TAdmin> {

}
