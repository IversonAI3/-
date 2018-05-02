package com.mycompany.model.dao.impl;

import com.mycompany.model.dao.BaseDao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class BaseDaoImpl<T> implements BaseDao<T>{
    @Override
    public int insert(Connection conn, T t) throws SQLException {
        Class c = this.getClassType();
        // 先拿到所有的get方法
        List<Method> getters = this.getGetters(c);
        // 再拿到所有的属性名称
        List<String> columnNames = this.getColumnNames(conn, c);
        StringBuilder sb = new StringBuilder();
        // "INSERT INTO table(" + ") VALUES(" + );
        sb.append("INSERT INTO ")
                .append(c.getSimpleName())
                .append("(");
        StringBuilder condition = new StringBuilder();
        condition.append("(");
        for(int i=0;i<columnNames.size();i++){
            sb.append(columnNames.get(i));
            if(i!=columnNames.size()-1) //如果不是最后一列
                sb.append(",");
        }
        return 0;
    }
    @Override
    public int update(Connection conn, T t) throws SQLException {
        return 0;
    }

    @Override
    public int deleteById(Connection conn, int id) throws SQLException {
        Class c = this.getClassType();
        String table = c.getSimpleName();
        String primaryKey = this.getColumnNames(conn, c).get(0); // 获得主键名
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(table)
                .append(" WHERE ").append(primaryKey)
                .append(" = ")
                .append(id).append(";");
        int num = conn.createStatement().executeUpdate(sql.toString());
        return num;
    }
    @Override
    public T findById(Connection conn, int id) throws SQLException {
        Class c = this.getClassType();
        String table = c.getSimpleName(); //获得表名
        List<String> fieldNames = this.getFieldNames(c); // 获得属性名
        String primaryKey = this.getColumnNames(conn, c).get(0); // 获得主键名
        List<Class> fieldTypes = this.getFieldTypes(c); // 获得属性类型
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ").append(table)
                .append(" WHERE ").append(primaryKey).append(" = ").append(id);
        ResultSet rs = conn.createStatement().executeQuery(sql.toString());
        List<Method> setters = this.getSetters(c); // 获得所有set方法
        if(rs.next()){ // 如果结果集中存在一行就创建一个对象
            try {
                T obj = (T) c.newInstance();
                for(int i=0;i<c.getDeclaredFields().length;i++){
                    String fieldName = fieldNames.get(i); // 提取出一个属性的名字
                    String fieldType = fieldTypes.get(i).getSimpleName(); // 提取出一个属性的类型
                    String value = rs.getString(i+1); // 提出第i+i列的数据
                    setters.forEach(setter->{
                        String setName =setter.getName().toLowerCase()
                                .replace("set", "");
                        if (setName.equals(fieldName)) {
                            switch (fieldType){
                                case "String": // 如果类型是String
                                    try {
                                        setter.invoke(obj, value);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case "Integer": // 如果类型是Integer
                                    try {
                                        if(value!=null) // 如果某一列的值是NULL，就不调用set方法
                                            setter.invoke(obj, Integer.valueOf(value));
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                            }
                        }
                    });
                }
                return obj;
            } catch (InstantiationException e) {
                e.printStackTrace();
                return null;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
    @Override
    public List<T> selectAll(Connection conn) throws SQLException {
        Class c = this.getClassType();
        String table = c.getSimpleName(); //获得表名
        List<String> fieldNames = this.getFieldNames(c); // 获得属性名
        List<Class> fieldTypes = this.getFieldTypes(c); // 获得属性类型
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM "+table+";");
        List<Method> setters = this.getSetters(c); // 获得所有set方法
        List<T> objList = new ArrayList<>();
        while(rs.next()){ // 只要有一行数据就创建一个对象
            try {
                T obj = (T) c.newInstance();
                for(int i=0;i<c.getDeclaredFields().length;i++){
                    String fieldName = fieldNames.get(i); // 提取出一个属性的名字
                    String fieldType = fieldTypes.get(i).getSimpleName(); // 提取出一个属性的类型
                    String value = rs.getString(i+1); // i从0开始，提出第i+1列的数据
                    setters.forEach(setter->{
                        String setName =setter.getName().toLowerCase()
                                .replace("set", "");
                        if (setName.equals(fieldName)) {
                            switch (fieldType){
                                case "String": // 如果类型是String
                                    try {
                                        setter.invoke(obj, value);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case "Integer": // 如果类型是Integer
                                    try {
                                        if(value!=null) // 如果某一列的值是NULL，就不调用set方法
                                            setter.invoke(obj, Integer.valueOf(value));
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                    break;
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
                return null;
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

    @Override
    public void test(Connection conn) throws SQLException {
        Class c = this.getClassType();
//		this.getColumnNames(conn, c).forEach(s -> System.out.println(s));
//		System.out.println(selectCount(conn));
//		this.getSetters(c).forEach(s-> System.out.println(s.getName()));
//		this.getGetters(c).forEach(g-> System.out.println(g.getName().replace("get","")));
//		this.getFieldNames(c).forEach(f-> System.out.println(f));
//		this.getFieldTypes(c).forEach(t-> System.out.println(t.getSimpleName()));
        System.out.println(this.findById(conn,2));
        System.out.println(this.findById(conn,5));
        System.out.println(this.findById(conn,9));
    }


    /*==================辅助方法=====================*/
    /**
     * 获得调用此方法的对象的类型
     * getClass()得到调用方的类信，通过类信息来得到此类的父类的信息
     * @return 参数化类的参数类型，比如List<String>返回String类型
     * */
    public Class getClassType(){
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) pt.getActualTypeArguments()[0];
    }

    /**
     * 获得所有的列名
     * @param conn 一个数据库的连接
     * @param c 指定的类
     * @return ArrayList<String> 返回一串列名
     * */
    public List<String> getColumnNames(Connection conn, Class c) throws SQLException {
        List<String> columnNames = new ArrayList<>();  // 用来保存列名的ArrayList
        ResultSet rs = conn.createStatement()
                .executeQuery("SELECT * FROM "+c.getSimpleName()+";");
        ResultSetMetaData rsmd = null;
        try {
            rsmd = rs.getMetaData();
            for(int i=1;i<=rsmd.getColumnCount();i++) {
                columnNames.add(rsmd.getColumnName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//		Collections.sort(columnNames);// 注意：一定要排好序
        return columnNames;
    }

    /**
     * 返回指定类中所有属性的类型
     * @param c 指定的类
     * */
    public List<Class> getFieldTypes(Class c) throws SQLException {
        List<Class> fieldTypes = new ArrayList<>();  // 用来保存列名的ArrayList
        Field[] fields = c.getDeclaredFields();
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
     * @return 一个包含所有set方法的集合
     * */
    public List<Method> getSetters(Class c){
        // 先用一个List来保存所有的方法
        List<Method> methods = Arrays.asList(c.getDeclaredMethods());
        // 然后用另外一个List来保存所有set方法
        List<Method> setters = new ArrayList<>(c.getDeclaredFields().length);
        for(int i=0;i<methods.size();i++) {
            if(methods.get(i).getName().startsWith("set")) {
                setters.add(methods.get(i)); // 把set方法添加到setters中
            }
        }
        return setters;
    }

    /**
     * 获得指定类的所有getter
     * @param c 指定的类
     * @return 一个包含所有set方法的集合
     * */
    public List<Method> getGetters(Class c){
        // 先用一个List来保存所有的方法
        List<Method> methods = Arrays.asList(c.getDeclaredMethods());
        // 然后用另外一个List来保存所有的get方法
        List<Method> getters = new ArrayList<>(c.getDeclaredFields().length);
        for(int i=0;i<methods.size();i++) {
            if(methods.get(i).getName().startsWith("get")) {
                getters.add(methods.get(i)); // 把get方法添加到getters中
            }
        }
//		Collections.sort(getters, new MethodComparator());
        return getters;
    }

    /**
     * 或的调用者的类中定义的所有成员变量名
     * @param c 指定的类
     * @return 返回list包含所有的属性名称
     * */
    public List<String> getFieldNames(Class c){
        Field[] fields = c.getDeclaredFields();
        List<String> fieldList = new LinkedList<String>();
        for(Field f: fields){
            fieldList.add(f.getName());
        }
//		Collections.sort(fieldList);
        return fieldList;
    }

    /**
     * 创建指定类的实例，根据
     * @param c 指定的类
     * @return 返回指定类的实例
     * */
    public List<T> instantiateObjects(Class c, ResultSet rs) throws SQLException {
        return null;
    }
}
