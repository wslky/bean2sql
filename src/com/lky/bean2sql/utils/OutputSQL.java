package com.lky.bean2sql.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @Description 输出SQL到文件
 * @Author lky
 * @Date 2021-11-14 16:29
 */
public class OutputSQL {

    /**
     * 入口
     * @param packageName "xx.xx.xx"全类名
     */
    public static void getFile(String packageName){
        GenerateSQL generateSQL = new GenerateSQL(packageName);
        List<List<String>> lists = generateSQL.getSQLList();
        List<String> name = generateSQL.getName();
        for (int i = 0; i < lists.size(); i++) {
            out(lists.get(i),name.get(i));
        }
    }

    /**
     * 输出
     * @param list 内容
     * @param name 文件名
     */
    private static void out(List<String> list,String name){
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(name+".sql"));
            for (int i = 0; i < list.size(); i++) {
                bw.write(list.get(i));
                bw.flush();
                bw.newLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
