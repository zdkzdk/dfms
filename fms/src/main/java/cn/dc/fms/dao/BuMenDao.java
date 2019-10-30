package cn.dc.fms.dao;

import cn.dc.fms.orm.TBumen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface BuMenDao extends JpaRepository<TBumen,Integer>, JpaSpecificationExecutor<TBumen> {

}
