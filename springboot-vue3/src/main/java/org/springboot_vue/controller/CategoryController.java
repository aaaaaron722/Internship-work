package org.springboot_vue.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springboot_vue.pojo.Category;
import org.springboot_vue.service.CategoryService;
import org.springboot_vue.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result<List<Category>> list(){
        log.info("查詢分類數據");
        List<Category> list = categoryService.list();
        return Result.success(list);
    }

    @PostMapping
    public Result add(@RequestBody @Validated(Category.Add.class) Category category) {
        log.info("新增分類: {}", category);
        categoryService.add(category);
        return Result.success();
    }

    @GetMapping("/detail")
    public Result<Category> detail(Integer id) {
        log.info("查詢分類詳情", id);
        Category c = categoryService.findById(id);
        return Result.success(c);
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category) {
        log.info("更新文章數據:{}", category);
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id) {
        log.info("刪除分類id:{}", id);
        categoryService.delete(id);
        return Result.success();
    }
}
