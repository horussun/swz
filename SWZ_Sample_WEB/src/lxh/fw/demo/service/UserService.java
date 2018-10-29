package lxh.fw.demo.service;

import lxh.fw.common.pagination.Page;
import lxh.fw.common.service.IBaseService;
import lxh.fw.demo.model.UserModel;
import lxh.fw.demo.model.UserQueryModel;

public interface UserService extends IBaseService<UserModel, Integer> {

    Page<UserModel> query(int pn, int pageSize, UserQueryModel command);
}
