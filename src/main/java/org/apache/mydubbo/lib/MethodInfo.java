package org.apache.mydubbo.lib;

import java.util.List;

public class MethodInfo {
	String methodName;
	List<Object> params;

	public MethodInfo(String methodName, List<Object> params) {
		this.methodName = methodName;
		this.params = params;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public List<Object> getParams() {
		return params;
	}

	public void setParams(List<Object> params) {
		this.params = params;
	}
}
