package ru.practicum.server.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Stat {
    @Id
    private String uri;
    private String app;
    private Long hits;
}