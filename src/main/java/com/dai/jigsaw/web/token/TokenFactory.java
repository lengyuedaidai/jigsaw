package com.dai.jigsaw.web.token;

import org.apache.ibatis.session.Configuration;

/**
 * Token工厂
 * 
 **/
public class TokenFactory {

    public static String tokenClass = null;

    public static Token buildDialect(Configuration configuration) {
        if (tokenClass == null) {
            synchronized (TokenFactory.class) {
                if (tokenClass == null) {
                	tokenClass = configuration.getVariables().getProperty("tokenClass");
                }
            }
        }
        Token token = null;
        try {
        	token = (Token) Class.forName(tokenClass).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("请检查xml 中  tokenClass 是否配置正确?");
        }
        return token;
    }
}
