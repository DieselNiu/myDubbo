package org.apache.mydubbo.lib;


import com.alibaba.fastjson2.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class MyDubboProvider<T> {
	private T serviceImpl;
	private ServerSocket ss;


	public MyDubboProvider(T serviceImpl) throws IOException {
		this.serviceImpl = serviceImpl;
		this.ss = new ServerSocket(8888);
	}

	public void start() throws IOException {
		while (true) {
			Socket socket = ss.accept();
			System.out.println("Accept!");
			new WorkerThread(socket).start();
		}
	}

	private class WorkerThread extends Thread {
		private Socket socket;

		public WorkerThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				String line = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
				System.out.println(line);
				MethodInfo methodInfo = JSON.parseObject(line, MethodInfo.class);
				Method method = serviceImpl.getClass().getMethod(methodInfo.getMethodName(),
					methodInfo.getParams().stream().map(Object::getClass).toArray(Class[]::new));
				Object returnValue = method.invoke(serviceImpl, methodInfo.params.toArray());
				socket.getOutputStream().write((JSON.toJSONString(returnValue) + "\n").getBytes());
				socket.getOutputStream().flush();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
