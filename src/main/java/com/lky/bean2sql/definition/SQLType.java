package com.lky.bean2sql.definition;

import java.util.HashMap;
import java.util.Map;

/**
 * 类型映射
 */
public class SQLType {
    public static Map<String,String> TYPES;
    public static String DEFAULT = "varchar(255)";
    static {
        TYPES = new HashMap<>();
        TYPES.put("int","int");
        TYPES.put("float","float");
        TYPES.put("long","bigint");
        TYPES.put("double","double");
        TYPES.put("Integer","integer");
        TYPES.put("char","char");
        TYPES.put("java.util.Date","datetime");
        TYPES.put("java.sql.Timestamp","timestamp");
        TYPES.put("java.math.BigDecimal","decimal");
        TYPES.put("java.math.Double","double");
        TYPES.put("boolean","char");
    }
}
