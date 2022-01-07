package com.hnxt.demoaa.controller;


import com.hnxt.common.vo.R;
import com.hnxt.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author yinz
 * @since 2021-10-21
 */
@RestController
@RequestMapping("/demoaa/acl-user")
public class AclUserController {

	/*@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public R login(@RequestBody User user) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		return R.ok().data("info", authenticate);
	}*/

	@GetMapping("info")
	@PreAuthorize("hasAuthority('user.list')")
	public R info(){
		//获取当前登录用户用户名
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
//		Map<String, Object> userInfo = indexService.getUserInfo(username);
		return R.ok().data("userName", username);
	}

	@GetMapping("info1")
	@PreAuthorize("hasAuthority('user.list1')")
	public R info1(){
		int i = 10 / 0;
		//获取当前登录用户用户名
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
//		Map<String, Object> userInfo = indexService.getUserInfo(username);
		return R.ok().data("userName", username);
	}

}

