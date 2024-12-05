package org.springboot_vue.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springboot_vue.pojo.Category;
import org.springboot_vue.utils.Result;

import java.util.List;

@Mapper
public interface CategoryMapper {
    void add(Category category);

    @Select(" SELECT * FROM category WHERE create_user = #{userId}")
    List<Category> list(Integer userId);

    @Select("select * from category where id = #{id}")
    Category findById(Integer id);

    void update(Category category);

    @Delete("Delete from category where id = #{id}")
    void deleteById(Integer id);
}
