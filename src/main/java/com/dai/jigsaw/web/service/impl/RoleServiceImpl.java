package com.dai.jigsaw.web.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dai.jigsaw.core.feature.orm.mybatis.WhereParam;
import com.dai.jigsaw.core.generic.GenericDao;
import com.dai.jigsaw.core.generic.GenericServiceImpl;
import com.dai.jigsaw.web.dao.RoleMapper;
import com.dai.jigsaw.web.model.Role;
import com.dai.jigsaw.web.service.RoleService;

/**
 * 角色Service实现类
 *
 * @author StarZou
 * @since 2014年6月10日 下午4:16:33
 */
@Service
public class RoleServiceImpl extends GenericServiceImpl<Role, Long> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public GenericDao<Role, Long> getDao() {
        return roleMapper;
    }

    @Override
    public List<Role> selectRolesByUserId(Long userId) {
        return roleMapper.selectRolesByUserId(userId);
    }

	@Override
	public int deleteById(Long id) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public List<Role> select(WhereParam whereParam) {
		// TODO 自动生成的方法存根
		return null;
	}

}
