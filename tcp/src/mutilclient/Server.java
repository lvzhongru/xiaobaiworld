package mutilclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	// �����̳߳�
	static ExecutorService es = Executors.newFixedThreadPool(3);

	public static void main(String[] args) {
		// �����̳߳�
		ExecutorService es = Executors.newFixedThreadPool(3);
		try {
			ServerSocket ss = new ServerSocket(6868);
			System.out.println("�������Ѿ������ȴ�����.....");

			while (true) {
				// �����ڴ��������ȴ��ͻ�������
				Socket socket = ss.accept();// ����������ͻ��˵��׽��ֶ���
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
			//��ȡ���׽��ֵ�������
			InputStream is = socket.getInputStream();
			byte[] b=new byte[1024];
			int len=is.read(b);
			System.out.println(new String(b,0,len));
			//��ȡ���׽��ֵ������
			OutputStream op = socket.getOutputStream();
			op.write("�յ�������Ϣ��".getBytes());
			socket.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

}
