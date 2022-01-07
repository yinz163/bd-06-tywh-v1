package com.hnxt.demoaa.service;

import com.hnxt.demoaa.entity.AclPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author yinz
 * @since 2021-10-21
 */
public interface IAclPermissionService extends IService<AclPermission> {

	List<String> selectPermissionValueByUserId(String id);
}
