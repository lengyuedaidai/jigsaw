package com.dai.jigsaw.web.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dai.jigsaw.core.feature.orm.mybatis.WhereParam;
import com.dai.jigsaw.core.generic.GenericDao;
import com.dai.jigsaw.core.generic.GenericServiceImpl;
import com.dai.jigsaw.web.dao.PermissionMapper;
import com.dai.jigsaw.web.model.Permission;
import com.dai.jigsaw.web.service.PermissionService;

/**
 * 权限Service实现类
 *
 * @author StarZou
 * @since 2014年6月10日 下午12:05:03
 */
@Service
public class PermissionServiceImpl extends GenericServiceImpl<Permission, Long> implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;


    @Override
    public GenericDao<Permission, Long> getDao() {
        return permissionMapper;
    }

    @Override
    public List<Permission> selectPermissionsByRoleId(Long roleId) {
        return permissionMapper.selectPermissionsByRoleId(roleId);
    }

	@Override
	public int deleteById(Long id) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public List<Permission> select(WhereParam whereParam) {
		// TODO 自动生成的方法存根
		return null;
	}
}
