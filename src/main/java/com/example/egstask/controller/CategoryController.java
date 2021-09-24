package com.example.egstask.controller;

import com.example.egstask.model.dto.request.CategoryReq;
import com.example.egstask.model.dto.response.CategoryRes;
import com.example.egstask.model.dto.response.Response;
import com.example.egstask.model.entity.Category;
import com.example.egstask.model.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Response<CategoryRes>> createCategory(HttpServletRequest request,
                                                                @Valid @RequestBody CategoryReq categoryReq) {
        CategoryRes category = new CategoryRes(categoryService.save(new Category(categoryReq)));
        Response<CategoryRes> response =
                new Response<CategoryRes>("OK", request.getRequestURI()).setMessage(category);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{category_id}")
    public ResponseEntity<Response<CategoryRes>> getCategory(HttpServletRequest request,
                                                             @PathVariable(name = "category_id") Long categoryId) {
        Category category = categoryService.findById(categoryId);
        Response<CategoryRes> response =
                new Response<CategoryRes>("OK", request.getRequestURI()).setMessage(new CategoryRes(category));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{category_id}")
    public ResponseEntity<Response<CategoryRes>> updateCategory(HttpServletRequest request,
                                                             @PathVariable(name = "category_id") Long categoryId,
                                                             @Valid @RequestBody CategoryReq categoryReq) {
        CategoryRes category = new CategoryRes(categoryService.update(categoryId, new Category(categoryReq)));
        Response<CategoryRes> response =
                new Response<CategoryRes>("OK", request.getRequestURI()).setMessage(category);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{category_id}")
    public ResponseEntity<Response> deleteCategory(HttpServletRequest request,
                                                   @PathVariable(name = "category_id") Long categoryId) {
        categoryService.delete(categoryId);
        Response response = new Response<>("OK", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
