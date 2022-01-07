package com.hnxt.demoaa.service.impl;

import com.hnxt.demoaa.entity.AclUser;
import com.hnxt.demoaa.service.IAclPermissionService;
import com.hnxt.demoaa.service.IAclUserService;
import com.hnxt.security.entity.SecurityUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  * <p>
 *  * 自定义userDetailsService - 认证用户详情
 *  * </p>
 * @Author yinz
 * @Date 2021/10/21 - 10:50
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IAclUserService userService;

	@Autowired
	private IAclPermissionService permissionService;

	/***
	 * 根据账号获取用户信息
	 * @param username:
	 * @return: org.springframework.security.core.userdetails.UserDetails
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 从数据库中取出用户信息
		AclUser user = userService.selectByUsername(username);

		// 判断用户是否存在
		if (null == user){
			//throw new UsernameNotFoundException("用户名不存在！");
		}
		// 返回UserDetails实现类
		com.hnxt.security.entity.User curUser = new com.hnxt.security.entity.User();
		BeanUtils.copyProperties(user,curUser);

		List<String> authorities = permissionService.selectPermissionValueByUserId(user.getId());
		SecurityUser securityUser = new SecurityUser(curUser);
		securityUser.setPermissionValueList(authorities);
		return securityUser;
	}
}
