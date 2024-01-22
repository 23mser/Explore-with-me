package ru.practicum.ewm.requests.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.events.dto.EventStatusUpdateRequest;
import ru.practicum.ewm.events.dto.EventStatusUpdateResponse;
import ru.practicum.ewm.events.model.Event;
import ru.practicum.ewm.events.model.EventState;
import ru.practicum.ewm.events.repository.EventRepository;
import ru.practicum.ewm.exceptions.EventConflictException;
import ru.practicum.ewm.exceptions.IncorrectRequestException;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.requests.dto.RequestDto;
import ru.practicum.ewm.requests.dto.RequestMapper;
import ru.practicum.ewm.requests.model.Request;
import ru.practicum.ewm.requests.model.RequestStatus;
import ru.practicum.ewm.requests.repository.RequestRepository;
import ru.practicum.ewm.users.model.User;
import ru.practicum.ewm.users.service.UserService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final UserService userService;
    private final EventRepository eventRepository;

    @Transactional
    public RequestDto createRequest(Long userId, Long eventId) {
        User user = userService.getUserByIdIfExist(userId);
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("Событие не найдено"));

        if (Objects.equals(userId, event.getInitiator().getId())) {
            throw new EventConflictException("Пользователь - автор события и не может создать запрос");
        }

        if (!requestRepository.findByRequesterIdAndEventId(userId, eventId).isEmpty()) {
            throw new EventConflictException("Нельзя добавить повторный запрос на участие в событии");
        }

        if (!Objects.equals(event.getState(), EventState.PUBLISHED)) {
            throw new EventConflictException("Нельзя добавить запрос на участие в неопубликованном событии");
        }

        Request request = new Request(null, LocalDateTime.now(), event, user, RequestStatus.PENDING);

        int confirmed = event.getConfirmedRequests();
        int limit = event.getParticipantLimit();

        if (limit == 0) {
            event.setConfirmedRequests(confirmed + 1);
            eventRepository.save(event);
            request.setStatus(RequestStatus.CONFIRMED);
        } else if (confirmed < limit) {
            if (!event.getRequestModeration()) {
                event.setConfirmedRequests(confirmed + 1);
                eventRepository.save(event);
                request.setStatus(RequestStatus.PENDING);
            }
        } else {
            throw new EventConflictException("Свободных мест для записи на событие нет");
        }

        Request savedRequest = requestRepository.save(request);
        return RequestMapper.toDto(savedRequest);
    }

    public List<RequestDto> getAllRequestsByUserId(Long userId) {
        userService.checkExistUserById(userId);

        List<Request> requests = requestRepository.findAllByRequesterId(userId);
        return RequestMapper.toDtoList(requests);
    }

    @Transactional
    public RequestDto cancelledRequestById(Long userId, Long requestId) {
        userService.checkExistUserById(userId);

        Request request = requestRepository.findByIdAndRequesterId(requestId, userId).orElseThrow(() ->
                new NotFoundException("Запрос не найден"));

        if ((Objects.equals(request.getStatus(), RequestStatus.CANCELED))
                || (Objects.equals(request.getStatus(), RequestStatus.REJECTED))) {
            throw new IncorrectRequestException("Запрос уже отменен");
        }

        request.setStatus(RequestStatus.CANCELED);
        Request savedRequest = requestRepository.save(request);
        return RequestMapper.toDto(savedRequest);
    }

    @Transactional
    public EventStatusUpdateResponse updateStatusRequestByEventId(Long userId, Long eventId,
                                                                  EventStatusUpdateRequest request) {
        userService.checkExistUserById(userId);
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException("Запрос не найден"));


        if (event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new EventConflictException("Достигнут лимит заявок на участие в событии");
        }

        List<Request> confirmed = new ArrayList<>();
        List<Request> rejected = new ArrayList<>();

        List<Request> requests = requestRepository.findAllById(request.getRequestIds());
        for (Request item : requests) {
            if (Objects.equals(item.getStatus(), RequestStatus.PENDING)) {
                if (event.getParticipantLimit() == 0) {
                    item.setStatus(RequestStatus.CONFIRMED);
                    event.setConfirmedRequests(event.getConfirmedRequests() + 1);

                } else if (event.getParticipantLimit() > event.getConfirmedRequests()) {
                    if (!event.getRequestModeration() ||
                            (Objects.equals(request.getStatus(), RequestStatus.CONFIRMED))) {
                        item.setStatus(RequestStatus.CONFIRMED);
                        event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                        confirmed.add(item);
                    } else {
                        item.setStatus(RequestStatus.REJECTED);
                        rejected.add(item);
                    }
                } else {
                    throw new EventConflictException("Статус можно изменить только у заявок в статусе WAITING");
                }

            }
        }
        eventRepository.save(event);

        return new EventStatusUpdateResponse(confirmed.stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList()),
                rejected.stream()
                        .map(RequestMapper::toDto)
                        .collect(Collectors.toList()));
    }

    public List<RequestDto> getAllParticipationRequestsByEventId(Long userId, Long eventId) {
        userService.checkExistUserById(userId);

        if (!eventRepository.existsByInitiatorIdAndId(userId, eventId)) {
            throw new EventConflictException("Пользователь не инициатор события");
        }

        List<Request> requests = requestRepository.findAllByEventId(eventId);
        return RequestMapper.toDtoList(requests);
    }

    public Optional<Request> getRequestByUserIdAndEventId(Long userId, Long eventId) {
        return requestRepository.findByEventIdAndRequesterId(eventId, userId);
    }
}