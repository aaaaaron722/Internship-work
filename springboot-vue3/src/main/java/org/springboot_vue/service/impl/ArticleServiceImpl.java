package org.springboot_vue.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springboot_vue.mapper.ArticleMapper;
import org.springboot_vue.pojo.Article;
import org.springboot_vue.pojo.PageBean;
import org.springboot_vue.pojo.User;
import org.springboot_vue.service.ArticleService;
import org.springboot_vue.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        article.setCreateUser(id);
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        /*創建pageBean對象*/
        PageBean<Article> pageBean = new PageBean<>();
        /*開啟分頁查詢 PageHelper*/
        PageHelper.startPage(pageNum, pageSize);
        /*調用mapper*/
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer UserId = (Integer) map.get("id");
        List<Article> as = articleMapper.list(categoryId, state, UserId);
        Page<Article> p = (Page<Article>) as;
        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;
    }

    @Override
    public void update(Article article) {
        /*更新時間*/
        article.setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        article.setCreateUser(id);
        articleMapper.update(article);
    }

    @Override
    public Article getArticleById(Integer id) {
        return articleMapper.getArticleById(id);
    }

    @Override
    public void deleteById(Integer id) {
        articleMapper.delete(id);
    }

    @Override
    public List<Article> getArticleByKeyword(String keyword) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer UserId = (Integer) map.get("id");
        return articleMapper.getArticleByKeyword(keyword, UserId);
    }
}
