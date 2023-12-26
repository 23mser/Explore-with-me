package ru.practicum.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.CreatedHitDto;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatDto;
import ru.practicum.server.mapper.HitMapper;
import ru.practicum.server.mapper.StatMapper;
import ru.practicum.server.model.Hit;
import ru.practicum.server.repository.StatRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final StatRepository statRepository;

    @Override
    @Transactional
    public CreatedHitDto createHitDto(HitDto hitDto) {
        Hit hit = HitMapper.toHit(hitDto);

        return HitMapper.toCreatedHitDto(statRepository.save(hit));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        return StatMapper.toStatDto(statRepository.getStats(uris, start, end, unique));
    }
}