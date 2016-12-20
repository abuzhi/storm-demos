package com.xiao.storm.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;

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
public class KafkaProducerUtils {

    private static final String CONFIG_FILE = "config/kafka.properties";
    private static Properties properties = null;
    private static Producer producer = null;

    private KafkaProducerUtils(){

    }

    public static synchronized void init(String filepath){
        properties = new Properties();
        FileInputStream fis = null;
        try{
            File tmp = new File("");
            String path =tmp.getCanonicalPath()+"/"+ CONFIG_FILE;
            if(filepath!=null && !"".equalsIgnoreCase(filepath)){
                path = tmp.getCanonicalPath()+"/"+ filepath;
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

    public static Producer producer(){
        if(producer==null){
            producer = new KafkaProducer<>(producerProps());
        }
        return producer;
    }

    private static Map<String, Object> producerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getProperty("producer.bootstrap.servers"));
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, properties.getProperty("producer.key.serializer"));
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, properties.getProperty("producer.value.serializer"));
        return props;
    }
}
