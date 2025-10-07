package com.bctt.news.services;

import com.bctt.news.models.Post;
import com.bctt.news.repositories.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Page<Post> search(String keyword, Pageable pageable) {
        return postRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    }

    public void save(Post post) {
        postRepository.save(post);
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public Page<Post> sortByDate(String order, Pageable pageable) {
        if ("asc".equalsIgnoreCase(order)) {
            return postRepository.findAllByOrderByCreatedAtAsc(pageable);
        } else {
            return postRepository.findAllByOrderByCreatedAtDesc(pageable);
        }
    }

    public Page<Post> sortByStatus(String order, Pageable pageable) {
        if ("desc".equalsIgnoreCase(order)) {
            return postRepository.findAllByOrderByStatusDesc(pageable);
        } else {
            return postRepository.findAllByOrderByStatusAsc(pageable);
        }
    }
}
