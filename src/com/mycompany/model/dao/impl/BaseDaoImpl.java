package com.mycompany.model.dao.impl;

import com.mycompany.model.dao.BaseDao;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public abstract class BaseDaoImpl<T> implements BaseDao<T>{
    @Override
    public List<T> selectAll(Connection conn) throws SQLException {
        Class c = this.getClassType();
        String table = c.getSimpleName(); // 获得表名
//        List<String> fieldNames = this.getFieldNames(c); // 获得属性名
        String[] columnNames = this.getColumnNames(conn,c);
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM "+table+";");
        List<Method> setters = this.getSetters(c); // 获得所有set方法
        List<T> objList = new LinkedList<>(); // 这里用LinkedList插入效率更高
        while(rs.next()){ // 只要有一行数据就创建一个对象
            try {
                T obj = (T) c.newInstance();
                for(int i=0;i<columnNames.length;i++){
                    String fieldName = columnNames[i].toLowerCase(); // 提取出一个属性的名字
                    String value = rs.getString(fieldName); // i从0开始，提出第i+1列的数据
                    setters.forEach(s -> {
                    // 对每一个set方法，先匹配它对应的属性
                        String type = s.getParameterTypes()[0].getSimpleName();
                    if(s.getName().replace("set","").toLowerCase()
                            .equals(fieldName)){
                        try {
                            if(value==null)
                                s.invoke(obj,value);
                            else{
                                switch (type){ // 则判断属性的类型, switch比if效率快很多
                                    case "String": s.invoke(obj,value); break;
                                    case "Integer":
                                        s.invoke(obj, Integer.valueOf(value)); break;
                                    case "Double": s.invoke(obj, Double.valueOf(value));break;
                                    case "Short": s.invoke(obj, Short.valueOf(value));break;
                                }
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                    });
                }
                objList.add(obj);
            } catch (InstantiationException e) {
                e.printStackTrace();
                return null;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return objList;
    }

    @Override
    public int selectCount(Connection conn) throws SQLException {
        Class c = this.getClassType();
        ResultSet rs = conn.createStatement().executeQuery("SELECT count(*) FROM "
                + c.getSimpleName() + ";");
        if(rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    /*==================辅助方法=====================*/
    /**
     * 获得调用此方法的对象的类型，BaseDaoImpl独有的方法，不希望被子类继承
     * getClass()得到调用方的类信，通过类信息来得到此类的父类的信息
     * @return 参数化类的参数类型，比如List<String>返回String类型
     * */
    protected Class getClassType(){
        // getGenericSuperclass()得到这个类的直接父类
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) pt.getActualTypeArguments()[0];
    }

    /**
     * 获得表中所有的列名column name
     * @param conn 一个数据库的连接
     * @param c 指定的类(bean)
     * @return ArrayList<String> 返回a list of column name
     * */
    protected String[] getColumnNames(Connection conn, Class c) throws SQLException {
        ResultSet rs = conn.createStatement()
                .executeQuery("SELECT * FROM "+c.getSimpleName()+";");
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        String[] columnNameList = new String[columnCount];
        for(int i=1;i<=columnCount;i++)  // 注意：数据库表中的列的索引从1开始
            columnNameList[i-1] = rsmd.getColumnName(i);
        return columnNameList;
    }

    /**
     * 返回指定类(bean)中所有属性的类型
     * @param c 指定的类
     * */
    protected List<Class> getFieldTypes(Class c){
        Field[] fields = c.getDeclaredFields();
        List<Class> fieldTypes = new ArrayList<>(fields.length);
        for(int i=0;i<fields.length;i++){
            fieldTypes.add(fields[i].getType());
        }
        return fieldTypes;
    }

    /**
     * 获得指定类的所有setter。
     * 注意： Arrays.asList返回的List是无法进行增加和删除操作的，试图增删会报错
     * 只能调用set(index, element)来修改值
     * @param c 指定的类
     * @return 一个包含所有set方法的list
     * */
    protected List<Method> getSetters(Class c){
        // getDeclaredMethods返回的是一个Methods[]数组包含所有此类中声明的方法，包括public,protected,private（不包含继承的方法）
        Method[] methods = c.getMethods();
        // 用一个List来保存所有set方法
        List<Method> setters = new ArrayList<>(methods.length);
        for(Method m : methods) {
            if(m.getName().startsWith("set")) { // 如果这个方法以set开头
                setters.add(m); // 把它添加到setters中
            }
        }
        return setters;
    }

    /**
     * 获得指定类的所有getter
     * @param c 指定的类
     * @return 一个包含所有get方法的集合
     * */
    protected List<Method> getGetters(Class c){
        Method[] methods = c.getMethods();
        List<Method> getters = new ArrayList<>(methods.length);
        for(Method m : methods) {
            if(m.getName().startsWith("get")) { // 如果这个方法以set开头
                getters.add(m); // 把它添加到setters中
            }
        }
        return getters;
    }

    /**
     * 或的调用者的类中定义的所有成员变量名
     * @param c 指定的类
     * @return 返回list包含所有的属性名称
     * */
    protected List<String> getFieldNames(Class c){
        Field[] fields = c.getDeclaredFields();
        List<String> fieldList = new ArrayList<>(fields.length);
        for(int i=0;i<fields.length;i++){
            fieldList.add(fields[i].getName());
        }
        return fieldList;
    }
}
