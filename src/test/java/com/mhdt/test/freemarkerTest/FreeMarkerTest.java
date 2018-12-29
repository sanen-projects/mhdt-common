package com.mhdt.test.freemarkerTest;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mhdt.analyse.FreeMarker;
import com.mhdt.toolkit.Bean;

public class FreeMarkerTest {
	
	public static void main(String[] args) throws IOException {
		FreeMarker freeMarker = new FreeMarker();
		freeMarker.loadDirectoryTemplate(new File("template"));
		
		
		
		Structure structure = new Structure();
		structure.setTableName("tableName");
		structure.setPosition("sum");
		structure.setColumns(JSONObject.toJSONString(Arrays.asList("num","big_name")));
		structure.setParamers(JSONObject.toJSONString(Arrays.asList("year","date")));
		
		
		Map<String,Object> map = Bean.toMap(structure);
		
		map.put("paramers", JSON.parseArray(structure.getParamers()));
		
		String str = freeMarker.out("baseLine.ftl", map);
		
		System.out.println(str);
	}

}
