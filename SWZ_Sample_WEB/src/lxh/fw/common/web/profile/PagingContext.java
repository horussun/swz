package lxh.fw.common.web.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *用于保存dataGrid中的数据以及分页排序情况的数据结构。
 */
public class PagingContext implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -2367218547976040218L;
    private String searchName;
    private int pageSize;
    private int pageNumber;
    private List<Map<String,String>> fullList=new ArrayList<Map<String,String>>();

    
    
    /**
     * @return the searchName
     */
    public String getSearchName() {
        return searchName;
    }


    /**
     * @param searchName the searchName to set
     */
    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }


    /**
     * @return the pageNumber
     */
    public int getPageNumber() {
        return pageNumber;
    }


    /**
     * @param pageNumber the pageNumber to set
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }


    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }


    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    /**
     * @return the fullList
     */
    public List<Map<String,String>> getFullList() {
        return fullList;
    }


    /**
     * @param fullList the fullList to set
     */
    public void setFullList(List<Map<String,String>> fullList) {
        this.fullList = fullList;
    }


}
