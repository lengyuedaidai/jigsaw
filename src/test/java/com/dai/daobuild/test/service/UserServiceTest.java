package com.dai.daobuild.test.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.dai.jigsaw.core.feature.orm.mybatis.Page;
import com.dai.jigsaw.core.feature.orm.mybatis.WhereParam;
import com.dai.jigsaw.core.feature.test.TestSupport;

public class UserServiceTest extends TestSupport {

	@Resource
	//private CsService csService;

	@Test
	public void test_page() {
/*		Page<Cs> page = new Page<Cs>(1,3);
		
		WhereParam whereParam = new WhereParam();
		//whereParam.setWhereString(whereString);
		try {
			List<Cs> data = csService.selectPage(page, whereParam);
			System.out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}*/


		/*
		 * User model = new User(); model.setUsername("daidai");
		 * model.setPassword(ApplicationUtils.sha256Hex("123456"));
		 * model.setCreateTime(new Date()); userService.insert(model);
		 */
	}

	// @Test
	public void test_insert() {
		/*
		 * User model = new User(); model.setUsername("daidai");
		 * model.setPassword(ApplicationUtils.sha256Hex("123456"));
		 * model.setCreateTime(new Date()); userService.insert(model);
		 */
	}

	// @Test
	public void test_10insert() {
		/*
		 * for (int i = 0; i < 10; i++) { User model = new User();
		 * model.setUsername("daidai" + i);
		 * model.setPassword(ApplicationUtils.sha256Hex("123456"));
		 * model.setCreateTime(new Date()); userService.insert(model); }
		 */
	}

}
