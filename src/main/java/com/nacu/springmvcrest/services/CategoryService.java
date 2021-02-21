package com.nacu.springmvcrest.services;

import com.nacu.springmvcrest.api.model.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryByName(String name);
}
