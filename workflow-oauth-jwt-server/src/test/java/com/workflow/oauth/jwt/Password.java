package com.workflow.oauth.jwt;

import org.springframework.util.DigestUtils;

public class Password {
    public static void main(String[] args) {

        System.out.println(DigestUtils.md5DigestAsHex("a".getBytes()));
    }
}
