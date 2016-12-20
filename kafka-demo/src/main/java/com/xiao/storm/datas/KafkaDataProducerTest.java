package com.xiao.storm.datas;

/**
 * Created by xiaoliang
 * 2016.12.19 18:04
 *
 * @Version 1.0
 */
public class KafkaDataProducerTest {

    public void testProducerData() throws Exception {
        KafkaDataProducer producer = new KafkaDataProducer();
        producer.produceData("2016-12-20 11:11:11","2016-12-20 12:11:11");
    }
}
