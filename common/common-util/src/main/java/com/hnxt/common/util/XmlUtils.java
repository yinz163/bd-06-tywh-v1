package com.hnxt.common.util;

import com.thoughtworks.xstream.XStream;

/**
 * 官网：http://x-stream.github.io/tutorial.html
 * 通过xstream第三方类库实现xml/json字符串与实体对象相互转换(推荐注解方式)
 * @Author yinz
 * @Date 2021/11/30 - 9:05
 */
public class XmlUtils {

	/**
	 * xml字符串转对象
	 * @param xmlStr
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T toObject(String xmlStr, Class<T> clazz) {
		XStream xStream = new XStream();
		xStream.processAnnotations(clazz);
		//不加下面一行，main方法可正常运行，与springboot集成后，使用会报错
		xStream.setClassLoader(clazz.getClassLoader());
		return (T) xStream.fromXML(xmlStr);
	}

	public static String toXml(Object object) {
		XStream xStream = new XStream();
		xStream.processAnnotations(object.getClass());
		//复杂类型转换可自定义converter实现，具体可参考官网教程
//		xStream.registerConverter();
		//字段扫描注解效率不高，不推荐
//		xStream.autodetectAnnotations(true);
		return xStream.toXML(object);
	}
 }
