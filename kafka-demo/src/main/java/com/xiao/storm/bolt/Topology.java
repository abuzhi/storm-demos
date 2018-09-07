package com.xiao.storm.bolt;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
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
        if(args==null || args.length!=1){
            System.out.println(" no config file ...");
            System.exit(0);
        }

        // 配置文件
        Properties properties = new Properties();
        FileInputStream fis = null;
        String confFile = args[0].toString();

        try{
            File pfile = new File(confFile);
            fis = new FileInputStream(pfile);
            properties.load(fis);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return;
        }

        TopologyBuilder builder = new TopologyBuilder();
        String kafka = properties.getProperty("kafka.bootstrap");
        String topic = properties.getProperty("kafka.topic");
        String groupId = properties.getProperty("kafka.groupId");

        String zkHost = properties.getProperty("zk.host");
        String path = properties.getProperty("zk.path");

        KafkaSpoutConfig<String, String> kafkaConf = KafkaSpoutConfig
                .builder(kafka,topic)
                .setGroupId(groupId)
                .build();
        KafkaSpout kafkaSpout = new KafkaSpout(kafkaConf);
        ZkListenerSpout zkListenerSpout = new ZkListenerSpout(zkHost,path);

        builder.setSpout("kafkaSpout", kafkaSpout, Integer.valueOf(properties.getProperty("kafkaSpout","1")));
        builder.setSpout("zkspout",zkListenerSpout,1);
//        builder.setBolt("zkListener",new ZkListenerBolt(),1).directGrouping("zkspout");

        builder.setBolt("split", new SplitBolt(),
                Integer.valueOf(properties.getProperty("BoltA","16")))
                .localOrShuffleGrouping("zkspout")
                .localOrShuffleGrouping("kafkaSpout")
        ;

        Config conf = new Config();
        conf.setDebug(false);
        conf.setNumWorkers(Integer.valueOf(properties.getProperty("numWorkers","8")));
        conf.setMaxSpoutPending(Integer.valueOf(properties.getProperty("maxSpoutPending","5000")));
        conf.setMessageTimeoutSecs(Integer.valueOf(properties.getProperty("messageTimeoutSecs","30")));

        // Topology 名称
        String topologyName = properties.getProperty("topology_name");
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(topologyName, conf, builder.createTopology());
    }
}
