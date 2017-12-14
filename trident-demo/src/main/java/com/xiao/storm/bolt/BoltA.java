package com.xiao.storm.bolt;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

/**
 * Created by xiaoliang on 2016/12/18.
 */
public class BoltA extends BaseBasicBolt {


    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
