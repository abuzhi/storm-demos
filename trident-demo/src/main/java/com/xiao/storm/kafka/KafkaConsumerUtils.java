package com.xiao.storm.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public static synchronized void init(String filepath){
        properties = new Properties();
        FileInputStream fis = null;
        try{
            String path = CONFIG_FILE;
            if(filepath!=null && !"".equalsIgnoreCase(filepath)){
                path = filepath;
            }
            File pfile = new File(path);
            fis = new FileInputStream(pfile);
            properties.load(fis);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 默认文件
     */
    public static synchronized void init(){
        init("");
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
//        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, properties.getProperty("consumer.key.deserializer"));
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, properties.getProperty("consumer.value.deserializer"));
        return props;
    }
}
