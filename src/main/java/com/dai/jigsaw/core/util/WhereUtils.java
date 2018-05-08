package com.dai.jigsaw.core.util;


import com.dai.jigsaw.core.exceptions.WhereParamNullException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WhereUtils {

	public static void main(String[] args) throws WhereParamNullException {
		String whereStirng = "[{logical:'and','field':'sex','operator':'equal','value':'男'},{logical:'or','field':'name','operator':'like','value':'a'},{logical:'or','field':'id','operator':'in','value':'1,2,3'}]";
		JSONArray ja = JSONArray.fromObject(whereStirng);
		System.out.println(jsonToWhere(ja));
	}
	
	/**
	 * [{logical:"and","field":"sex","operator":"equal","value":"男"}，
	 * {logical:"or","field":"name","operator":"like","value":"a"}，
	 * {logical:"or","field":"id","operator":"in","value":"1,2,3","type":
	 * "number"}],
	 * 
	 * @throws WhereParamNullException
	 */
	public static String jsonToWhere(JSONArray whereArr) throws WhereParamNullException {
		StringBuilder whereBuilder = new StringBuilder("1 = 1");
		JSONObject whereObj;
		for (int i = 0; i < whereArr.size(); i++) {
			whereObj = whereArr.getJSONObject(i);
			// 逻辑连接符
			if (StringUtil.isBlankByTrim(getStringByJSON(whereObj,"logical"))) {
				whereBuilder.append(" and ");
			} else if (getStringByJSON(whereObj,"logical").trim().toLowerCase().equals("and")
					|| getStringByJSON(whereObj,"logical").trim().toLowerCase().equals("or")) {
				whereBuilder.append(" " + getStringByJSON(whereObj,"logical").trim().toLowerCase() + " ");
			} else {
				whereBuilder.append(" and ");
			}
			// 查询字段
			if (StringUtil.isBlankByTrim(getStringByJSON(whereObj,"field"))) {
				throw new WhereParamNullException("未标明查询字段");
			} else {
				whereBuilder.append(" " + getStringByJSON(whereObj,"field")+ " ");
			}
			// 查询连接符号
			if (StringUtil.isBlankByTrim(getStringByJSON(whereObj,"operator"))) {
				throw new WhereParamNullException("未标明查询连接符");
			} else {
				whereBuilder.append(builderValue(getStringByJSON(whereObj,"operator"),getStringByJSON(whereObj,"value"),getStringByJSON(whereObj,"type")));
			}

		}
		return whereBuilder.toString();
	}
	
	
	private static String getStringByJSON(JSONObject obj,String key){
		if(obj.containsKey(key)){
			return obj.getString(key);
		}else{
			return null;
		}
	}
	
	
	
	private static String builderValue(String operator,String value,String type) throws WhereParamNullException{
		if(StringUtil.isBlankByTrim(value)){
			throw new WhereParamNullException("查询值为空！");
		}else{
			switch (operator.toLowerCase()) {
			case "equal":
				return " = " + getValue(value,type);
			case "like":
				return " like " + getValue(value,type,true);
			case "in":
				return " in " +getValue(value,type,false,true);
			default:
				return " = " + getValue(value,type);
			}
		}
	}
	
	private static String getValue(String value,String type) throws WhereParamNullException{
		return getValue(value,type,false,false);
	}
	
	private static String getValue(String value,String type,boolean isLike) throws WhereParamNullException{
		return getValue(value,type,isLike,false);
	}
	private static String getValue(String value,String type,boolean isLike,boolean isArray) throws WhereParamNullException{
		if(StringUtil.isBlankByTrim(value)){
			throw new WhereParamNullException("查询值为空！");
		}else{
			type = StringUtil.isBlankByTrim(type)?"":type;
			if(!isArray){
				if(isLike){
					return " '%"+value+"%' ";
				}
				switch (type) {
				case "number":
					return Double.parseDouble(value)+"";
				case "date":
					return "";
				case "datetime":
					return "";
				default:
					return "'"+value+"'";
				}
			}else{
				String[] strArr = value.split(",");
				StringBuilder sb = new StringBuilder();
				sb.append("(");
				for (int i = 0; i < strArr.length; i++) {
					switch (type) {
						case "number":
							sb.append(Double.parseDouble(strArr[i])).append(",");
							break;
						default:
							sb.append("'").append(strArr[i]).append("'").append(",");
							break;
					}
				}
				sb.deleteCharAt(sb.length()-1);
				sb.append(")");
				return sb.toString();
			}
		}
	}
	
	
	
}
