package com.xiao.storm.utils;

import com.xiao.storm.common.utils.EncryptMD5;
import com.xiao.storm.common.utils.RandomUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * HBase增删改查
 */
@SuppressWarnings("deprecation")
public class HBaseUtils {
    private final static String HBASE_PATH = "hadoop.hbase.conf.path";

    private static Connection instance = null;

    public static synchronized void setUp(Map conf) throws IOException {
        if (instance == null) {
            String hbasepath = (String) conf.get("hadoop.hbase.conf.path");
            Configuration config = HBaseConfiguration.create();
            if(StringUtils.isNotBlank(hbasepath)){
                config.addResource(new Path(hbasepath, "hbase-site.xml"));
                config.addResource(new Path(hbasepath, "core-site.xml"));
            }
            instance = ConnectionFactory.createConnection(config);
        }
    }

    public static Connection getInstance() {
        return instance;
    }

    public synchronized static void close() throws IOException {
        instance.close();
    }

    public static void insertAndUpdate(String tableName, String row, String family,
                                       String qualifier, String value) throws IOException {
        Table table = HBaseUtils.getInstance().getTable(TableName.valueOf(tableName));
        Put p = new Put(Bytes.toBytes(row));
        p.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes
                .toBytes(value));

        table.put(p);
    }

    public static void insertAndUpdate(String tableName, String row,
                                       String family, Map<String, String> mapKeyValue) throws IOException {
        Table table = HBaseUtils.getInstance().getTable(TableName.valueOf(tableName));
        Put p = new Put(Bytes.toBytes(row));
        for (Map.Entry<String, String> entry : mapKeyValue.entrySet()) {
            p.addColumn(Bytes.toBytes(family), Bytes.toBytes(entry.getKey()),
                    Bytes.toBytes(entry.getValue()));
        }
        table.put(p);
    }

    public static void insertAndUpdate(String tableName, String row, String family,
                                       String qualifier, String value, long ttl) throws IOException {
        Table table = HBaseUtils.getInstance().getTable(TableName.valueOf(tableName));
        Put p = new Put(Bytes.toBytes(row));
        p.setTTL(ttl);
        p.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes
                .toBytes(value));

        table.put(p);
    }

    public static Boolean isExist(String tableName, String row) throws IOException {
        Table table = HBaseUtils.getInstance().getTable(TableName.valueOf(tableName));
        Get g = new Get(Bytes.toBytes(row));
        return table.exists(g);
    }

    public static void createSchemaTables(String tablename, String row) throws IOException {
        try (Connection connection = HBaseUtils.getInstance();
             Admin admin = connection.getAdmin()) {
            HTableDescriptor table = new HTableDescriptor(TableName.valueOf(tablename));
            if (admin.tableExists(table.getTableName())) {
                System.out.println("table exist ...");
                return;
            }

            table.addFamily(new HColumnDescriptor(row).setCompressionType(Algorithm.SNAPPY));
            admin.createTable(table);
        }
    }

    public static void main(String args[]) {
        Map<String,String> conf = new HashMap();
        conf.put("hadoop.hbase.conf.path","E:\\git-repos\\storm-demos\\hbase-demo\\config\\");

        String table = "test";

        try {
            HBaseUtils.setUp(conf);

            long start = System.currentTimeMillis();
            for(int i =0;i<100000;i++){
                String userid = RandomUtil.randomString(10);
                String key = EncryptMD5.md5(userid);

                boolean is = HBaseUtils.isExist(table,key);
                Map<String,String> map = new HashMap<String,String>();
                map.put("logtime", "201622222");
                if(!is){
                    HBaseUtils.insertAndUpdate(table,key,"f",map);
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("cost = "+(end-start)+"ms");



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
