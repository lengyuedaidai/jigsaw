package com.dai.jigsaw.web.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.dai.jigsaw.core.feature.orm.mybatis.Page;
import com.dai.jigsaw.core.feature.orm.mybatis.WhereParam;
import com.dai.jigsaw.core.generic.GenericDao;
import com.dai.jigsaw.web.model.User;
import com.dai.jigsaw.web.model.UserExample;

/**
 * 用户Dao接口
 * 
 * @author StarZou
 * @since 2014年7月5日 上午11:49:57
 **/
public interface UserMapper extends GenericDao<User, Long> {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    @Override
	int deleteByPrimaryKey(Long id);

    int insert(User record);

    @Override
	int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    @Override
	User selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    @Override
	int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 用户登录验证查询
     * 
     * @param record
     * @return
     */
    User authentication(@Param("record") User record);

    /**
     * 分页条件查询
     * 
     * @param page
     * @param example
     * @return
     */
    List<User> selectByExampleAndPage(Page<User> page, UserExample example);
    /**
     * 分页条件查询
     * 
     * @param page
     * @param example
     * @return
     */
    @Override
	List<User> selectByWhereParamAndPage(Page<User> page, WhereParam whereParam);
}