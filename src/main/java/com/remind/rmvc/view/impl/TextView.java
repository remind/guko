package com.remind.rmvc.view.impl;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.remind.rmvc.GlobalConfig;
import com.remind.rmvc.context.HttpContext;
import com.remind.rmvc.internal.action.ActionResult;
import com.remind.rmvc.view.View;

/**
 * 直接输出文本到浏览器
 * @author remind
 *
 */
public class TextView implements View {

	private HttpContext httpContext = HttpContext.getCurrent();
	private Map<String, String> headerMap = new HashMap<String, String>(); //默认部份，可以覆盖
	
	private String content;
	
	public TextView(String content) {
		this.content = content;
		headerMap.put("content-type", "text/plan; charset=" + GlobalConfig.ENCODING);
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
			writer.write(content);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
