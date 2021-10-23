package com.springboot.learning.util;

import java.util.Random;

/**
 * @Description:获取16位的盐
 * @Author: 坚持的力量
 * @Date: 2021/10/19 17:35
 * @Version: 11
 */

public class Md5Utils {


    /**
     * @Description:length用户要求产生字符串的长度
     **/
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
