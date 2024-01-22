package ru.practicum.ewm.comments.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.comments.model.Comment;
import ru.practicum.ewm.events.model.Event;
import ru.practicum.ewm.users.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CommentMapper {
    public CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .created(comment.getCreated())
                .event(comment.getEvent().getId())
                .text(comment.getText())
                .author(comment.getAuthor().getId())
                .build();
    }

    public Comment toComment(CommentDto commentDto, Long userId, Long eventId) {
        return Comment.builder()
                .text(commentDto.getText())
                .author(User.builder().id(userId).build())
                .event(Event.builder().id(eventId).build())
                .created(LocalDateTime.now())
                .build();
    }

    public List<CommentDto> toCommentsDto(List<Comment> comments) {
        return comments.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }
}