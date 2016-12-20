package com.xiao.storm.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by xiaoliang
 * 2016.12.19 13:49
 *
 * @Version 1.0
 */
public class KafkaToKafkaTest {

    @Test
    public void testMain() throws Exception {
        Consumer<String,String> consumer =  KafkaConsumerUtils.consumer();
        consumer.subscribe(Arrays.asList("mobilegame"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records){
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                Thread.sleep(3000);
            }
        }

    }
}