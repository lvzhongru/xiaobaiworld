package tcpdemo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
	try {
		Socket socket=new Socket("127.0.0.1",6767);
		OutputStream op = socket.getOutputStream();
		op.write("·þÎñÆ÷ÄãºÃ".getBytes());
		
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
