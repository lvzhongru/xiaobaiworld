package tcpdemo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
/*
 * �����̷�������
 * 
 * 
 */
public class Server {
	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(6767);
			System.out.println("�������Ѿ��������ȴ��ͻ�������...");
			//�ȴ����ӣ������ڴ�����
			Socket socket = ss.accept();//�����Ϻ󣬷����������ӵĿͻ���
			System.out.println(socket.getInetAddress());
			//��ȡ���׽��ֵ�������
			InputStream is = socket.getInputStream();
			byte[] b=new byte[1024];
			int len=is.read(b);
			System.out.println(new String(b,0,len));
			OutputStream op = socket.getOutputStream();
			op.write("�Ѿ��յ�������Ϣ".getBytes());
			ss.close();
			socket.close();
			
			
	
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
}
