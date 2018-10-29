package lxh.fw.common.web.view;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.BeanNameViewResolver;

/**
 * 严格的bean名称view解析类。继承自Spring BeanNameViewResolver。<br/>
 * 事先会缓存容器里实现了View接口的bean名称。好处是既限制了bean的数量，又提高了速度。
 * 
 * @see BeanNameViewResolver
 */
public class StrictBeanNameViewResolver extends BeanNameViewResolver {

	private Set<String> viewNames = null;

	public View resolveViewName(String viewName, Locale locale)
			throws BeansException {
		if (viewNames == null) {
			initView();
		}
		if (!viewNames.isEmpty() && viewNames.contains(viewName)) {
			ApplicationContext context = getApplicationContext();
			return (View) context.getBean(viewName);
		}
		return null;
	}

	private void initView() {
		viewNames = new HashSet<String>();
		for (String beanName : getApplicationContext().getBeanNamesForType(
				View.class)) {
			viewNames.add(beanName);
		}
	}
}
