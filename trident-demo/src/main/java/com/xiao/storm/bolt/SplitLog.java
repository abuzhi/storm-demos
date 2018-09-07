package com.xiao.storm.bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by xiaoliang
 * 2017.11.30 11:03
 *
 * @Version 1.0
 */
public class SplitLog extends BaseRichBolt {

    public static final Logger logger = LoggerFactory.getLogger(SplitLog.class);

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {

    }

    @Override
    public void execute(Tuple tuple) {
        String log = tuple.getString(0);
        logger.info(log);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}