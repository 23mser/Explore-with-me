package ru.practicum.server.mapper;

import ru.practicum.dto.HitDto;
import ru.practicum.server.model.Stat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatMapper {

    public static Stat toStat(HitDto hitDto) {
        LocalDateTime dateTime = LocalDateTime.from(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .parse(hitDto.getTimestamp()));

        return Stat.builder()
                .app(hitDto.getApp())
                .uri(hitDto.getUri())
                .ip(hitDto.getIp())
                .timestamp(dateTime)
                .build();
    }
}