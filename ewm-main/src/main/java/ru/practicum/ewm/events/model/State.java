package ru.practicum.ewm.events.model;

public enum State {
    PUBLISHED,
    PENDING,
    CANCELED,
    CANCEL_REVIEW,
    SEND_TO_REVIEW,
    PUBLISH_EVENT,
    REJECT_EVENT,
    CONFIRMED,
    REJECTED
}