package io.ymq.mybatis.dao.base;

/**
 * 描述: 基础工具方法
 * author: yanpenglei
 * Date: 2017/9/8 21:13
 */
public class BaseUtils {

	/**
	 * 生成sql方法调用路径
	 * 
	 * @param <T>
	 * 
	 * @param object 全限定类名
	 * @param methodName 调用方法名称
	 * @return
	 */
	public static <T> String makeClazzPath(T object, String methodName) {

		if (object == null) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(object.getClass().getName());
		buffer.append(".");
		buffer.append(methodName);
		return buffer.toString();

	}

	public static <T> String makeClazzPath(Class<T> clazz, String methodName) {

		StringBuffer buffer = new StringBuffer();
		buffer.append(clazz.getName());
		buffer.append(".");
		buffer.append(methodName);
		return buffer.toString();

	}
}
