package lxh.fw.common.web.bean;

/**
 * web页面用于保存用户权限的数据结构
 *
 */
public class RoleBean {
	
	private int type;
	private String userName;
	private Long userId;
	private String orgcode;
	
	public RoleBean(int type,String userName,long userId,String orgcode) {

		this.type = type;
		this.userName = userName;
		this.userId = userId;
        this.orgcode = orgcode;
	}


    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }


    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }


    /**
     * @return the orgcode
     */
    public String getOrgcode() {
        return orgcode;
    }


    /**
     * @param orgcode the orgcode to set
     */
    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }


    /**
     * @return the type
     */
    public int getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }
    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }
    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
	

}
