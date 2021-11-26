# bean2sql

***通过注解方式将Java实体类,生成创建 Sql文件***

#### 映射规则:

| JAVA类型   | 数据库类型 |
| ---------- | ---------- |
| int        | int        |
| long       | bigint     |
| float      | float      |
| double     | double     |
| Integer    | integer    |
| char       | char       |
| Date       | datetime   |
| Timestamp  | timestamp  |
| BigDecimal | decimal    |
| Double     | double     |
| boolean    | char       |
| 其他       | varchar    |

| 注解                                                 |
| ---------------------------------------------------- |
| @QTable(name):标注在需要创建表的类上(表名默认为类名) |
| @QColumn(name):,指定列名(列名默认为属性名)           |
| @QIgnore:忽略这个属性                                |
| @QKey:主键                                           |

```java
/**
*@param packageName "xx.xx.xx"
*/
OutputSQL.getFile(String name);//程序入口
```



