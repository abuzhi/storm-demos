package com.xiao.storm.utils;

import com.xiao.storm.common.utils.EncryptMD5;
import com.xiao.storm.common.utils.RandomUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.security.UserGroupInformation;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by xiao on 2018/8/6.
 */
public class HBaseUtilsTest {
    @Test
    public void setUp() throws Exception {

    }

    @Test
    public void setZk() throws Exception {
        String zkServer = "host190,host191,host192";

        Configuration config = HBaseConfiguration.create();
        //配置Zookeeper节点
        config.set("hbase.zookeeper.quorum", zkServer);
        config.set("zookeeper.znode.parent", "/hbase");
        config.set("hbase.superuser", "root");
        config.set("hadoop.security.authentication", "root");
        config.set("hbase.security.authentication", "root");

//        UserGroupInformation.setConfiguration(config);
//        UserGroupInformation.loginUserFromKeytab(ZOOKEEPER_PRINCIPAL,ZOOKEEPER_KEYTAB);
//
//        HBaseAdmin admins = new HBaseAdmin(config);
//        TableName[] tables  = admins.listTableNames();
//
//        for(TableName table: tables){
//            System.out.println(table.toString());
//        }


        Connection instance = ConnectionFactory.createConnection(config);

        Table table = instance.getTable(TableName.valueOf("test"));
            System.out.println(table.toString());

    }

}