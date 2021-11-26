package com.lky.bean2sql.utils;

import com.lky.bean2sql.definition.ColumnDef;
import com.lky.bean2sql.definition.SQLType;
import com.lky.bean2sql.definition.TableDef;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description 生成SQL
 * @Author lky
 * @Date 2021-11-14 16:28
 */
public class GenerateSQL {
    private Log log =  LogFactory.getLog(getClass());
    /**
     *  每一张表的sql语句
     */
    private List<List<String>> list;
    /**
     * SQL文件的名字
     */
    private List<String> name;
    /**
     * 获取表信息的对象
     */
    private LoaderDef loaderDef;

    public List<String> getName() {
        return name;
    }

    public GenerateSQL(String packageName) {
        this.loaderDef = new LoaderDef(packageName);
    }
    /**
     * 拿到生成的sql
     * @return 所有sql语句
     */
    public List<List<String>> getSQLList(){
        /**
         * 获取指定包下所有@Table标注的类,并生成表信息
         */
        List<TableDef> tableDefs = loaderDef.getTableDefs();
        if (tableDefs != null && tableDefs.size() > 0){
            list = new ArrayList<>();
            name = new ArrayList<>();
            for (int i = 0; i < tableDefs.size(); i++) {
                List sql = doSql(initTemplate(), tableDefs.get(i));
                list.add(sql);
            }
        }
        return list;
    }

    /**
     * TableDef解析
     * @param list
     * @param tableDef
     * @return
     */
    private List doSql(List list,TableDef tableDef){
        String tableName = tableDef.getName();
        log.info("正在创建: "+tableName+"表...");
        name.add(tableName);
        StringBuilder sb = new StringBuilder();
        sb.append("create table if not exists `" + tableName + "` (");
        sb.append("\n");
        List<ColumnDef> columnDefs = tableDef.getColumnDefs();
        for (int i = 0; i < columnDefs.size(); i++) {
            ColumnDef columnDef = columnDefs.get(i);
            sb.append('\t');
            sb.append("`"+columnDef.getName()+"`");
            sb.append('\t');
            //类型
            toSqlType(sb,columnDef.getType());
            //是否主键
            /**
             * 优化:
             *  1.判断是否是主键  √
             *  2.类型映射:基本类型映射 + 日期映射  + BigDecimal映射  √
             *  3.定义约束信息
             */
            if (columnDef.isQKey()){ isKey(sb); }
            sb.append(',');
            sb.append('\n');
        }
        int i = sb.lastIndexOf(",");
        sb.deleteCharAt(i);
        sb.append(")");
        list.add(sb.toString());
        log.info(tableName+"表创建成功!");
        return list;
    }

    /**
     * 主键SQL
     * @param sb
     */
    public void isKey(StringBuilder sb){
        sb.append("\t");
        sb.append("primary key");
    }

    /**
     * 类型映射
     * @param sb
     * @param type
     */
    public void toSqlType(StringBuilder sb,String type){
        if (SQLType.TYPES.containsKey(type)){
            sb.append(SQLType.TYPES.get(type));
        }else {
            sb.append(SQLType.DEFAULT);
        }
    }

    /**
     * 初始化sql模板
     * @return
     */
    private List initTemplate() {
        List list = new ArrayList<>();
        list.add("# @Author lky");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        list.add("# 生成时间:" + simpleDateFormat.format(new Date()));
        list.add("# ------------------SQL-------------------");
        return list;
    }

}
