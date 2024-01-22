package ru.practicum.ewm.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.practicum.ewm.comments.dto.CommentDto;
import ru.practicum.ewm.comments.dto.CommentMapper;
import ru.practicum.ewm.comments.model.Comment;
import ru.practicum.ewm.comments.repository.CommentRepository;
import ru.practicum.ewm.events.model.Event;
import ru.practicum.ewm.events.model.EventState;
import ru.practicum.ewm.events.service.EventService;
import ru.practicum.ewm.exceptions.CommentException;
import ru.practicum.ewm.exceptions.EventConflictException;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.requests.model.Request;
import ru.practicum.ewm.requests.model.RequestStatus;
import ru.practicum.ewm.requests.service.RequestService;
import ru.practicum.ewm.users.model.User;
import ru.practicum.ewm.users.service.UserService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final EventService eventService;
    private final RequestService requestService;

    @Transactional
    public CommentDto createComment(Long userId, Long eventId, CommentDto commentDto) {
        User user = userService.getUserByIdIfExist(userId);
        Event event = eventService.getEventByIdIfExist(eventId);

        if (!Objects.equals(event.getState(), EventState.PUBLISHED)) {
            throw new EventConflictException("Статус события должен быть 'PUBLISHED'.");
        }

        Optional<Request> optionalRequest = requestService.getRequestByUserIdAndEventId(userId, eventId);

        if (!Objects.equals(user.getId(), event.getInitiator().getId()) && (optionalRequest.isEmpty()
                || (!Objects.equals(optionalRequest.get().getStatus(), RequestStatus.CONFIRMED)))) {
            throw new CommentException("Пользователь не участвовал в событии и не может оставить комментарий.");
        }

        Optional<Comment> foundComment = commentRepository.findByEventIdAndAuthorId(eventId, userId);

        if (foundComment.isPresent()) {
            throw new CommentException("Пользователь уже оставлял комментарий к событию.");
        }

        Comment comment = CommentMapper.toComment(commentDto, userId, eventId);
        Comment savedComment = commentRepository.save(comment);
        return CommentMapper.toCommentDto(savedComment);
    }

    @Transactional
    public void deleteCommentById(Long commentId, Long userId) {
        Comment comment = getCommentByIdIfExist(commentId);
        checkUserIsAuthorComment(comment.getAuthor().getId(), userId);
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        checkExistCommentById(commentId);
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public CommentDto updateCommentById(Long commentId, Long userId, CommentDto commentDto) {
        Comment foundComment = getCommentByIdIfExist(commentId);
        checkUserIsAuthorComment(foundComment.getAuthor().getId(), userId);
        String newText = commentDto.getText();

        if (StringUtils.hasLength(newText)) {
            foundComment.setText(newText);
        }

        Comment savedComment = commentRepository.save(foundComment);
        return CommentMapper.toCommentDto(savedComment);
    }

    public List<CommentDto> getAllCommentsByEventId(Long eventId, Integer from, Integer size) {
        eventService.getEventByIdIfExist(eventId);
        PageRequest pageRequest = PageRequest.of(from, size);
        List<Comment> comments = commentRepository.findAllByEventIdOrderByCreatedDesc(eventId, pageRequest);
        return CommentMapper.toCommentsDto(comments);
    }

    private void checkUserIsAuthorComment(Long authorId, Long userId) {
        if (!Objects.equals(authorId, userId)) {
            throw new CommentException("Пользователь не является автором комментария и не может его удалить / изменить.");
        }
    }

    private Comment getCommentByIdIfExist(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Комментарий не найден."));
    }

    private void checkExistCommentById(Long commentId) {
        if (commentRepository.countById(commentId) <= 0) {
            throw new NotFoundException("Комментарий не найден.");
        }
    }
}