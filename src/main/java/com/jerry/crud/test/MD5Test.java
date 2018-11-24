package com.jerry.crud.test;

import java.util.Random;

/**
 * @author 向博文
 * @date 2018年8月6日
 */
public class MD5Test {
	public static void main(String[] args) {
		Random random = new Random();
		for(int i = 0;i <= 100;i++) {
			System.out.println(random.nextInt(7));
		}
	}
}
