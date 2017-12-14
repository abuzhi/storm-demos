package com.xiao.storm.bolt;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Created by xiaoliang
 * 2017.11.30 10:48
 *
 * @Version 1.0
 */
public class BoltTopology {

    public static final Logger logger = LoggerFactory.getLogger(BoltTopology.class);


    public static void main(String[] args) {
        BoltTopology main = new BoltTopology();
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
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", kafkaSpout,8);
        builder.setBolt("split",new SplitLog(),1).localOrShuffleGrouping("spout");

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(topologyName, conf,  builder.createTopology());
    }


}
