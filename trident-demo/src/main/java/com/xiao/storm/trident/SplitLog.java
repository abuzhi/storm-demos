package com.xiao.storm.trident;

import com.alibaba.fastjson.JSONObject;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xiaoliang
 * 2017.11.30 11:03
 *
 * @Version 1.0
 */
public class SplitLog extends BaseFunction {

    public static final Logger logger = LoggerFactory.getLogger(SplitLog.class);

    public void execute(TridentTuple tuple, TridentCollector collector) {
        String log = tuple.getString(0);
        JSONObject obj = JSONObject.parseObject(log);
        logger.info(log);
        collector.emit(new Values("count", log));

    }
}