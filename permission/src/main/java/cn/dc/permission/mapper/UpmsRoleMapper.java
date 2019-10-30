package cn.dc.permission.mapper;

import cn.dc.permission.pojo.UpmsRole;
import cn.dc.permission.pojo.UpmsRoleExample;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UpmsRoleMapper {
    /*自定义*/
    List<UpmsRole> listAll();

    //将信息添加到role_permission中间表
    @Insert("insert INTO upms_role_permission (role_id,permission_id) VALUES (#{rid},#{perid})")
    int addPerToRole(int rid,String perid);

    //将role_permission中间表中指定role_id的记录全部删除
    @Delete("DELETE FROM upms_role_permission WHERE role_id = #{roleId}")
    int delPerByRId(int roleId);




    int countByExample(UpmsRoleExample example);

    int deleteByExample(UpmsRoleExample example);

    int deleteByPrimaryKey(Integer roleId);

    int insert(UpmsRole record);

    int insertSelective(UpmsRole record);

    List<UpmsRole> selectByExample(UpmsRoleExample example);

    UpmsRole selectByPrimaryKey(Integer roleId);

    int updateByExampleSelective(@Param("record") UpmsRole record, @Param("example") UpmsRoleExample example);

    int updateByExample(@Param("record") UpmsRole record, @Param("example") UpmsRoleExample example);

    int updateByPrimaryKeySelective(UpmsRole record);

    int updateByPrimaryKey(UpmsRole record);
}