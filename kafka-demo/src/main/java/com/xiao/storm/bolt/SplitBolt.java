package com.xiao.storm.bolt;

import com.xiao.storm.business.Constants;
import org.apache.commons.lang.StringUtils;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xiaoliang on 2016/12/18.
 */
public class SplitBolt extends BaseBasicBolt {

    public static final Logger logger = LoggerFactory.getLogger(SplitBolt.class);

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        String key = input.getStringByField("value");

        String taskData = input.getStringByField(Constants.ZK_TASK_DATA);
        if (StringUtils.isNotBlank(taskData)) {
            // TODO: 2018/9/7 task reload
        }

        String topicData = input.getStringByField(Constants.ZK_TOPIC_DATA);
        if (StringUtils.isNotBlank(topicData)) {
            // TODO: 2018/9/7 topic reload
        }
        collector.emit(new Values("ok"));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("success"));
    }
}
