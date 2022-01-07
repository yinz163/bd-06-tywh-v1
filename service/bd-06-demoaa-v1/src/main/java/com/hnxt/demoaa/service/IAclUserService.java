package com.hnxt.demoaa.service;

import com.hnxt.demoaa.entity.AclUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author yinz
 * @since 2021-10-21
 */
public interface IAclUserService extends IService<AclUser> {

	AclUser selectByUsername(String username);
}
