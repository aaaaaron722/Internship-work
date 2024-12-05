package org.springboot_vue.service;

import org.springboot_vue.pojo.Article;
import org.springboot_vue.pojo.PageBean;

import java.util.List;

public interface ArticleService {
    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    void update(Article article);

    Article getArticleById(Integer id);

    void deleteById(Integer id);

    List<Article> getArticleByKeyword(String keyword);
}
