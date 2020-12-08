package chatroom;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		try {
			//创建该客户端套接字对象
			Socket socket=new Socket("127.0.0.1",7070);
            //创建该套接字的输出流
			OutputStream os = socket.getOutputStream();
			//创建该套接字的输入流
			InputStream is = socket.getInputStream();
			//定义存放数据的数组
			byte[] b=new byte[1024];
			//创建Scanner类的对象
			Scanner sc=new Scanner(System.in);
			System.out.println("请输入您的登录名字！");
			//输入用户名
			String name = sc.next();
			//将用户名传给服务器
			os.write(name.getBytes());
			new Thread(new ReceiveThread(is)).start();
			new Thread(new SendThread(os)).start();
			
			
			
			
			
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
/*
 * 
 * 接收数据
 * 
 */
class ReceiveThread implements Runnable{
	private InputStream is;
       public ReceiveThread(InputStream is) {
		this.is=is;
	}
       //定义存放数据的数组
       byte[] b=new byte[1024];
	@Override
	public void run() {
		try {
			is.read(b);
			String str=new String(b);
			if(str!=null) {
				System.out.println(str);
			}
			else {
				System.out.println("kong");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}
	
	
}
/*
*
*发送数据
*
*/
class SendThread implements Runnable{
	private OutputStream os;
	String message;
   Scanner sc=new Scanner(System.in);
	public SendThread(OutputStream os) {
		this.os=os;
	}
	@Override
	public void run() {
         while(true) {
			System.out.println("请输入您要发的信息：");
			 message=sc.next();
			//将信息传给服务器
			try {
				if(message!=null) {
				os.write(message.getBytes());
				os.flush();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
	
	}
	
	}
	
}