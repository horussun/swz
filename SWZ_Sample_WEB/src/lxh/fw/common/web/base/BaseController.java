package lxh.fw.common.web.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import lxh.fw.common.Constants;
import lxh.fw.common.util.StringUtil;
import lxh.fw.common.web.base.Message;
import lxh.fw.common.web.profile.UserContext;

/**
 * 所有webController 的基类
 *
 */
public class BaseController extends MultiActionController {
	
	public static final String DEFAULT_JSON_VIEW_NAME = "jsonView"; // json视图缺省名

    public final static String PARAM_JSON_DATA = "json"; // 默认返回json数据的根名称

    public final static String PARAM_MESSAGE = "message"; // 默认返回消息对象的名称

    private String jsonViewName = DEFAULT_JSON_VIEW_NAME; // JSON视图名

    public static final String JSON_PARAM = "json"; // request json参数
    
    /**
     * 创建一个json视图
     */
    protected ModelAndView createJsonView() {
        return new ModelAndView(this.jsonViewName);
    }

    /**
     * 创建一个json视图，并设置一个model
     */
    protected ModelAndView createJsonViewAndModel(String modelName,
            Object modelValue) {
        return new ModelAndView(this.jsonViewName, modelName, modelValue);
    }

    /**
     * 创建一个json视图
     */
    protected ModelAndView createJsonViewAndModel(Object modelValue) {
        return new ModelAndView(this.jsonViewName, PARAM_JSON_DATA, modelValue);
    }

    /**
     * 发出错误
     * 前台Ajax: 使用error: Util.ajaxErrorHandler 来处理
     * 
     */
    protected ModelAndView sendJsonError(String message, Throwable exception) {
        return new ModelAndView(this.jsonViewName, PARAM_MESSAGE,
                Message.ERROR(message, exception));
    }
    
    /**
     * 从session中得到Sterling的UserContext对象
     */
    protected UserContext getUserContext(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        if (session == null) {
            return null;
        }

        UserContext context=(UserContext) session.getAttribute(Constants.USER_CONTEXT);
        if(context==null){
            context=new UserContext();
            session.setAttribute(Constants.USER_CONTEXT, context);
        }
        return context;
    }
    
    /**
     * 从session中取得BeanMap
     * @param request
     * @return
     */
    protected Map<String,Object> getBeanMap(HttpServletRequest request){
        UserContext context=getUserContext(request);
        return context.getBeanMap();
    }
    
    /**
     * 从request中取得数据根据反射复制到bean对象中
     * @param request
     * @param bean
     * @param beanName
     * @return
     * @throws NumberFormatException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    protected Object copyParameter (HttpServletRequest request, Object bean,String beanName)throws NumberFormatException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
        Method[] methods =bean.getClass().getDeclaredMethods();
        if(beanName==null){
            beanName=bean.getClass().getName().substring(bean.getClass().getName().lastIndexOf(".")+1);
        }
        for (Method method:methods){
            String name=method.getName();
            
            if(name.startsWith("set")){
                String attributeName=getAttributeName(name);
                String value=request.getParameter(beanName+"_"+attributeName);
                Object[] para=new Object[1];
                if(StringUtil.isEmpty(value)){
                	//处理日期
                	value = request.getParameter(beanName+"_"+attributeName+":date");
                	if(!StringUtil.isEmpty(value)){
                		String time = request.getParameter(beanName+"_"+attributeName+":time");
                		if(!StringUtil.isEmpty(time)){
                			value = value + " " + time.substring(1);
                		}else{
                			value += " 00:00:00";
                		}
                		para[0]=value;
                	}else{
                		para[0]=null;
                	}
                }else{
                    Class c=method.getParameterTypes()[0];
                    if(c.equals(Double.class)){
                        para[0]=new Double(value);
                    }else if(c.equals(Long.class)){
                        para[0]=new Long(value);
                    }else if(c.equals(Integer.class)){
                        para[0]=new Long(value);
                    }else if(c.equals(Date.class)){
                        para[0]=new Date(value);
                    }else{
                        para[0]=value;
                    }
                }
                method.invoke(bean, para);
            }
        }
        return bean;
    }
    
    /**
     * 从request中取得数据根据反射复制到bean对象中
     * @param request
     * @param bean
     * @return
     * @throws NumberFormatException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    protected Object copyParameter (HttpServletRequest request, Object bean) throws NumberFormatException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
        return copyParameter(request,bean,null);
    }
    
    /**
     * 取得get set方法的名字
     * @param name
     * @return
     */
    private String getAttributeName(String name){
        String realName=name.substring(3);
        String first=realName.substring(0, 1);
        realName=first.toLowerCase()+realName.substring(1);
        return realName;
    }
}
