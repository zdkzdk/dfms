package cn.dc.fms.dao;

import cn.dc.fms.orm.TAdmin;
import cn.dc.fms.orm.TFeiyong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface FeiYongDao extends JpaRepository<TFeiyong,Integer>, JpaSpecificationExecutor<TFeiyong> {

}
