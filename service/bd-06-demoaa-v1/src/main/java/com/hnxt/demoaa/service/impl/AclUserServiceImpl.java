package com.hnxt.demoaa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hnxt.demoaa.entity.AclUser;
import com.hnxt.demoaa.mapper.AclUserMapper;
import com.hnxt.demoaa.service.IAclUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author yinz
 * @since 2021-10-21
 */
@Service
public class AclUserServiceImpl extends ServiceImpl<AclUserMapper, AclUser> implements IAclUserService {

	@Override
	public AclUser selectByUsername(String username) {
		return baseMapper.selectOne(new QueryWrapper<AclUser>().eq("username", username));
	}
}
