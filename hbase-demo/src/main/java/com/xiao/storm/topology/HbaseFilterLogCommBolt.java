package com.xiao.storm.topology;

import com.xiao.storm.utils.Constant;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * hbase 过滤
 *
 * @author Administrator
 */
public class HbaseFilterLogCommBolt extends BaseBasicBolt {
    private static final long serialVersionUID = -6833367979089536918L;
    private static final Logger logger = LoggerFactory.getLogger(HbaseFilterLogCommBolt.class);

    @SuppressWarnings("rawtypes")
    @Override
    public void prepare(Map conf, TopologyContext context) {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        int gameid = tuple.getIntegerByField(Constant.GAME_ID);
        String gameName = tuple.getStringByField(Constant.GAME_NAME);
        int business = tuple.getIntegerByField(Constant.GAME_BUSINESS);
        Map<String, String> map = (Map<String, String>) tuple.getValueByField(Constant.GAME_LOG_MAP);
        String md5 = map.get("md5");
        String word = map.get("word");
        Values values = new Values(word);
        collector.emit(values);
    }
}
