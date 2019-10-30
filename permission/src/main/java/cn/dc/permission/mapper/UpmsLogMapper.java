package cn.dc.permission.mapper;

import cn.dc.permission.pojo.UpmsLog;
import cn.dc.permission.pojo.UpmsLogExample;
import cn.dc.permission.pojo.UpmsLogWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UpmsLogMapper {
    int countByExample(UpmsLogExample example);

    int deleteByExample(UpmsLogExample example);

    int deleteByPrimaryKey(Integer logId);

    int insert(UpmsLogWithBLOBs record);

    int insertSelective(UpmsLogWithBLOBs record);

    List<UpmsLogWithBLOBs> selectByExampleWithBLOBs(UpmsLogExample example);

    List<UpmsLog> selectByExample(UpmsLogExample example);

    UpmsLogWithBLOBs selectByPrimaryKey(Integer logId);

    int updateByExampleSelective(@Param("record") UpmsLogWithBLOBs record, @Param("example") UpmsLogExample example);

    int updateByExampleWithBLOBs(@Param("record") UpmsLogWithBLOBs record, @Param("example") UpmsLogExample example);

    int updateByExample(@Param("record") UpmsLog record, @Param("example") UpmsLogExample example);

    int updateByPrimaryKeySelective(UpmsLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(UpmsLogWithBLOBs record);

    int updateByPrimaryKey(UpmsLog record);
}