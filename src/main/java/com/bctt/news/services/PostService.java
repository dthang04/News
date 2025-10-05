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

    // ✅ Lấy tất cả với phân trang
    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    // ✅ Tìm kiếm với phân trang
    public Page<Post> search(String keyword, Pageable pageable) {
        return postRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    }

    // ✅ Thêm / Sửa
    public void save(Post post) {
        postRepository.save(post);
    }

    // ✅ Tìm theo ID
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    // ✅ Xóa
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    // ✅ Nếu vẫn muốn sort thủ công (không dùng Pageable Sort)
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
