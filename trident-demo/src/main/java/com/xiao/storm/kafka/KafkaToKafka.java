package com.xiao.storm.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by xiaoliang
 * 2016.12.19 11:53
 *
 * @Version 1.0
 */
public class KafkaToKafka {
    public static final Logger logger = LoggerFactory.getLogger(KafkaToKafka.class);

    public static void main(String[] args) throws InterruptedException {

            Consumer<String,String> consumer =  KafkaConsumerUtils.consumer();
            consumer.subscribe(Arrays.asList("mobilegame"));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records){
                    logger.debug("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                    Thread.sleep(3000);
                }
            }
    }
}
