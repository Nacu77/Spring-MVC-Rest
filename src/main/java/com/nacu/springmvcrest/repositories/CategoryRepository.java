package com.nacu.springmvcrest.repositories;

import com.nacu.springmvcrest.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
