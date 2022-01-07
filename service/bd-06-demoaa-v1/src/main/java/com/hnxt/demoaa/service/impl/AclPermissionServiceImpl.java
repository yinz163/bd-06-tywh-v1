package com.hnxt.demoaa.service.impl;

import com.hnxt.demoaa.entity.AclPermission;
import com.hnxt.demoaa.entity.AclUser;
import com.hnxt.demoaa.mapper.AclPermissionMapper;
import com.hnxt.demoaa.service.IAclPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnxt.demoaa.service.IAclUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.acl.Acl;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author yinz
 * @since 2021-10-21
 */
@Service
public class AclPermissionServiceImpl extends ServiceImpl<AclPermissionMapper, AclPermission> implements IAclPermissionService {

	@Autowired
	private IAclUserService userService;

	@Override
	public List<String> selectPermissionValueByUserId(String id) {
		List<String> selectPermissionValueList = null;
		if(this.isSysAdmin(id)) {
			//如果是系统管理员，获取所有权限
			selectPermissionValueList = baseMapper.selectAllPermissionValue();
		} else {
			selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
		}
		return selectPermissionValueList;
	}

	/**
	 * 判断用户是否系统管理员
	 * @param userId
	 * @return
	 */
	private boolean isSysAdmin(String userId) {
		AclUser user = userService.getById(userId);

		if(null != user && "admin".equals(user.getUsername())) {
			return true;
		}
		return false;
	}
}
