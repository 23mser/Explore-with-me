package ru.practicum.ewm.comments.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.comments.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Integer countById(Long commentId);

    List<Comment> findAllByEventIdOrderByCreatedDesc(Long eventId, PageRequest of);

    Optional<Comment> findByEventIdAndAuthorId(Long eventId, Long userId);
}