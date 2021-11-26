package com.lky.bean2sql.utils;

import com.lky.bean2sql.annotation.QColumn;
import com.lky.bean2sql.annotation.QIgnore;
import com.lky.bean2sql.annotation.QKey;
import com.lky.bean2sql.annotation.QTable;
import com.lky.bean2sql.definition.ColumnDef;
import com.lky.bean2sql.definition.TableDef;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
    private Log log =  LogFactory.getLog(getClass());
    /**
     * projectPath:工程地址
     */
    private String projectPath;
    /**
     * stack:遍历目录
     */
    private Stack<String> stack;
    /**
     * clazzPath:指定包下的所有类
     */
    private List<String> clazzPath;
    /**
     * fullClassNames:指定包下所有类的全类名
     */
    private List<String> fullClassNames;
    /**
     * clazz:所有@Table标注的类
     */
    private List<Class<?>> clazz;
    /**
     * tableDefs:需要创建的表的定义信息
     */
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
     * 获取当前包下的所有class对象的名字
     */
    private void getAllClassNames(){
        while (!stack.empty()){
            File file = new File(stack.pop());
            if (!file.exists()){
                log.warn("路径非法,退出!");
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
            log.error("获取@Table出错!");
            e.printStackTrace();
        }
    }

    /**
     * 当前class文件的定义信息
     * @param clazz @Table标注的类
     * @return TableDef @Table类中其他创建表的信息
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
                //是否标有注解
                if (annotations.length > 0){
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
