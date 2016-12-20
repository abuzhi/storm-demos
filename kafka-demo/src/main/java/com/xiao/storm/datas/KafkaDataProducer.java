package com.xiao.storm.datas;

import com.xiao.storm.common.utils.RandomUtil;
import com.xiao.storm.kafka.KafkaProducerUtils;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xiaoliang
 * 2016.12.19 18:04
 *
 * @Version 1.0
 */
public class KafkaDataProducer {

    public static final Logger logger = LoggerFactory.getLogger(KafkaDataProducer.class);

    public void produceData(String beginDate,String endDate){



        DataEntity data = new DataEntity();
        DataFactory.init("src/test/resources/datas.properties");
        KafkaProducerUtils.init("config/kafka.properties");
        DateTime dateTime = new DateTime();

        while(true){
            long ms = RandomUtil.random(dateTime.getMillis(),dateTime.plusMinutes(5).getMillis());
            DateTime d = new DateTime(ms);
            data.setLogtime(d.toString("yyyy-MM-dd HH:mm:ss"));
            data.setAccount(RandomUtil.randomString(15));
            data.setUserid(RandomUtil.randomString(15));
            data.setRoleid(RandomUtil.randomString(15));
            data.setOs(String.valueOf(RandomUtil.random(0,3)));
            data.setPlatform(RandomUtil.randomString(10));
            data.setServerid(String.valueOf(RandomUtil.random(1,999)));
            data.setCash(String.valueOf(RandomUtil.random(1,10000)));
            data.setYuanbao(String.valueOf(RandomUtil.random(1,10000)));
            data.setCurrentuser(String.valueOf(RandomUtil.random(100,10000)));
            data.setLoginplaying(String.valueOf(RandomUtil.random(10,5000)));
            data.setLoginqueuing(String.valueOf(RandomUtil.random(10,100)));
            data.setMac(RandomUtil.randomString(20));

            Producer<String,String> producer = KafkaProducerUtils.producer();

            String rolelogin = DataFactory.rolelogin(data);
            producer.send(new ProducerRecord<String, String>("test_mobilegame",rolelogin));
            logger.info(rolelogin);

            String createrole = DataFactory.createRole(data);
            producer.send(new ProducerRecord<String, String>("test_mobilegame",createrole));
            logger.info(createrole);


            String addcash = DataFactory.addcash(data);
            producer.send(new ProducerRecord<String, String>("test_mobilegame",addcash));
            logger.info(addcash);


            String costyuanbao = DataFactory.costyuanbao(data);
            producer.send(new ProducerRecord<String, String>("test_mobilegame",costyuanbao));
            logger.info(costyuanbao);


            DateTime now = new DateTime();
            if(now.isAfter(dateTime.plusMinutes(5).toInstant())){
                data.setLogtime(now.toString("yyyy-MM-dd HH:mm:ss"));
                String online = DataFactory.onlineuser(data);
                producer.send(new ProducerRecord<String, String>("test_mobilegame",online));
                logger.info(online);
                dateTime = dateTime.plusMinutes(5);
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
