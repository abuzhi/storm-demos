package com.xiao.storm.kafka;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * Created by xiaoliang
 * 2016.12.16 11:56
 *
 * @Version 1.0
 */
public class ProducerThread implements Runnable {

    @Override
    public void run() {
        Producer<String,String> producer = KafkaProducerUtils.producer();
        while(true){
            producer.send(new ProducerRecord<String, String>("test123",String.valueOf(RandomUtils.nextInt(6)),"producer"+RandomUtils.nextInt(10000)));
        }
    }
}
