package ru.practicum.server.mapper;

import ru.practicum.dto.StatDto;
import ru.practicum.server.model.Stat;

import java.util.List;
import java.util.stream.Collectors;

public class StatMapper {

    private StatMapper() {

    }

    public static List<StatDto> toStatDto(List<Stat> stats) {
        return stats
                .stream()
                .map(stat -> new StatDto(stat.getApp(), stat.getUri(), stat.getHits()))
                .collect(Collectors.toList());
    }
}