package com.xiao.storm.datas;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by xiaoliang
 * 2016.12.19 16:43
 *
 * @Version 1.0
 */
public class DataFactory {

    public static final String CONFIG_FILE="datas.properties";
    private static Properties properties = null;

    public static synchronized void init(String filepath){
        properties = new Properties();
        FileInputStream fis = null;
        try{
            File tmp = new File("");
            String path =tmp.getCanonicalPath()+"/"+ CONFIG_FILE;
            if(filepath!=null && !"".equalsIgnoreCase(filepath)){
                path = tmp.getCanonicalPath()+"/"+ filepath;
            }
            File pfile = new File(path);
            fis = new FileInputStream(pfile);
            properties.load(fis);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 默认文件
     */
    public static synchronized void init(){
        init("");
    }

    public static String rolelogin(DataEntity data){
        String msg = properties.getProperty("rolelogin");
        return msg.replaceAll(DataConstants.logtime,data.getLogtime())
                .replaceAll(DataConstants.platform,data.getPlatform())
                .replaceAll(DataConstants.os,data.getOs())
                .replaceAll(DataConstants.serverid,data.getServerid())
                .replaceAll(DataConstants.userid,data.getUserid())
                .replaceAll(DataConstants.account,data.getAccount())
                .replaceAll(DataConstants.roleid,data.getRoleid())
                .replaceAll(DataConstants.cash,data.getCash())
                .replaceAll(DataConstants.yuanbao,data.getYuanbao())
                .replaceAll(DataConstants.currentuser,data.getCurrentuser())
                .replaceAll(DataConstants.loginplaying,data.getLoginplaying())
                .replaceAll(DataConstants.loginqueuing,data.getLoginqueuing())
                .replaceAll(DataConstants.mac,data.getMac());
    }

    public static String createRole(DataEntity data){
        String msg = properties.getProperty("createrole");
        return msg.replaceAll(DataConstants.logtime,data.getLogtime())
                .replaceAll(DataConstants.platform,data.getPlatform())
                .replaceAll(DataConstants.os,data.getOs())
                .replaceAll(DataConstants.serverid,data.getServerid())
                .replaceAll(DataConstants.userid,data.getUserid())
                .replaceAll(DataConstants.account,data.getAccount())
                .replaceAll(DataConstants.roleid,data.getRoleid())
                .replaceAll(DataConstants.cash,data.getCash())
                .replaceAll(DataConstants.yuanbao,data.getYuanbao())
                .replaceAll(DataConstants.currentuser,data.getCurrentuser())
                .replaceAll(DataConstants.loginplaying,data.getLoginplaying())
                .replaceAll(DataConstants.loginqueuing,data.getLoginqueuing())
                .replaceAll(DataConstants.mac,data.getMac());
    }


    public static String addcash(DataEntity data){
        String msg = properties.getProperty("addcash");
        return msg.replaceAll(DataConstants.logtime,data.getLogtime())
                .replaceAll(DataConstants.platform,data.getPlatform())
                .replaceAll(DataConstants.os,data.getOs())
                .replaceAll(DataConstants.serverid,data.getServerid())
                .replaceAll(DataConstants.userid,data.getUserid())
                .replaceAll(DataConstants.account,data.getAccount())
                .replaceAll(DataConstants.roleid,data.getRoleid())
                .replaceAll(DataConstants.cash,data.getCash())
                .replaceAll(DataConstants.yuanbao,data.getYuanbao())
                .replaceAll(DataConstants.currentuser,data.getCurrentuser())
                .replaceAll(DataConstants.loginplaying,data.getLoginplaying())
                .replaceAll(DataConstants.loginqueuing,data.getLoginqueuing())
                .replaceAll(DataConstants.mac,data.getMac());
    }

    public static String costyuanbao(DataEntity data){
        String msg = properties.getProperty("costyuanbao");
        return msg.replaceAll(DataConstants.logtime,data.getLogtime())
                .replaceAll(DataConstants.platform,data.getPlatform())
                .replaceAll(DataConstants.os,data.getOs())
                .replaceAll(DataConstants.serverid,data.getServerid())
                .replaceAll(DataConstants.userid,data.getUserid())
                .replaceAll(DataConstants.account,data.getAccount())
                .replaceAll(DataConstants.roleid,data.getRoleid())
                .replaceAll(DataConstants.cash,data.getCash())
                .replaceAll(DataConstants.yuanbao,data.getYuanbao())
                .replaceAll(DataConstants.currentuser,data.getCurrentuser())
                .replaceAll(DataConstants.loginplaying,data.getLoginplaying())
                .replaceAll(DataConstants.loginqueuing,data.getLoginqueuing())
                .replaceAll(DataConstants.mac,data.getMac());
    }

    public static String onlineuser(DataEntity data){
        String msg = properties.getProperty("onlineuser");
        return msg.replaceAll(DataConstants.logtime,data.getLogtime())
                .replaceAll(DataConstants.platform,data.getPlatform())
                .replaceAll(DataConstants.os,data.getOs())
                .replaceAll(DataConstants.serverid,data.getServerid())
                .replaceAll(DataConstants.userid,data.getUserid())
                .replaceAll(DataConstants.account,data.getAccount())
                .replaceAll(DataConstants.roleid,data.getRoleid())
                .replaceAll(DataConstants.cash,data.getCash())
                .replaceAll(DataConstants.yuanbao,data.getYuanbao())
                .replaceAll(DataConstants.currentuser,data.getCurrentuser())
                .replaceAll(DataConstants.loginplaying,data.getLoginplaying())
                .replaceAll(DataConstants.loginqueuing,data.getLoginqueuing())
                .replaceAll(DataConstants.mac,data.getMac());
    }

}
