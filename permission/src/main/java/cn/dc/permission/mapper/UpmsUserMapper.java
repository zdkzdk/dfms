package cn.dc.permission.mapper;

import cn.dc.permission.pojo.UpmsRole;
import cn.dc.permission.pojo.UpmsUser;
import cn.dc.permission.pojo.UpmsUserExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UpmsUserMapper {
    /*
    用户拥有的角色
     */
    @Select("SELECT r.role_id roleId,r.name name,r.title title,r.description description  FROM upms_role r JOIN upms_user_role ur ON r.role_id = ur.role_id WHERE ur.user_id = #{userId} ")
    List<UpmsRole> getRolesByUserId(int userId);

    int countByExample(UpmsUserExample example);

    int deleteByExample(UpmsUserExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(UpmsUser record);

    int insertSelective(UpmsUser record);

    List<UpmsUser> selectByExample(UpmsUserExample example);

    UpmsUser selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") UpmsUser record, @Param("example") UpmsUserExample example);

    int updateByExample(@Param("record") UpmsUser record, @Param("example") UpmsUserExample example);

    int updateByPrimaryKeySelective(UpmsUser record);

    int updateByPrimaryKey(UpmsUser record);
}