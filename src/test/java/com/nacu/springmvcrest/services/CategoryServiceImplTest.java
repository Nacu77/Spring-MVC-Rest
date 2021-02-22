package com.nacu.springmvcrest.services;

import com.nacu.springmvcrest.api.mapper.CategoryMapper;
import com.nacu.springmvcrest.api.model.CategoryDTO;
import com.nacu.springmvcrest.domain.Category;
import com.nacu.springmvcrest.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {

    private final Long ID = 2L;
    private final String NAME = "Jimmy";

    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
    }

    @Test
    void getAllCategories() {
        List<Category> categories = Arrays.asList(new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();

        assertEquals(2, categoryDTOS.size());
    }

    @Test
    void getCategoryByName() {
        Category category = Category.builder().id(ID).name(NAME).build();
        when(categoryRepository.findByName(anyString())).thenReturn(category);
        CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);

        assertEquals(ID, categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
    }
}