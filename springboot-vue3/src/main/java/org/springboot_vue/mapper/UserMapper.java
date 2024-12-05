package org.springboot_vue.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springboot_vue.pojo.User;

import java.time.LocalDateTime;

@Mapper
public interface UserMapper {

    @Select("select * from user where username = #{username}")
    User findByUserName(String username);

    void add(String username, String password, LocalDateTime createTime, LocalDateTime updateTime);

    void update(User user);

    void updateAvatar(String avatarUrl, Integer id);

    void updatePwd(String newPwd, Integer id);

}
