package com.xiao.storm.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by xiaoliang
 * 2016.12.07 16:34
 *
 * @Version 1.0
 */
public class KafkaProducerUtilsTest {

    @Test
    public void testConsumer() throws Exception {
        Consumer<String,String> consumer = KafkaConsumerUtils.consumer();
        TopicPartition partition0 = new TopicPartition("test123", 0);
        consumer.seekToBeginning(Arrays.asList(partition0));
        consumer.assign(Arrays.asList(partition0));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
        }
    }

    @Test
    public void testProducer() throws Exception {
        Producer<String,String> producer = KafkaProducerUtils.producer();
        int i =43;
        while(true){
            producer.send(new ProducerRecord<String, String>("test123","0","producer"+i));
            i ++;
            Thread.sleep(3000);
        }
    }
}