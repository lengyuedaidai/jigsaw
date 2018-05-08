package com.dai.jigsaw.core.generic;

import java.util.List;

import com.dai.jigsaw.core.feature.orm.mybatis.Page;
import com.dai.jigsaw.core.feature.orm.mybatis.WhereParam;


/**
 * 所有自定义Service的顶级接口,封装常用的增删查改操作
 * <p/>
 * Model : 代表数据库中的表 映射的Java对象类型
 * PK :代表对象的主键类型
 *
 * @author daidai
 * @since 2014年6月9日 下午6:14:06
 */
public interface GenericService<Model, PK> {

    /**
     * 插入对象
     *
     * @param model 对象
     */
    int insert(Model model);

    /**
     * 更新对象
     *
     * @param model 对象
     */
    int update(Model model);
    
    /**
     * 通过主键, 删除对象
     *
     * @param id 主键
     */
    int delete(Model model);

    /**
     * 通过主键, 删除对象
     *
     * @param id 主键
     */
    int deleteById(PK id);
    
    

    /**
     * 通过主键, 查询对象
     *
     * @param id 主键
     * @return model 对象
     */
    Model selectById(PK id);
    /**
     * 按条件分页查询
     * @param page
     * @param whereParam
     * @return
     */
    List<Model> selectPage(Page<Model> page, WhereParam whereParam);
    /**
     * 按条件查询
     * @param whereParam
     * @return
     */
    List<Model> select(WhereParam whereParam);

	

}
