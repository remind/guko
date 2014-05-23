package com.remind.rmvc.view.impl;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.remind.rmvc.GlobalConfig;
import com.remind.rmvc.context.HttpContext;
import com.remind.rmvc.internal.action.ActionResult;
import com.remind.rmvc.view.View;

/**
 * json视图
 * @author remind
 *
 */
public class JsonView implements View{

	private HttpContext httpContext = HttpContext.getCurrent();
	private Map<String, String> headerMap; //默认部份，可以覆盖
	
	private Object object;
	
	public JsonView(Object object) {
		this.object = object;
		headerMap.put("content-type", "text/json; charset=" + GlobalConfig.ENCODING);
	}
	
	public Map<String, String> getHeaderMap() {
		return this.headerMap;
	}
	
	@Override
	public void render(ActionResult actionResult) {
		try {
			for (String name : this.headerMap.keySet()) {
				httpContext.getResponse().setHeader(name, this.headerMap.get(name));
			}
			Writer writer = httpContext.getResponse().getWriter();
			writer.write(JSON.toJSONString(object));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
