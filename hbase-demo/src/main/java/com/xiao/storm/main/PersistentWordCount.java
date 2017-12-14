package com.xiao.storm.main;

import com.xiao.storm.common.utils.FileUtils;
import com.xiao.storm.topology.HbaseFilterLogCommBolt;
import com.xiao.storm.topology.TestBolt;
import com.xiao.storm.topology.WordCounter;
import com.xiao.storm.topology.WordSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.hbase.bolt.HBaseBolt;
import org.apache.storm.hbase.bolt.mapper.SimpleHBaseMapper;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import java.util.Properties;

/**
 * Created by xiaoliang
 * 2016/9/28 17:08
 * @Version 1.0
 */
public class PersistentWordCount {

    private static final String WORD_SPOUT = "WORD_SPOUT";
    private static final String COUNT_BOLT = "COUNT_BOLT";
    private static final String HBASE_BOLT = "HBASE_BOLT";


    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.put("hadoop.hbase.conf.path","E:/git-repos/storm-demos/hbase-demo/src/main/resources/");

        Config config = new Config();

        WordSpout spout = new WordSpout();
        WordCounter bolt = new WordCounter();
        HbaseFilterLogCommBolt hbase = new HbaseFilterLogCommBolt();
        TestBolt test = new TestBolt();

        // wordSpout ==> countBolt ==> HBaseBolt
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout(WORD_SPOUT, spout, 3);
        builder.setBolt(COUNT_BOLT, bolt, 3).localOrShuffleGrouping(WORD_SPOUT);
        builder.setBolt(HBASE_BOLT, hbase, 3).fieldsGrouping(COUNT_BOLT, new Fields("md5"));
        builder.setBolt("test", test, 3).localOrShuffleGrouping(HBASE_BOLT);


        if (args.length == 0) {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("test", config, builder.createTopology());
        } else {
            config.setNumWorkers(3);
            StormSubmitter.submitTopology("test", config, builder.createTopology());
        }
    }



}
