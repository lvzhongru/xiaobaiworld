package tcpdemo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
/*
 * 网络编程服务器端
 * 
 * 
 */
public class Server {
	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(6767);
			System.out.println("服务器已经启动，等待客户端连接...");
			//等待连接，程序在此阻塞
			Socket socket = ss.accept();//连接上后，返回请求连接的客户端
			System.out.println(socket.getInetAddress());
			//获取此套接字的输入流
			InputStream is = socket.getInputStream();
			byte[] b=new byte[1024];
			int len=is.read(b);
			System.out.println(new String(b,0,len));
			OutputStream op = socket.getOutputStream();
			op.write("已经收到您的消息".getBytes());
			ss.close();
			socket.close();
			
			
	
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
}
