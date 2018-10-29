package lxh.fw.common.util.exception;

import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;

/**
 * 
 * @author jiesun
 *
 * @date 2012-11-13 下午11:54:25
 */
public class AopExceptionAdvice implements ThrowsAdvice {
	
	public void afterThrowing(Method method, Object[] args, Object target, RuntimeException throwable) {
		System.out.println("产生异常的方法名称：  " + method.getName());

		for (Object o : args) {
			System.out.println("方法的参数：   " + o.toString());
		}

		System.out.println("代理对象: " + target.getClass().getName());
		System.out.println("抛出的异常: " + throwable.getMessage() + ">>>>>>>" + throwable.getCause());
		System.out.println("异常详细信息: " + throwable.fillInStackTrace());
	}

}
