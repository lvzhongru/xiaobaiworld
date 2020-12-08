package chatroom;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
/*
 * 
 * 服务器端代码
 * 
 */
public class Server {
	// 创建一个存放客户端套接字对象的用户列表
	public static Map<String, Socket> map = new HashMap();

	public static void main(String[] args) {
		try {

			// 创服务器套接字对象
			ServerSocket ss = new ServerSocket(7070);
			System.out.println("服务器已经启动，等待你们登录.....");
			while (true) {
				// 程序在此阻塞，等待连接
				Socket socket = ss.accept();// 连接后返回连接的套接字对象

				// 获取该客户端套接字的输入流
				InputStream is = socket.getInputStream();
				// 获取该客户端套接字的输出流
				OutputStream os = socket.getOutputStream();
				// 返回信息告知客户端他已经登录成功
				os.write("您已经登录成功，赶快去找小伙伴聊天吧！".getBytes());
				// 定义一个存放数据的数组
				byte[] b = new byte[1024];
				// 读取登录的姓名
				is.read(b);
				// 将名字转为字符形式
				String name =new String(b);
				System.out.println(name+"已经登录！");
				// 将该客户端套接字对象添加到用户列表
				map.put(name, socket);
				System.out.println("在线人数" + map.size());
				// 创建客户端线程
				new Thread(new UserThread(socket, is, map,name,os)).start();

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

/*
 * 处理接受的信息
 * 
 */
class UserThread implements Runnable {
	private Socket socket;
	private InputStream is;
	private Map<String,Socket> map;
	private String name;
    private OutputStream os;
	public UserThread(Socket socket, InputStream is, Map<String,Socket> map,String name,OutputStream os) {
		this.socket = socket;
		this.is = is;
		this.map = map;
		this.name=name;
		this.os=os;
	}

	@Override
	public void run() {
		String message=null;
		while (true) { // 循环接收该客户端套接字的信息
			// 定义存放数据的数组
			byte[] b = new byte[1024];
		
			try {
				// 读取数据并返回读取的数据长度
		     		int len = is.read(b);
					// 将接收到的信息转为字符串
					message = new String(b, 0, len);
					//
                    System.out.println(message);
			} catch (IOException e) {

				e.printStackTrace();
			}
				// 判断输入的信息的类型
				if (message.startsWith("@") && message.contains(":")) {// 判断输入的信息是否以@开头且包含“:"符号
					// 获取“：”符号第一次出现的下标
					int index = message.indexOf(":");
					// 获取接收信息的对象名字
					String rename = message.substring(1, index);
					//
					System.out.println(rename);
					//获取要发送的信息
					String mess=message.substring(index+1);
					//
					System.out.println(mess);
                    for(String aname:map.keySet()) {//遍历客户端套接字的数组
                    	System.out.println("111111111111111");
                    	System.out.println("rename---->"+rename);
                    	System.out.println("aname-------->"+aname);
                    	if(aname.equals(rename)) {//如果名字相等将消息发给该客户端套接字对象
                    		//获取接收信息的客户端套接字对象
                    		Socket sesocket = map.get(aname);
                    		//
                    		System.out.println("22222222222222");
                    		System.out.println(sesocket.toString());
                    		//获取接收信息的套接字对象的输出流
                    		OutputStream opre;
							try {
								opre = sesocket.getOutputStream();
								opre.write((name+"悄悄跟你说："+mess).getBytes());
								opre.flush();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                    		
                    	}
                    	else {
                    		//通知客户端他发送的用户不存在
                    		try {
								os.write((rename+"不存在").getBytes());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                    		try {
								os.flush();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                    	}
                    	
                    }
				}
				
				else {//将信息发给所有客户端
					 for(String rename:map.keySet()) {//遍历客户端套接字的数组
	                    
	                    		//获取接收信息的客户端套接字对象
	                    		Socket sesocket = map.get(rename);
	                    		//获取接收信息的套接字对象的输出流
	                    		OutputStream opre;
								try {
									opre = sesocket.getOutputStream();
									opre.write((name+"说："+message).getBytes());
									opre.flush();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	                    		//将信息发给接收对象
	                    		
	                          
					 }
					  }
				
                   
                   
			

		}
		
	}

}
