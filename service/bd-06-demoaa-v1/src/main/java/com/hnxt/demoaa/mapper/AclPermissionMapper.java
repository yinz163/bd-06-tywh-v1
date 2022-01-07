package com.hnxt.demoaa.mapper;

import com.hnxt.demoaa.entity.AclPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author yinz
 * @since 2021-10-21
 */
public interface AclPermissionMapper extends BaseMapper<AclPermission> {

	List<String> selectPermissionValueByUserId(String id);

	List<String> selectAllPermissionValue();

	List<AclPermission> selectPermissionByUserId(String userId);

}
