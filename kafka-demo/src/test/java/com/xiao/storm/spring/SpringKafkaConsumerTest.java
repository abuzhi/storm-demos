package com.xiao.storm.spring;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.*;

/**
 * Created by xiaoliang
 * 2017.01.06 14:41
 *
 * @Version 1.0
 */
public class SpringKafkaConsumerTest {

    private static final String TEMPLATE_TOPIC = "templateTopic";
    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, TEMPLATE_TOPIC);

    @Test
    public void testConsumer() throws Exception {
        Map<String, Object> consumerProps = consumerProps();

        DefaultKafkaConsumerFactory<Integer, String> cf =
                new DefaultKafkaConsumerFactory<Integer, String>(consumerProps);
        ContainerProperties containerProperties = new ContainerProperties(TEMPLATE_TOPIC);
        KafkaMessageListenerContainer<Integer, String> container = new KafkaMessageListenerContainer<>(cf, containerProperties);
        final BlockingQueue<ConsumerRecord<Integer, String>> records = new LinkedBlockingQueue<>();

        container.setupMessageListener(new MessageListener<Integer, String>() {

            @Override
            public void onMessage(ConsumerRecord<Integer, String> record) {
                System.out.println(record);
                records.add(record);
            }

        });

    }

    private static Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "test-realtime-01:9092,test-realtime-02:9092,test-realtime-03:9092");
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, properties.getProperty("consumer.group.id"));
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
//        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, properties.getProperty("auto.offset.reset","latest"));
////        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, properties.getProperty("consumer.key.deserializer"));
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, properties.getProperty("consumer.value.deserializer"));
        return props;
    }

}