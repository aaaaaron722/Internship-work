package org.springboot_vue.mapper;

import org.apache.ibatis.annotations.*;
import org.springboot_vue.pojo.Article;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Insert("insert into article(title, content, cover_img, state, category_id, create_user, create_time, update_time) " +
            "values (#{title}, #{content}, #{coverImg}, #{state}, #{categoryId}, #{createUser}, #{createTime}, #{updateTime})")
    void add(Article article);

    List<Article> list(Integer categoryId, String state, Integer userId);

    void update(Article article);

    @Select("select * from article where id = #{id}")
    Article getArticleById(Integer id);

    @Delete("delete from article where id = #{id}")
    void delete(Integer id);

    List<Article> getArticleByKeyword(String keyword, Integer userId);
}
