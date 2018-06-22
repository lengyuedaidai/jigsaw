package com.dai.jigsaw.web.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dai.jigsaw.core.aspect.annotation.AccessToken;
import com.dai.jigsaw.core.entity.ErrorResult;
import com.dai.jigsaw.core.entity.Result;
import com.dai.jigsaw.core.entity.SuccessResult;
import com.dai.jigsaw.core.exceptions.WhereParamNullException;
import com.dai.jigsaw.core.feature.orm.mybatis.Page;
import com.dai.jigsaw.core.feature.orm.mybatis.PageResult;
import com.dai.jigsaw.core.feature.orm.mybatis.WhereParam;
import com.dai.jigsaw.core.generic.GenericService;
import com.dai.jigsaw.core.util.StringUtil;
import com.dai.jigsaw.core.util.WhereUtils;
import com.dai.jigsaw.web.model.User;
import com.dai.jigsaw.web.token.Token;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import oracle.jdbc.proxy.annotation.Methods;

/**
 * 
 **/
@RestController
@RequestMapping("/dataServices")
public class DataBaseController {
	public static long EFFECTIVE_TIME = 60*60*5;
	@Autowired
	Token tokenManager;
	
	JsonConfig jsonConfig;

	public JsonConfig getJsonConfig() {
		if (jsonConfig == null) {
			jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(java.util.Date.class,
					new JsonValueProcessor() {
						private final String format = "yyyy-MM-dd";

						public Object processObjectValue(String key,
								Object value, JsonConfig arg2) {
							if (value == null)
								return "";
							if (value instanceof java.util.Date) {
								String str = new SimpleDateFormat(format)
										.format((java.util.Date) value);
								return str;
							}
							return value.toString();
						}

						public Object processArrayValue(Object value,
								JsonConfig arg1) {
							return null;
						}

					});
		}
		return jsonConfig;
	}

	WebApplicationContext wtx = ContextLoader.getCurrentWebApplicationContext();

	/**
	 * 用户登出
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("userInfo");
		// 登出操作
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "login";
	}
	
	@RequestMapping(value = "/isLogin")
	public Result isLogin(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		// 已登陆则 跳到首页
		if (subject.isAuthenticated()) {
			String token = subject.getSession().getAttribute("token").toString();
			if(tokenManager.validate(token)){
				JSONObject obj = new JSONObject();
				obj.put("token", token);
				Result result = new SuccessResult<JSONObject>(obj);
				return result;	
			}
		}
		subject.logout();
		return new ErrorResult("用户未登录 ！");
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Result login(HttpServletRequest request, String username,String password) {
		try {
			Subject subject = SecurityUtils.getSubject();
			// 已登陆则 跳到首页
			if(StringUtil.isBlankByTrim(username)||StringUtil.isBlankByTrim(password)){
				return new ErrorResult("用户名或密码不能为空 ！");
			}
			// 身份验证
			UsernamePasswordToken a = new UsernamePasswordToken(
					username,password);
			subject.login(a);
			// 验证成功在Session中保存用户信息
			return createTokenResult(subject,username);
		} catch (AuthenticationException e) {
			// 身份验证失败
			return new ErrorResult("用户名或密码错误 ！");
		}
	}
	private Result createTokenResult(Subject subject,String username){
		Map info = new HashMap<String, String>();
		info.put("userName", username);
		JSONObject obj = new JSONObject();
		String token = tokenManager.create(info, EFFECTIVE_TIME);
		obj.put("token", token);
		subject.getSession().setAttribute("token", token);
		Result result = new SuccessResult<JSONObject>(obj);
		return result;
	}
	

	@RequestMapping("/test")
	@AccessToken
	public Result test(HttpServletRequest request, String data) {
		JsonConfig jsonConfig = new JsonConfig();
		JSONObject obj = new JSONObject();
		obj.put("aa", "aa");
		Result result = new SuccessResult<JSONObject>(obj);
		return result;
	}
	
	@RequestMapping("/get")
	@AccessToken
	public Result select(HttpServletRequest request, String data) {
		JsonConfig jsonConfig = new JsonConfig();
		JSONObject result = new JSONObject();
		try {
			JSONObject dataInfo =  JSONObject.fromObject(data);
			WhereParam whereParam = new WhereParam();
			whereParam.setWhereString(
					WhereUtils.jsonToWhere(dataInfo.getJSONArray("where")));
			List list = ((GenericService) wtx.getBean(
					dataInfo.getString("table").toUpperCase() + "_SERVICE"))
							.select(whereParam);
			return new SuccessResult<List>(JSONArray.fromObject(list, getJsonConfig()));
		} catch (WhereParamNullException e) {
			// 身份验证失败
			return new ErrorResult(e.getMessage());
		}
	}

	@RequestMapping("/page")
	@AccessToken
	public Result selectPage(HttpServletRequest request, String data) {
		JSONObject arg = JSONObject.fromObject(data);
		JSONObject dataInfo = arg.getJSONObject("data");
		Page page = new Page<>(dataInfo.getInt("pageNo"),
				dataInfo.getInt("pageSize"));
		try {
			WhereParam whereParam = new WhereParam();
			whereParam.setWhereString(
					WhereUtils.jsonToWhere(dataInfo.getJSONArray("where")));
			List list = ((GenericService) wtx.getBean(
					dataInfo.getString("table").toUpperCase() + "_SERVICE"))
							.selectPage(page, whereParam);
			JSONObject result = new JSONObject();
			result.put("data", JSONArray.fromObject(list, getJsonConfig()));
			result.put("page",
					new PageResult(page.getPageNo(), page.getPageSize(),
							page.getTotalCount(), page.getTotalPages()));
			return new SuccessResult<JSONObject>(result);
		} catch (WhereParamNullException e) {
			return new ErrorResult(e.getMessage());
		}
	}

	@RequestMapping("/post")
	@AccessToken
	public Result insert(HttpServletRequest request, String data) {
		JSONObject result = new JSONObject();
		JSONObject arg = JSONObject.fromObject(data);
		JSONObject dataInfo = arg.getJSONObject("data");
		String tableName = dataInfo.getString("table").toUpperCase();
		int i = ((GenericService) wtx.getBean(tableName + "_SERVICE"))
				.insert(JSONObject.toBean(dataInfo.getJSONObject("model"),
						wtx.getBean(tableName + "_MODEL").getClass()));
		if (i > 0) {
			return new SuccessResult<JSONObject>(result);
		} else {
			return new ErrorResult("未插入数据");
		}
	}

	@RequestMapping("/put")
	@AccessToken
	public Result update(HttpServletRequest request, String data) {
		JSONObject result = new JSONObject();
		JSONObject arg = JSONObject.fromObject(data);
		JSONObject dataInfo = arg.getJSONObject("data");
		String tableName = dataInfo.getString("table").toUpperCase();
		int i = ((GenericService) wtx.getBean(tableName + "_SERVICE"))
				.update(JSONObject.toBean(dataInfo.getJSONObject("model"),
						wtx.getBean(tableName + "_MODEL").getClass()));
		if (i > 0) {
			return new SuccessResult<JSONObject>(result);
		} else {
			return new ErrorResult("未修改数据");
		}
	}

	@RequestMapping("/delete")
	@AccessToken
	public Result delete(HttpServletRequest request, String data) {
		JSONObject result = new JSONObject();
		JSONObject arg = JSONObject.fromObject(data);
		JSONObject dataInfo = arg.getJSONObject("data");
		String tableName = dataInfo.getString("table").toUpperCase();
		int i = ((GenericService) wtx.getBean(tableName + "_SERVICE"))
				.delete(JSONObject.toBean(dataInfo.getJSONObject("model"),
						wtx.getBean(tableName + "_MODEL").getClass()));
		if (i > 0) {
			return new SuccessResult<JSONObject>(result);
		} else {
			return new ErrorResult("未删除数据");
		}
	}

	@RequestMapping("/delByIds")
	@ResponseBody
	public JSONObject delByIds(HttpServletRequest request, String data) {
		return null;
	}

}