package com.online_courses.kafka;

import com.online_courses.kafka.model.OnlineCourseEvent;

public interface KafkaProducerService<T> {
    void sendMessage(T message);
}
