package org.apache.mydubbo.provider;

import org.apache.mydubbo.api.GreetingServiceImpl;
import org.apache.mydubbo.lib.MyDubboProvider;

import java.io.IOException;

public class Application {
	public static void main(String[] args) throws IOException {
		new MyDubboProvider<>(new GreetingServiceImpl()).start();
	}
}
