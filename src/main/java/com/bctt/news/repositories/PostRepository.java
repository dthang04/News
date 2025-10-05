package com.bctt.news.repositories;

import com.bctt.news.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // ✅ Tìm kiếm có phân trang
    Page<Post> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    // ✅ Sắp xếp theo ngày
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<Post> findAllByOrderByCreatedAtAsc(Pageable pageable);

    // ✅ Sắp xếp theo trạng thái
    Page<Post> findAllByOrderByStatusAsc(Pageable pageable);
    Page<Post> findAllByOrderByStatusDesc(Pageable pageable);
}
