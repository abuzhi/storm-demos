/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xiao.storm.topology;

import com.xiao.storm.common.utils.EncryptMD5;
import com.xiao.storm.utils.Constant;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

import static org.apache.storm.utils.Utils.tuple;

public class WordCounter implements IBasicBolt {


    @SuppressWarnings("rawtypes")
    public void prepare(Map stormConf, TopologyContext context) {
    }

    /*
     * Just output the word value with a count of 1.
     * The HBaseBolt will handle incrementing the counter.
     */
    public void execute(Tuple input, BasicOutputCollector collector) {
        String word = input.getStringByField("word");
        String md5 = EncryptMD5.md5(word);
        Map<String,String> contentMap = new HashMap<String, String>();;
        contentMap.put("md5", md5);
        contentMap.put("word", word);

        Values values = new Values(md5,117,"zxyd",111,contentMap);
        collector.emit(values);
    }

    public void cleanup() {

    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("md5",Constant.GAME_ID,Constant.GAME_NAME, Constant.GAME_BUSINESS, Constant.GAME_LOG_MAP));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

}
