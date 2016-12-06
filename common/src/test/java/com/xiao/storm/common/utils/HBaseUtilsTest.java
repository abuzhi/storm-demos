package com.xiao.storm.common.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xiaoliang
 * 2016.12.02 14:50
 *
 * @Version 1.0
 */
public class HBaseUtilsTest {
    @Test
    public void insertAndUpdate() throws Exception {
        boolean is = HBaseUtils.isExist("t_rt_test_account_lib","t_rt_test_account_lib_f");
        assertEquals(true,is);
    }

    @Test
    public void insertAndUpdate1() throws Exception {

    }

    @Test
    public void isExist() throws Exception {

    }

}