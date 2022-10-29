package server;



import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	private String msg;
	public Server(int port) throws Exception {

		ServerSocket server = new ServerSocket(port);
		System.out.println("Server runner on Port" + port);
		
		
		while(true) {
			Socket socket = server.accept();
			new SocketThread(socket).start();
			
		}
		
	}
	
	private void setMsg(String msg) {
		this.msg = msg;
	}
	


	public static void main(String[] args) {
		final int PORT = 3000;
		try {
			new Server(PORT);
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

}
