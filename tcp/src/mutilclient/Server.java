package mutilclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	// 创建线程池
	static ExecutorService es = Executors.newFixedThreadPool(3);

	public static void main(String[] args) {
		// 创建线程池
		ExecutorService es = Executors.newFixedThreadPool(3);
		try {
			ServerSocket ss = new ServerSocket(6868);
			System.out.println("服务器已经启动等待连接.....");

			while (true) {
				// 程序在此阻塞，等待客户端请求
				Socket socket = ss.accept();// 返回求请求客户端的套接字对象
				es.submit(new UserThread(socket));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

class UserThread implements Runnable {
	private Socket socket;

	public UserThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		
		try {
			//获取此套接字的输入流
			InputStream is = socket.getInputStream();
			byte[] b=new byte[1024];
			int len=is.read(b);
			System.out.println(new String(b,0,len));
			//获取此套接字的输出流
			OutputStream op = socket.getOutputStream();
			op.write("收到您的消息了".getBytes());
			socket.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

}
