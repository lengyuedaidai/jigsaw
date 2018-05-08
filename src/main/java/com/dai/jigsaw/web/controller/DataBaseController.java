package com.dai.jigsaw.web.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dai.jigsaw.core.aspect.annotation.AccessToken;
import com.dai.jigsaw.core.entity.Result;
import com.dai.jigsaw.core.entity.SuccessResult;
import com.dai.jigsaw.core.exceptions.WhereParamNullException;
import com.dai.jigsaw.core.feature.orm.mybatis.Page;
import com.dai.jigsaw.core.feature.orm.mybatis.PageResult;
import com.dai.jigsaw.core.feature.orm.mybatis.WhereParam;
import com.dai.jigsaw.core.generic.GenericService;
import com.dai.jigsaw.core.util.WhereUtils;
import com.dai.jigsaw.web.token.Token;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

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

	@RequestMapping("/login")
	public Result login(HttpServletRequest request, String username,String password) {
		Map info = new HashMap<String, String>();
		info.put("userName", username);
		JSONObject obj = new JSONObject();
		String token = tokenManager.create(info, EFFECTIVE_TIME);
		obj.put("token", token);
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
	public JSONObject select(HttpServletRequest request, String data) {
		JsonConfig jsonConfig = new JsonConfig();
		JSONObject result = new JSONObject();
		try {
			JSONObject arg = JSONObject.fromObject(data);
			JSONObject dataInfo = arg.getJSONObject("data");
			WhereParam whereParam = new WhereParam();
			whereParam.setWhereString(
					WhereUtils.jsonToWhere(dataInfo.getJSONArray("where")));
			List list = ((GenericService) wtx.getBean(
					dataInfo.getString("table").toUpperCase() + "_SERVICE"))
							.select(whereParam);
			result.put("data", JSONArray.fromObject(list, getJsonConfig()));
			result.put("state", 1);
			result.put("error_msg", "");
		} catch (WhereParamNullException e) {
			result.put("state", 0);
			result.put("error_msg", e.getMessage());
			result.put("data", null);
		}
		return result;
	}

	@RequestMapping("/page")
	@AccessToken
	public JSONObject selectPage(HttpServletRequest request, String data) {
		JSONObject result = new JSONObject();
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
			result.put("data", JSONArray.fromObject(list, getJsonConfig()));
			result.put("state", 1);
			result.put("error_msg", "");
			result.put("page",
					new PageResult(page.getPageNo(), page.getPageSize(),
							page.getTotalCount(), page.getTotalPages()));
		} catch (WhereParamNullException e) {
			result.put("state", 0);
			result.put("error_msg", e.getMessage());
			result.put("data", null);
			result.put("page",
					new PageResult(page.getPageNo(), page.getPageSize(),
							page.getTotalCount(), page.getTotalPages()));
		}
		return result;
	}

	@RequestMapping("/post")
	@AccessToken
	public JSONObject insert(HttpServletRequest request, String data) {
		JSONObject result = new JSONObject();
		JSONObject arg = JSONObject.fromObject(data);
		JSONObject dataInfo = arg.getJSONObject("data");
		String tableName = dataInfo.getString("table").toUpperCase();
		int i = ((GenericService) wtx.getBean(tableName + "_SERVICE"))
				.insert(JSONObject.toBean(dataInfo.getJSONObject("model"),
						wtx.getBean(tableName + "_MODEL").getClass()));
		if (i > 0) {
			result.put("state", 1);
			result.put("error_msg", "");
		} else {
			result.put("state", 0);
			result.put("error_msg", "未插入数据");
		}
		return result;
	}

	@RequestMapping("/put")
	@AccessToken
	public JSONObject update(HttpServletRequest request, String data) {
		JSONObject result = new JSONObject();
		JSONObject arg = JSONObject.fromObject(data);
		JSONObject dataInfo = arg.getJSONObject("data");
		String tableName = dataInfo.getString("table").toUpperCase();
		int i = ((GenericService) wtx.getBean(tableName + "_SERVICE"))
				.update(JSONObject.toBean(dataInfo.getJSONObject("model"),
						wtx.getBean(tableName + "_MODEL").getClass()));
		if (i > 0) {
			result.put("state", 1);
			result.put("error_msg", "");
		} else {
			result.put("state", 0);
			result.put("error_msg", "未插入数据");
		}
		return result;
	}

	@RequestMapping("/delete")
	@AccessToken
	public JSONObject delete(HttpServletRequest request, String data) {
		JSONObject result = new JSONObject();
		JSONObject arg = JSONObject.fromObject(data);
		JSONObject dataInfo = arg.getJSONObject("data");
		String tableName = dataInfo.getString("table").toUpperCase();
		int i = ((GenericService) wtx.getBean(tableName + "_SERVICE"))
				.delete(JSONObject.toBean(dataInfo.getJSONObject("model"),
						wtx.getBean(tableName + "_MODEL").getClass()));
		if (i > 0) {
			result.put("state", 1);
			result.put("error_msg", "");
		} else {
			result.put("state", 0);
			result.put("error_msg", "未插入数据");
		}
		return result;
	}

	@RequestMapping("/delByIds")
	@ResponseBody
	public JSONObject delByIds(HttpServletRequest request, String data) {
		return null;
	}

}