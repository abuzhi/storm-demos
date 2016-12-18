package com.xiao.storm.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by xiaoliang
 * 2016.12.06 15:54
 *
 * @Version 1.0
 */
public class KafkaConsumerUtils {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerUtils.class);

    private static final String CONFIG_FILE = "config/kafka.properties";
    private static Properties properties = null;
    private static Consumer consumer = null;

    private KafkaConsumerUtils(){

    }

    static {
        init();
    }

    private static void init(){
        properties = new Properties();
        FileInputStream fis = null;
        try{
            File pfile = new File(CONFIG_FILE);
            fis = new FileInputStream(pfile);
            properties.load(fis);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Consumer consumer(){
        if(consumer==null){
            consumer = new KafkaConsumer(consumerProps());
        }
        return consumer;
    }

    private static Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getProperty("consumer.bootstrap.servers"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, properties.getProperty("consumer.group.id"));
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, properties.getProperty("auto.offset.reset","latest"));
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, properties.getProperty("consumer.key.deserializer"));
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, properties.getProperty("consumer.value.deserializer"));
        return props;
    }
}
