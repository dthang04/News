package com.bctt.news.controllers;

import com.bctt.news.models.Category;
import com.bctt.news.repositories.CategoryRepository;
import com.bctt.news.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "categories";
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("category", new Category());
        return "category_form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/categories";
    }

    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        Optional<Category> existing = categoryRepository.findByName(category.getName());
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Category saved = categoryRepository.save(category);
        return ResponseEntity.ok(saved);
    }
}
