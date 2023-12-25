package ru.practicum.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.server.model.Hit;
import ru.practicum.server.model.Stat;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<Hit, Long> {

    @Query("SELECT new Stat(h.uri, h.app, COUNT(DISTINCT h.ip)) " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "AND h.uri IN :uris " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    List<Stat> getViewStatsByUniqIp(@Param("uris") List<String> uris,
                                    @Param("start") LocalDateTime start,
                                    @Param("end") LocalDateTime end);

    default List<Stat> getStats(List<String> uris, LocalDateTime start,
                                LocalDateTime end, Boolean uniqueIp) {
        return uniqueIp ? getViewStatsByUniqIp(uris, start, end) : getStats(uris, start, end);
    }

    @Query("SELECT new Stat(h.uri, h.app, COUNT(h.ip)) " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "AND h.uri IN :uris " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    List<Stat> getStats(@Param("uris") List<String> uris,
                                @Param("start") LocalDateTime start,
                                @Param("end") LocalDateTime end);

    @Query("SELECT new Stat(h.uri, h.app, COUNT(h.ip)) " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    List<Stat> getStats(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}