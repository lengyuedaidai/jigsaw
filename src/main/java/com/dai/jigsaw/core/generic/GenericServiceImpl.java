package com.dai.jigsaw.core.generic;

import java.util.List;

import com.dai.jigsaw.core.feature.orm.mybatis.Page;
import com.dai.jigsaw.core.feature.orm.mybatis.WhereParam;


/**
 * GenericService的实现类, 其他的自定义 ServiceImpl, 继承自它,可以获得常用的增删查改操作,
 * 未实现的方法有 子类各自实现
 * <p/>
 * Model : 代表数据库中的表 映射的Java对象类型
 * PK :代表对象的主键类型
 *
 * @author daidai
 * @since 2014年6月9日 下午6:14:06
 */
public abstract  class GenericServiceImpl<Model, PK> implements GenericService<Model, PK> {

    /**
     * 定义成抽象方法,由子类实现,完成dao的注入
     *
     * @return GenericDao实现类
     */
    public abstract GenericDao<Model, PK> getDao();

    /**
     * 插入对象
     *
     * @param model 对象
     */
    @Override
	public int insert(Model model) {
        return getDao().insertSelective(model);
    }

    /**
     * 更新对象
     *
     * @param model 对象
     */
    @Override
	public int update(Model model) {
        return getDao().updateByPrimaryKeySelective(model);
    }

    /**
     * 通过主键, 删除对象
     *
     * @param id 主键
     */
    @Override
	public int delete(Model model) {
        return getDao().deleteByPrimaryKeySelective(model);
    }

    /**
     * 通过主键, 查询对象
     *
     * @param id 主键
     * @return
     */
    @Override
	public Model selectById(PK id) {
        return getDao().selectByPrimaryKey(id);
    }

	@Override
	public int deleteById(PK id) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public List<Model> selectPage(Page<Model> page, WhereParam whereParam) {
		return getDao().selectByWhereParamAndPage(page, whereParam);
	}

	@Override
	public List<Model> select(WhereParam whereParam) {
		// TODO 自动生成的方法存根
		return getDao().selectByWhereParam(whereParam);
	}

}
