package lxh.fw.common.util.log;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.AfterReturningAdvice;

/**
 * 
 * @author jiesun
 *
 * @date 2012-11-13 下午11:54:12
 */
public class AopLogAdvice implements MethodBeforeAdvice, AfterReturningAdvice {

	@Override
	public void afterReturning(Object returnValues, Method method, Object[] args, Object target) throws Throwable {
		System.out.println("afterReturning:  "+method.getName()+"   "+returnValues);
	}

	@Override
	public void before(Method method, Object[] arg1, Object target) throws Throwable {
		System.out.println("before:  " + method.getName());
		// 循环输出方法的参数
		for (Object obj : arg1) {
			System.out.println("方法参数：" + obj);
		}
	}

}
