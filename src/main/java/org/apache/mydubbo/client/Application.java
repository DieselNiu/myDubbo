package org.apache.mydubbo.client;

import org.apache.mydubbo.api.GreetingService;
import org.apache.mydubbo.lib.MyDubboClient;

import java.io.IOException;

public class Application {
	public static void main(String[] args) throws IOException {
		GreetingService service = new MyDubboClient<>(GreetingService.class).getRef();
		System.out.println(service.sayHi("lalalala"));
	}
}
