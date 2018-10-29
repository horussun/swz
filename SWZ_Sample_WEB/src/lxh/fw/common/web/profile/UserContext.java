package lxh.fw.common.web.profile;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lxh.fw.common.web.bean.RoleBean;

/**
 * 保存于session中的，与用户相关的信息，主要有登录权限和分页排序信息
 *
 */
public final class UserContext implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3473238606187439304L;

    private Map<String,PagingContext> pagingContextMap=new HashMap<String,PagingContext>();
    
    private Map<String,Object> beanMap=new HashMap<String,Object>();
    
    private RoleBean role;
    
    private static transient ThreadLocal<UserContext> local = new ThreadLocal<UserContext>();

    
    /**
     * @return the role
     */
    public RoleBean getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(RoleBean role) {
        this.role = role;
    }

    /**
     * @return the pagingContext
     */
    public PagingContext getPagingContext(String key) {
        PagingContext p=pagingContextMap.get(key);
        if(p==null){
            p=new PagingContext();
            pagingContextMap.put(key, p);
        }
        return p;
    }
    /*
     * clear the pagingContext
     */
    public void clearPagingContext(String key){
    	PagingContext p = pagingContextMap.get(key);
    	if(p!=null){
    		pagingContextMap.put(key, null);
    	}
    }
    /**
     * @return the beanMap
     */
    public Map<String, Object> getBeanMap() {
        return beanMap;
    }

    public static UserContext getUserContext() {
        if ( local.get() != null ) {
            return local.get().getUserContext();
        } 
        
        return null;
    }

}
