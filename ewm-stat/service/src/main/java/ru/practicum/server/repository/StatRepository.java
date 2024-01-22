package ru.practicum.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.dto.StatDto;
import ru.practicum.server.model.Stat;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<Stat, Long> {
    @Query("select new ru.practicum.dto.StatDto(s.app, s.uri, count(s.ip)) " +
            "from Stat s " +
            "where s.timestamp between ?1 and ?2 " +
            "group by s.app, s.uri " +
            "order by count(s.ip) desc")
    List<StatDto> findAllWithoutUris(LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.dto.StatDto(s.app, s.uri, count(s.ip)) " +
            "from Stat s " +
            "where s.timestamp between ?1 and ?2 " +
            "and s.uri in (?3) " +
            "group by s.app, s.uri " +
            "order by count(s.ip) desc")
    List<StatDto> findAllNotUnique(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select new ru.practicum.dto.StatDto(s.app, s.uri, count(distinct s.ip)) " +
            "from Stat s " +
            "where s.timestamp between ?1 and ?2 " +
            "and s.uri in (?3) " +
            "group by s.app, s.uri " +
            "order by count(distinct s.ip) desc")
    List<StatDto> findAllUnique(LocalDateTime start, LocalDateTime end, List<String> uris);
}