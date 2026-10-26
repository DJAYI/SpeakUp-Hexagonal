package com.electiva.speakup.application.port.in;

import com.electiva.speakup.domain.model.Category;

import java.util.List;

public interface ListCategoriesUseCase {

    public List<Category> list();
}
