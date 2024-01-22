package ru.practicum.ewm.events.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.events.model.Event;
import ru.practicum.ewm.events.model.EventState;
import ru.practicum.ewm.locations.dto.LocationDto;
import ru.practicum.ewm.locations.model.Location;
import ru.practicum.ewm.users.dto.UserShortDto;
import ru.practicum.ewm.users.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class EventMapper {
    public Event toEvent(NewEventDto eventNewDto, Category category, User initiator) {
        return Event.builder()
                .annotation(eventNewDto.getAnnotation())
                .category(category)
                .confirmedRequests(0)
                .createdOn(LocalDateTime.now())
                .description(eventNewDto.getDescription())
                .eventDate(eventNewDto.getEventDate())
                .initiator(initiator)
                .location(Location.builder()
                        .lat(eventNewDto.getLocation().getLat())
                        .lon(eventNewDto.getLocation().getLon())
                        .build())
                .paid(eventNewDto.getPaid())
                .participantLimit(eventNewDto.getParticipantLimit())
                .requestModeration(eventNewDto.getRequestModeration())
                .title(eventNewDto.getTitle())
                .state(EventState.PENDING)
                .publishedOn(LocalDateTime.now())
                .views(0)
                .build();
    }

    public EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryDto.builder()
                        .id(event.getCategory().getId())
                        .name(event.getCategory().getName())
                        .build())
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .initiator(UserShortDto.builder()
                        .id(event.getInitiator().getId())
                        .name(event.getInitiator().getName())
                        .build())
                .paid(event.getPaid())
                .views(event.getViews())
                .title(event.getTitle())
                .confirmedRequests(event.getConfirmedRequests())
                .build();
    }

    public EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryDto.builder()
                        .id(event.getCategory().getId())
                        .name(event.getCategory().getName())
                        .build())
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .initiator(UserShortDto.builder()
                        .id(event.getInitiator().getId())
                        .name(event.getInitiator().getName())
                        .build())
                .location(LocationDto.builder()
                        .lat(event.getLocation().getLat())
                        .lon(event.getLocation().getLon())
                        .build())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public List<EventShortDto> toEventShortDtoList(List<Event> events) {
        return events.stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    public List<EventFullDto> toEventFullDtoList(List<Event> events) {
        return events.stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }
}