package org.apache.mydubbo.lib;

import com.alibaba.fastjson2.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyDubboClient<T> {
	private Class<T> interfaceClass;
	private Socket socket;

	public MyDubboClient(Class<T> interfaceClass) throws IOException {
		this.interfaceClass = interfaceClass;
		this.socket = new Socket();
		this.socket.connect(new InetSocketAddress("127.0.0.1", 8888));
	}

	public T getRef() {
		return (T) Proxy.newProxyInstance(getClass().getClassLoader(),
			new Class[]{interfaceClass},
			(proxy, method, args) -> {
				MethodInfo info = new MethodInfo(method.getName(),
					Stream.of(args).collect(Collectors.toList()));
				byte[] bytes = ((JSON.toJSON(info)) + "\n").getBytes();

				MyDubboClient.this.socket.getOutputStream().write(bytes);
				MyDubboClient.this.socket.getOutputStream().flush();
				String returnValueJson = new BufferedReader(new InputStreamReader(MyDubboClient.this.socket.getInputStream())).readLine();
				return JSON.parse(returnValueJson);

			});
	}


}
