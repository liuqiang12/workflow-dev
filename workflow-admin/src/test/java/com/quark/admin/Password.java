package com.quark.admin;

import org.springframework.util.DigestUtils;

/**
 * Created by DELL on 2017/12/27.
 */
public class Password {
    public static void main(String[] args) {

        System.out.println(DigestUtils.md5DigestAsHex("a".getBytes()));
    }
}
