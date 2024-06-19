package com.project.shopapp.controller;

import com.project.shopapp.dtos.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/categories")

public class CategoryController {
    @GetMapping("") //http://localhost:8088/api/v1/categories?page=1&limit=10
    public ResponseEntity<String> getAllCateegories(
            @RequestParam("page")  int page,
            @RequestParam("limit")  int limit
    ){
        return ResponseEntity.ok(String.format("getAllCateegories, page = %d, limit = %d",page,limit));
    }
    @PostMapping("")
    public ResponseEntity <?> insertCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result){
        if(result.hasErrors()){
           List<String> errorMessages = result.getFieldErrors()
                   .stream()
                   .map(FieldError::getDefaultMessage)
                   .toList();
           return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.ok("This is insertCategory"+categoryDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id){
        return ResponseEntity.ok("insertCategory with id = "+ id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        return ResponseEntity.ok("deleteCategory with id = "+ id);
    }
}
