package cn.dc.fms.dao;

import cn.dc.fms.orm.TAdmin;
import cn.dc.fms.orm.TGongzi;
import cn.dc.fms.orm.TZhigong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface GongZiDao extends JpaRepository<TGongzi,Integer>, JpaSpecificationExecutor<TGongzi> {


}
