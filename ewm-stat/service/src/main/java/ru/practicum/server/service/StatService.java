package ru.practicum.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatDto;
import ru.practicum.server.handler.NoValidParameterRequest;
import ru.practicum.server.mapper.StatMapper;
import ru.practicum.server.model.Stat;
import ru.practicum.server.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatService {
    private final StatRepository repository;

    public void saveRecord(HitDto hitDto) {
        Stat stat = StatMapper.toStat(hitDto);
        repository.save(stat);
    }

    public List<StatDto> getAllStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        checkEndIsAfterStart(start, end);

        if (uris == null || uris.isEmpty()) {
            return repository.findAllWithoutUris(start, end);
        }
        if (unique) {
            return repository.findAllUnique(start, end, uris);
        }
        return repository.findAllNotUnique(start, end, uris);
    }

    private void checkEndIsAfterStart(LocalDateTime start, LocalDateTime end) {
        if (!end.isAfter(start)) {
            throw new NoValidParameterRequest("Неверная дата");
        }
    }
}