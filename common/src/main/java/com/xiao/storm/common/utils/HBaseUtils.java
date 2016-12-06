package com.xiao.storm.common.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * HBase增删改查
 * @author hyh
 */
@SuppressWarnings("deprecation")
public class HBaseUtils {
    private static Connection instance = null;

    public static Connection getInstance() {
        if (instance == null) {
            Configuration config = HBaseConfiguration.create();
            ClassLoader classLoader = HBaseUtils.class.getClassLoader();
            config.addResource(classLoader.getResource("hbase-site.xml"));
            config.addResource(classLoader.getResource("core-site.xml"));
            try {
                instance = ConnectionFactory.createConnection(config);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public synchronized static void close() throws IOException {
        instance.close();
    }

    public static void insertAndUpdate(String tableName, String row, String family,
                                       String qualifier, String value) {
        Table table = null;
        try{
            table = HBaseUtils.getInstance().getTable(TableName.valueOf(tableName));
            Put p = new Put(Bytes.toBytes(row));
            p.add(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes
                    .toBytes(value));

            table.put(p);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                table.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void insertAndUpdate(String tableName, String row, String family,
                                       String qualifier, String value,long ttl) {
        Table table = null;
        try{
            table = HBaseUtils.getInstance().getTable(TableName.valueOf(tableName));
            Put p = new Put(Bytes.toBytes(row));
            p.setTTL(ttl);
            p.add(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes
                    .toBytes(value));

            table.put(p);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                table.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static Boolean isExist(String tableName, String row) {
        Table table = null;
        try{
            table = HBaseUtils.getInstance().getTable(TableName.valueOf(tableName));
            Get g = new Get(Bytes.toBytes(row));
            return table.exists(g);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                table.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }
}
