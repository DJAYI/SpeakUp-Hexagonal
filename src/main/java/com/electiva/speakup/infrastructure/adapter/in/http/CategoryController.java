package com.electiva.speakup.infrastructure.adapter.in.http;

import com.electiva.speakup.application.port.in.CreateCategoryUseCase;
import com.electiva.speakup.application.port.in.ListCategoriesUseCase;
import com.electiva.speakup.application.port.in.RetrieveQuestionsFromCategoryUseCase;
import com.electiva.speakup.domain.model.Category;
import com.electiva.speakup.domain.model.Question;
import com.electiva.speakup.infrastructure.adapter.in.http.dto.CreateCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final ListCategoriesUseCase listCategoriesUseCase;
    private final CreateCategoryUseCase createCategoryUseCase;
    private final RetrieveQuestionsFromCategoryUseCase retrieveQuestionsFromCategoryUseCase;


    @GetMapping
    public ResponseEntity<List<Category>> listCategories() {
        return ResponseEntity.ok(listCategoriesUseCase.list());
    }

    @GetMapping("/{categoryId}/questions")
    public ResponseEntity<List<Question>> retrieveQuestions(@PathVariable Long categoryId) {
        return ResponseEntity.ok(retrieveQuestionsFromCategoryUseCase.retrieve(categoryId));
    }

    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody CreateCategoryRequest request) {
        createCategoryUseCase.create(request.name(), request.description());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
