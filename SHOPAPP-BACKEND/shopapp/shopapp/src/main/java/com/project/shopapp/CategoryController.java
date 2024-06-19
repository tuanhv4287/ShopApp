package com.project.shopapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {
    @GetMapping("")
    public ResponseEntity<String> getAllCateegories(){
        return ResponseEntity.ok("chao ban,haha");
    }
    @PostMapping("")
    public ResponseEntity<String> insertCategory(){
        return ResponseEntity.ok("This is insertCategory");
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
