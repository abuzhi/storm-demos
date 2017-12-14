package com.xiao.storm.trident;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.LocalAssignment;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.kafka.spout.trident.KafkaTridentSpoutOpaque;
import org.apache.storm.kafka.trident.TridentKafkaStateFactory;
import org.apache.storm.kafka.trident.mapper.TridentTupleToKafkaMapper;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.testing.MemoryMapState;
import org.apache.storm.trident.testing.Split;
import org.apache.storm.trident.topology.TridentTopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by xiaoliang
 * 2017.11.30 10:48
 *
 * @Version 1.0
 */
public class MainTopology {

    public static final Logger logger = LoggerFactory.getLogger(MainTopology.class);


    public static void main(String[] args) {
        MainTopology main = new MainTopology();
        main.calculate(new Properties());

    }

    public void calculate(Properties properties) {
        String servers = "realtime-test-01:9092,realtime-test-02:9092,realtime-test-03:9092,realtime-test-04:9092,realtime-test-05:9092,realtime-test-06:9092,realtime-test-07:9092,realtime-test-08:9092";
        String topic = "test_kudu_topic";
        String groupid = "test-trident-demo";

        KafkaSpoutConfig kafkaSpoutConfig = null;


        if (topic.contains("*")) {
            kafkaSpoutConfig = KafkaSpoutConfig.builder(servers, Pattern.compile(topic))
                    .setGroupId(groupid)
                    .build();
        } else {
            List<String> tops = Arrays.asList(topic.split(","));
            kafkaSpoutConfig = KafkaSpoutConfig.builder(servers, tops)
                    .setGroupId(groupid)
                    .build();
        }

        Config conf = new Config();
        conf.setDebug(false);
        String topologyName = "test";

        KafkaSpout kafkaSpout = new KafkaSpout(kafkaSpoutConfig);

        TridentTopology tridentTopology = new TridentTopology();
        tridentTopology.newStream("kafkaSpout", kafkaSpout)
                .parallelismHint(1)
                .each(new Fields("value"),new SplitLog(),new Fields("test"))
                .parallelismHint(1);
        LocalCluster cluster1 = new LocalCluster();
        cluster1.submitTopology(topologyName, conf, tridentTopology.build());

        if (Boolean.valueOf(properties.getProperty("debug.mode", "true"))) {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("test-trident", conf, tridentTopology.build());
        } else {
            try {
                StormSubmitter.submitTopology("test-trident", conf, tridentTopology.build());
            } catch (AlreadyAliveException e) {
                logger.error("moblie CalculateTopology init error : ", e);
            } catch (InvalidTopologyException e) {
                logger.error("moblie CalculateTopology init error : ", e);
            } catch (AuthorizationException e) {
                logger.error("moblie CalculateTopology init error : ", e);
            }
        }

    }


}
