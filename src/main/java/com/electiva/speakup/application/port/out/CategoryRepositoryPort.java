package com.electiva.speakup.application.port.out;

import com.electiva.speakup.domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryPort {
    public Category save(Category category);

    public Optional<Category> findById(Long id);

    public List<Category> findAll();

    public Optional<Category> findByName(String name);
}
