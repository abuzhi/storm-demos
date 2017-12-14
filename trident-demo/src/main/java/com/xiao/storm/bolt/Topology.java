package com.xiao.storm.bolt;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by xiaoliang
 * 2016.12.09 16:40
 *
 * @Version 1.0
 */
public class Topology {
    public static void main(String[] args) {
        // 配置文件
        Properties properties = new Properties();
        FileInputStream fis = null;
        try{
            File pfile = new File("D:\\workspaces\\GitRepos\\storm-demos\\kafka-demo\\config\\config.properties");
            fis = new FileInputStream(pfile);
            properties.load(fis);
        }catch(Exception e){
            e.printStackTrace();
        }

        TopologyBuilder builder = new TopologyBuilder();
        //设置喷发节点并分配并发数，该并发数将会控制该对象在集群中的线程数（6个）
        String servers = (String)properties.get("kafka.bootstrap.servers");
        String topic = (String)properties.get("kafka.topic");
        String groupId =(String)properties.get("kafka.group");

        KafkaSpoutConfig<String, String> spoutConfig = KafkaSpoutConfig
                .builder(servers,topic)
                .setGroupId(groupId)
                .build();
        KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);

        builder.setSpout("kafkaSpout", kafkaSpout, Integer.valueOf(properties.getProperty("kafkaSpout","1")));
        builder.setBolt("BoltA", new BoltA(),
                Integer.valueOf(properties.getProperty("BoltA","16")))
                .setNumTasks(32)
                .localOrShuffleGrouping("kafkaSpout");

        Config conf = new Config();
        conf.setDebug(false);
        // Topology 名称
        String topologyName = properties.getProperty("topology_name");
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(topologyName, conf, builder.createTopology());


    }
}
