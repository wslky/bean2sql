package com.lky.bean2sql.utils;

import com.lky.bean2sql.annotation.QColumn;
import com.lky.bean2sql.annotation.QIgnore;
import com.lky.bean2sql.annotation.QKey;
import com.lky.bean2sql.annotation.QTable;
import com.lky.bean2sql.definition.ColumnDef;
import com.lky.bean2sql.definition.TableDef;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @Description 初始化定义信息
 * @Author lky
 * @Date 2021-11-14 15:25
 */
public class LoaderDef {
    private String projectPath;
    private Stack<String> stack;
    private List<String> clazzPath;
    private List<String> fullClassNames;
    private List<Class<?>> clazz;
    private List<TableDef> tableDefs;

    /**
     * 初始化
     * @param packageName 被扫描的包名
     */
    public LoaderDef(String packageName) {
        projectPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        stack = new Stack<>();
        String replace = packageName.replace(".", "/");
        replace = projectPath + replace + "/";
        stack.push(replace);
        clazzPath = new ArrayList<>();
        fullClassNames = new ArrayList<>();
        clazz = new ArrayList<>();
    }

    /**
     * 将所有当前包下所有class路径
     */
    private void getAllClassNames(){
        while (!stack.empty()){
            File file = new File(stack.pop());
            if (!file.exists()){//路径非法退出
                return;
            }
            File[] files = file.listFiles((pathname) -> {
                return (file.isDirectory()) || (file.getName().endsWith(".class"));
            });
            for (File f : files){
                if (f.isDirectory()){
                    stack.push(f.getPath());
                }else {
                    clazzPath.add(f.getPath());
                }
            }
        }
    }

    /**
     * 将绝对路径转为全类名格式方便反射
     */
    private void pathToPackageName(){
        int len = projectPath.length() - 1;
        for (int i = 0; i < clazzPath.size(); i++) {
            String s = clazzPath.get(i);
            s = s.replace("\\", ".");
            fullClassNames.add(s.substring(len, s.length() - 6));
        }
    }

    /**
     * 拿到所有@Table注解的类
     */
    private void getALLTableDefs(){
        try {
            for (int i = 0; i < fullClassNames.size() ; i++) {
                Class<?> aClass = Class.forName(fullClassNames.get(i));
                if (aClass.getAnnotation(QTable.class) != null){
                    clazz.add(aClass);
                }
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * 当前class文件的定义信息
     * @param clazz
     * @return TableDef 表定义信息
     */
    private TableDef getTableDef(Class<?> clazz){
        TableDef tableDef = new TableDef();
        QTable annotation = clazz.getAnnotation(QTable.class);
        String s = annotation.name();
        if (s.equals("default")){
            String name = clazz.getName();
            name = name.substring(name.lastIndexOf('.') + 1);
            tableDef.setName(name);
        }else {
            tableDef.setName(s);
        }

        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            ColumnDef columnDef = new ColumnDef();
            QIgnore qIgnore = fields[i].getAnnotation(QIgnore.class);
            //是列名
            if (qIgnore == null){
                columnDef.setName(fields[i].getName());
                columnDef.setType(fields[i].getType().getName());
                Annotation[] annotations = fields[i].getAnnotations();
                if (annotations.length > 0){//有其他注解
                    for (int j = 0; j < annotations.length; j++) {
                        if (annotations[j].annotationType().getName().equals(QKey.class.getName())){
                            columnDef.setQKey(true);
                        }else if ((annotations[j].annotationType().getName().equals(QColumn.class.getName()))){
                            QColumn qColumn = fields[i].getAnnotation(QColumn.class);
                            if (!qColumn.name().equals("default")){
                                columnDef.setName(qColumn.name());
                            }
                        }
                    }
                }
                tableDef.getColumnDefs().add(columnDef);
            }
        }
        return tableDef;
    }

    /**
     * 返回所有class文件的信息
     * @return 所有需要创建的表的信息
     */
    public List<TableDef> getTableDefs(){
        getAllClassNames();
        if (clazzPath.size() > 0){
            pathToPackageName();
            if (fullClassNames.size() > 0){
                getALLTableDefs();
            }
        }
        if (clazz.size() > 0){
            tableDefs = new ArrayList<>();
            for (int i = 0; i < clazz.size(); i++) {
                tableDefs.add(getTableDef(clazz.get(i)));
            }
        }
        return tableDefs;
    }



}
