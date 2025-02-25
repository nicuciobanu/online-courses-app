package com.online_courses.kafka;

import com.online_courses.kafka.model.OnlineCourseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService<OnlineCourseEvent> {
    private final KafkaProducerConfig kafkaProducerConfig;
    private final KafkaTopicConfig kafkaTopicConfig;

    @Autowired
    public KafkaProducerServiceImpl(KafkaProducerConfig kafkaProducerConfig, KafkaTopicConfig kafkaTopicConfig) {
        this.kafkaProducerConfig = kafkaProducerConfig;
        this.kafkaTopicConfig = kafkaTopicConfig;
    }

    @Override
    public void sendMessage(OnlineCourseEvent message) {
        kafkaProducerConfig.kafkaTemplate().send(kafkaTopicConfig.onlineCoursesEventsTopic().name(), message);
    }
}
