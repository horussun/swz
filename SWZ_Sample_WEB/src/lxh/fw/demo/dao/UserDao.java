package lxh.fw.demo.dao;

import java.util.List;

import lxh.fw.common.dao.IBaseDao;
import lxh.fw.demo.model.UserModel;
import lxh.fw.demo.model.UserQueryModel;

/**
 * @author horus
 * @version 1.0, 2012-11-11
 */
public interface UserDao extends IBaseDao<UserModel, Integer> {
    
    List<UserModel> query(int pn, int pageSize, UserQueryModel command);

    int countQuery(UserQueryModel command);

}
