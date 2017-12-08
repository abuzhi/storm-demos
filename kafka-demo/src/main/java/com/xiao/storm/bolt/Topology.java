package com.xiao.storm.bolt;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
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
        String zkhost = (String)properties.get("kafka.zookeeper");
        String topic = (String)properties.get("kafka.topic");
        String groupId =(String)properties.get("kafka.groupId");
        ZkHosts zkHosts = new ZkHosts(zkhost);//kafaka所在的zookeeper
        SpoutConfig spoutConfig = new SpoutConfig(zkHosts, topic, "/storm-kafka/mobilegame",groupId);
        spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
        KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);
        builder.setSpout("kafkaSpout", kafkaSpout, Integer.valueOf(properties.getProperty("kafkaConsumerSpout","1")));
        builder.setBolt("BoltA", new BoltA(),
                Integer.valueOf(properties.getProperty("BoltA","16"))).localOrShuffleGrouping("kafkaSpout");

        Config conf = new Config();
        conf.setDebug(false);
        conf.setNumWorkers(Integer.valueOf(properties.getProperty("numWorkers","8")));
        conf.setMaxSpoutPending(Integer.valueOf(properties.getProperty("maxSpoutPending","5000")));
        conf.setMessageTimeoutSecs(Integer.valueOf(properties.getProperty("messageTimeoutSecs","60")));

        // Topology 名称
        String topologyName = properties.getProperty("topology_name");
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(topologyName, conf, builder.createTopology());
    }
}
