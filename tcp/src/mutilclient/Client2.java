package mutilclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client2 {

	public static void main(String[] args) {
	try {
		Socket socket=new Socket("127.0.0.1",6868);
		OutputStream op = socket.getOutputStream();
		op.write("服务器你好不好".getBytes());
		
		InputStream is = socket.getInputStream();
		byte[] b=new byte[1024];
		int len=is.read(b);
		System.out.println(new String(b,0,len));
		socket.close();
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	}

}
