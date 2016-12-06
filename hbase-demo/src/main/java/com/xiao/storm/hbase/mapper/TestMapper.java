package com.xiao.storm.hbase.mapper;

import org.apache.storm.hbase.bolt.mapper.HBaseMapper;
import org.apache.storm.hbase.common.ColumnList;
import org.apache.storm.tuple.Tuple;

/**
 * Created by xiaoliang
 * 2016.11.22 17:54
 *
 * @Version 1.0
 */
public class TestMapper implements HBaseMapper {
    @Override
    public byte[] rowKey(Tuple tuple) {
        return new byte[0];
    }

    @Override
    public ColumnList columns(Tuple tuple) {
        return null;
    }
}
