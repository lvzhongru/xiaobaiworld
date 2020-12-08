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
			//�����ÿͻ����׽��ֶ���
			Socket socket=new Socket("127.0.0.1",7070);
            //�������׽��ֵ������
			OutputStream os = socket.getOutputStream();
			//�������׽��ֵ�������
			InputStream is = socket.getInputStream();
			//���������ݵ�����
			byte[] b=new byte[1024];
			//����Scanner��Ķ���
			Scanner sc=new Scanner(System.in);
			System.out.println("���������ĵ�¼���֣�");
			//�����û���
			String name = sc.next();
			//���û�������������
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
 * ��������
 * 
 */
class ReceiveThread implements Runnable{
	private InputStream is;
       public ReceiveThread(InputStream is) {
		this.is=is;
	}
       //���������ݵ�����
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
*��������
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
			System.out.println("��������Ҫ������Ϣ��");
			 message=sc.next();
			//����Ϣ����������
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