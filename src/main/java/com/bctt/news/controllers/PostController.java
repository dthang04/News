package com.bctt.news.controllers;

import com.bctt.news.models.Post;
import com.bctt.news.services.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.bctt.news.repositories.CategoryRepository;
import com.bctt.news.models.Category;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final CategoryRepository categoryRepository;


    public PostController(PostService postService , CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.postService = postService;
    }

    @GetMapping
    public String list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts;

        if (keyword != null && !keyword.isBlank()) {
            posts = postService.search(keyword, pageable);
        } else {
            posts = postService.findAll(pageable);
        }


        model.addAttribute("posts", posts.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("type", type);
        model.addAttribute("sort", sort);

        return "posts";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("categories", categoryRepository.findAll());
        return "post_form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Post post) {
        postService.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.findById(id).orElse(new Post()));
        model.addAttribute("categories", categoryRepository.findAll());
        return "post_form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        postService.delete(id);
        return "redirect:/posts";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        Post post = postService.findById(id).orElse(null);
        if (post == null) {
            System.out.println("⚠️ Không tìm thấy bài viết có id = " + id);
            return "redirect:/posts";
        }
        model.addAttribute("post", post);
        return "post_view";
    }
}
