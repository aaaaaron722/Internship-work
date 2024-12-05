package org.springboot_vue.service.impl;

import org.springboot_vue.mapper.CategoryMapper;
import org.springboot_vue.pojo.Category;
import org.springboot_vue.service.CategoryService;
import org.springboot_vue.utils.Result;
import org.springboot_vue.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public void add(Category category) {
        /*補充屬性值*/
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer UserId = (Integer) map.get("id");
        category.setCreateUser(UserId);
        categoryMapper.add(category);
    }

    @Override
    public List<Category> list() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer UserId = (Integer) map.get("id");
        return categoryMapper.list(UserId);
    }

    @Override
    public Category findById(Integer id) {
        Category c = categoryMapper.findById(id);
        return c;
    }

    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    @Override
    public void delete(Integer id) {
        categoryMapper.deleteById(id);
    }
}
