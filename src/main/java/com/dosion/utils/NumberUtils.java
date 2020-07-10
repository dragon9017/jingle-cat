package com.dosion.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class NumberUtils {
  /**
   * 生成六位数随机数
   *
   * @return
   */

  public static void main(String[] args) {
   password();
   cardNumber();
  }
  public static String password() {
    //String sources = "0123456789"; // 加上一些字母，就可以生成pc站的验证码了
    String sources =String.valueOf(System.currentTimeMillis());
    Random rand = new Random();
    StringBuffer flag = new StringBuffer();
    for (int j = 0; j < 6; j++) {
      flag.append(sources.charAt(rand.nextInt(9)) + "");
    }
    System.out.println(flag.toString());
    return flag.toString();
  }

  public static String cardNumber() {
    String sources = "0123456789"; // 加上一些字母，就可以生成pc站的验证码了
    Random rand = new Random();
    StringBuffer flag = new StringBuffer();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String time = sdf.format(new Date());
    for (int j = 0; j < 4; j++) {
      flag.append(sources.charAt(rand.nextInt(9)) + "");
    }
    System.out.println(flag.toString());
    return time+flag.toString();
  }

}
