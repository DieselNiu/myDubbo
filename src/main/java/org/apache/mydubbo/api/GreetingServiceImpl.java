package org.apache.mydubbo.api;

public class GreetingServiceImpl implements GreetingService {
	@Override
	public String sayHi(String name) {
		return "hi, " + name;
	}
}
