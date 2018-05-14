package appcontext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 上下文实现
 */
public class ApplicationContext {

    //存放所有实体类对象的集合
    private static Map<String,Object> beanMap = new HashMap<String,Object>();

    //一个静态块，预先先做好所有准备
    static{
        //第一件事情：解析xml，创建所有的具体的业务实体，加入到集合中
        createBeans();

        //第二件事情：解析xml，为那些有参数的视图，提供参数：
        // 将业务逻辑service中的dao的属性 赋值
        setProperty();
    }
    //--------------------------------
    //【创建实体，加入集合】
    //只能实现 xml文档中所有 <bean> 的 name 与 class
    private static void createBeans(){
        try {
            //读取
            SAXReader reader = new SAXReader();
            Document doc = reader.read(ApplicationContext.class.getClassLoader().getResourceAsStream("ApplicationContext.xml"));

            //根元素<beans>
            Element root = doc.getRootElement();

            //提取所有的<bean>
            List<Element> list = root.elements();

            //循环
            for(Element e : list){

                //名字: map中的key
                String key = e.attributeValue("name");

                //class，反射对象，实例化，就是map中的value
                Class<?> cls = Class.forName(e.attributeValue("class"));
                Object value = cls.newInstance();
                System.out.println("对象"+value);
                //加入集合
                beanMap.put(key, value);

                //System.out.println(key+"---"+value);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //设置参数
    private static void setProperty(){
        try {
            //读取
            SAXReader reader = new SAXReader();
            Document doc = reader.read(ApplicationContext.class.getClassLoader().getResourceAsStream("applicationContext.xml"));

            //根元素<beans>
            Element root = doc.getRootElement();
            //提取所有的<bean>
            List<Element> list = root.elements();
            //循环
            for(Element e : list){

                //提取所有旗下的Property
                List<Element> plist = e.elements();
                //遍历
                for(Element pt : plist){

                    //当前类
                    Object obj = getBean(e.attributeValue("name"));

                    //利用反射，提取里面所有的set方法，且名字里面有xml声明的name就可以了
                    Method[] methods = obj.getClass().getDeclaredMethods();
                    //遍历循环
                    for(Method m : methods){
                        //判断(如果：方法名字里面，包好了 Ud的话)
                        String methodName = m.getName().toLowerCase();
                        if(methodName.contains(pt.attributeValue("name").toLowerCase())){
                            //利用反射调用这个set方法，（在指定的类对象中，提取需要的类参数）
                            Object o = m.invoke(obj, getBean(pt.attributeValue("value")));
                        }
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //从beanMap映射中返回一个Bean对象
    public static Object getBean(String name){
        return beanMap.get(name);
    }
}
