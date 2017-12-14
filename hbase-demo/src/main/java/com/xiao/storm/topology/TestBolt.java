package com.xiao.storm.topology;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xiaoliang
 * 2017.01.04 13:44
 *
 * @Version 1.0
 */
public class TestBolt extends BaseBasicBolt {
    public static final Logger logger = LoggerFactory.getLogger(TestBolt.class);

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        logger.info(input.getString(0));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
