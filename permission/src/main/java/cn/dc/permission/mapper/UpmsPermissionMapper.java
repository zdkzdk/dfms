package cn.dc.permission.mapper;

import cn.dc.permission.pojo.UpmsPermission;
import cn.dc.permission.pojo.UpmsPermissionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

public interface UpmsPermissionMapper {
    /*自定义
     * */
    //根据roleId查权限
    @Select("SELECT p.permission_id permissionId,p.pid,p.name,p.permission_value permissionValue FROM upms_role_permission rp " +
            "JOIN upms_permission p ON rp.permission_id = p.permission_id " +
            "WHERE rp.role_id = #{roleId}")
    Set<UpmsPermission> findPerByRoleId(int roleId);

    int countByExample(UpmsPermissionExample example);

    int deleteByExample(UpmsPermissionExample example);

    int deleteByPrimaryKey(Integer permissionId);

    int insert(UpmsPermission record);

    int insertSelective(UpmsPermission record);

    List<UpmsPermission> selectByExample(UpmsPermissionExample example);

    UpmsPermission selectByPrimaryKey(Integer permissionId);

    int updateByExampleSelective(@Param("record") UpmsPermission record, @Param("example") UpmsPermissionExample example);

    int updateByExample(@Param("record") UpmsPermission record, @Param("example") UpmsPermissionExample example);

    int updateByPrimaryKeySelective(UpmsPermission record);

    int updateByPrimaryKey(UpmsPermission record);
}