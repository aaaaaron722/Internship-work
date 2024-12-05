package org.springboot_vue.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springboot_vue.pojo.Article;
import org.springboot_vue.pojo.PageBean;
import org.springboot_vue.service.ArticleService;
import org.springboot_vue.utils.JwtUtil;
import org.springboot_vue.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping/*新增文章*/
    public Result add(@RequestBody @Validated(Article.Add.class) Article article){
        log.info("新增文章:{}", article);
        articleService.add(article);
        return Result.success();
    }

    @GetMapping/*文章分頁查詢*/
    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ) {
        log.info("文章分頁查詢");
        PageBean<Article> pg = articleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(pg);
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Article.Update.class) Article article){
        log.info("更新文章:{}", article);
        articleService.update(article);
        return Result.success();
    }

    @GetMapping("/detail")
    public Result<Article> getArticleById(Integer id){
        log.info("用id查詢文章:{}", id);
        Article articleId = articleService.getArticleById(id);
        return Result.success(articleId);
    }

    @GetMapping("/keyword")
    public Result<List<Article>> getArticleByKeyword(@RequestParam String word){
        log.info("用關鍵字查詢:{}", word);
        List<Article> articleList = articleService.getArticleByKeyword(word);
        return Result.success(articleList);
    }

    @DeleteMapping
    public Result delete(Integer id){
        log.info("刪除文章id:{}", id);
        articleService.deleteById(id);
        return Result.success();
    }
}
