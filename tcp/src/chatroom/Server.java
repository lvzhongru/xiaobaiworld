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
 * �������˴���
 * 
 */
public class Server {
	// ����һ����ſͻ����׽��ֶ�����û��б�
	public static Map<String, Socket> map = new HashMap();

	public static void main(String[] args) {
		try {

			// ���������׽��ֶ���
			ServerSocket ss = new ServerSocket(7070);
			System.out.println("�������Ѿ��������ȴ����ǵ�¼.....");
			while (true) {
				// �����ڴ��������ȴ�����
				Socket socket = ss.accept();// ���Ӻ󷵻����ӵ��׽��ֶ���

				// ��ȡ�ÿͻ����׽��ֵ�������
				InputStream is = socket.getInputStream();
				// ��ȡ�ÿͻ����׽��ֵ������
				OutputStream os = socket.getOutputStream();
				// ������Ϣ��֪�ͻ������Ѿ���¼�ɹ�
				os.write("���Ѿ���¼�ɹ����Ͽ�ȥ��С�������ɣ�".getBytes());
				// ����һ��������ݵ�����
				byte[] b = new byte[1024];
				// ��ȡ��¼������
				is.read(b);
				// ������תΪ�ַ���ʽ
				String name =new String(b);
				System.out.println(name+"�Ѿ���¼��");
				// ���ÿͻ����׽��ֶ�����ӵ��û��б�
				map.put(name, socket);
				System.out.println("��������" + map.size());
				// �����ͻ����߳�
				new Thread(new UserThread(socket, is, map,name,os)).start();

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

/*
 * ������ܵ���Ϣ
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
		while (true) { // ѭ�����ոÿͻ����׽��ֵ���Ϣ
			// ���������ݵ�����
			byte[] b = new byte[1024];
		
			try {
				// ��ȡ���ݲ����ض�ȡ�����ݳ���
		     		int len = is.read(b);
					// �����յ�����ϢתΪ�ַ���
					message = new String(b, 0, len);
					//
                    System.out.println(message);
			} catch (IOException e) {

				e.printStackTrace();
			}
				// �ж��������Ϣ������
				if (message.startsWith("@") && message.contains(":")) {// �ж��������Ϣ�Ƿ���@��ͷ�Ұ�����:"����
					// ��ȡ���������ŵ�һ�γ��ֵ��±�
					int index = message.indexOf(":");
					// ��ȡ������Ϣ�Ķ�������
					String rename = message.substring(1, index);
					//
					System.out.println(rename);
					//��ȡҪ���͵���Ϣ
					String mess=message.substring(index+1);
					//
					System.out.println(mess);
                    for(String aname:map.keySet()) {//�����ͻ����׽��ֵ�����
                    	System.out.println("111111111111111");
                    	System.out.println("rename---->"+rename);
                    	System.out.println("aname-------->"+aname);
                    	if(aname.equals(rename)) {//���������Ƚ���Ϣ�����ÿͻ����׽��ֶ���
                    		//��ȡ������Ϣ�Ŀͻ����׽��ֶ���
                    		Socket sesocket = map.get(aname);
                    		//
                    		System.out.println("22222222222222");
                    		System.out.println(sesocket.toString());
                    		//��ȡ������Ϣ���׽��ֶ���������
                    		OutputStream opre;
							try {
								opre = sesocket.getOutputStream();
								opre.write((name+"���ĸ���˵��"+mess).getBytes());
								opre.flush();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                    		
                    	}
                    	else {
                    		//֪ͨ�ͻ��������͵��û�������
                    		try {
								os.write((rename+"������").getBytes());
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
				
				else {//����Ϣ�������пͻ���
					 for(String rename:map.keySet()) {//�����ͻ����׽��ֵ�����
	                    
	                    		//��ȡ������Ϣ�Ŀͻ����׽��ֶ���
	                    		Socket sesocket = map.get(rename);
	                    		//��ȡ������Ϣ���׽��ֶ���������
	                    		OutputStream opre;
								try {
									opre = sesocket.getOutputStream();
									opre.write((name+"˵��"+message).getBytes());
									opre.flush();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	                    		//����Ϣ�������ն���
	                    		
	                          
					 }
					  }
				
                   
                   
			

		}
		
	}

}
