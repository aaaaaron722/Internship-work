package org.springboot_vue.service;

import org.springboot_vue.pojo.Category;
import org.springboot_vue.utils.Result;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    void add(Category category);

    List<Category> list();

    Category findById(Integer id);

    void update(Category category);

    void delete(Integer id);
}
