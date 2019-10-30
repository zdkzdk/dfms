package cn.dc.fms.dao;

import cn.dc.fms.orm.TAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


public interface AdminDao extends JpaRepository<TAdmin,Integer>, JpaSpecificationExecutor<TAdmin> {

}
