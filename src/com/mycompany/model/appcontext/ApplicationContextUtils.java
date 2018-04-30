package com.mycompany.model.appcontext;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这是一个工具类，用来配置应用程序上下文
 * */
public class ApplicationContextUtils {
    // 存放所有实体对象的合集
    // key是接口名, Object是接口的对象
    private static Map<String, Object> beanMap = new HashMap<>();

    // 一个静态块，初始化beanMap
    static {
        // 第一件事情: 解析XML文档
        createBeans();

    }

    /* 解析上下文xml文件，创建实例，加入集合。根据XML中所有的name和class来创建bean*/
    private static void createBeans()  {
        SAXReader reader = new SAXReader();
        Document doc = null;
        try {
            doc = reader.read(ApplicationContextUtils.class.getClassLoader()
                    .getResourceAsStream("applicationContext.xml"));
            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for(Element e : list){
                String key = e.attributeValue("name"); // Map中的key
                Class c = Class.forName(e.attributeValue("class"));
                Object value = c.newInstance();
                beanMap.put(key,value);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /* 解析上下文xml文件，调用对象的set方法，为属性赋值*/
    private static void setProperty(){
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(ApplicationContextUtils.class
                    .getClassLoader()
                    .getResourceAsStream("applicationContext.xml"));
            // 获得根元素<beans>
            Element root = doc.getRootElement();
            // 获得根元素中的所有元素<bean>
            List<Element> elements = root.elements();
            for(Element e : elements){
                // 获得一个<bean>元素中所有的属性<property>元素
                List<Element> properties = e.elements();
                for(Element property: properties){
                    Object beanObject = beanMap.get(property.attributeValue("name"));
                    Method[] methods = beanObject.getClass().getDeclaredMethods();
                    for(Method m:methods){
                        String methodName = m.getName().replace("set","").toLowerCase();
                        switch (methodName){

                        }
                    }
                }
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
