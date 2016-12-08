package com.xiao.storm.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by xiaoliang
 * 2016.12.07 16:34
 *
 * @Version 1.0
 */
public class KafkaProducerUtilsTest {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerUtils.class);

    @Test
    public void testAutoCommit() throws Exception {
        KafkaProducerUtils kafka = new KafkaProducerUtils();

        logger.info("Start auto");
        ContainerProperties containerProps = new ContainerProperties("mobilegame");

        KafkaMessageListenerContainer<Integer, String> container = kafka.createContainer(containerProps);
        final CountDownLatch latch = new CountDownLatch(4);
        containerProps.setMessageListener(new MessageListener<Integer, String>() {

            @Override
            public void onMessage(ConsumerRecord<Integer, String> message) {
                logger.info("received: " + message);
                latch.countDown();
            }

        });
        container.setBeanName("testAuto");
        container.start();
        Thread.sleep(1000); // wait a bit for the container to start
        KafkaTemplate<Integer, String> template = kafka.createTemplate();
        template.setDefaultTopic("test_topic123");
        template.sendDefault(0, "foo");
        template.sendDefault(2, "bar");
        template.sendDefault(0, "baz");
        template.sendDefault(2, "qux");
        template.flush();
        assertTrue(latch.await(60, TimeUnit.SECONDS));
        container.stop();
        logger.info("Stop auto");

    }

    @Test
    public void testProducer() throws Exception {
        KafkaProducerUtils kafka = new KafkaProducerUtils();
//        KafkaTemplate<Integer, String>  template = kafka.createTemplate();
//        template.send("test_kafka123","test123456");
//        assertNotNull("abc");

        KafkaTemplate<Integer, String> template =  kafka.createTemplate();
        template.setDefaultTopic("test_kafka123");
        template.sendDefault(0, "foo");
        template.sendDefault(2, "bar");
        template.sendDefault(0, "baz");
        template.sendDefault(2, "qux");
        template.flush();

    }

    @Test
    public void createProducer() {
        Properties props = new Properties();
//        props.put("metadata.broker.list", "10.14.251.70:9092");
        props.put("bootstrap.servers", "test-realtime-01:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
            producer.send(new ProducerRecord<String, String>("mobilegame","test123","test1234566"));
        producer.close();
    }

}