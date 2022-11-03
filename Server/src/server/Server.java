package server;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	private ServerSocket server;
	
	public Server(int port) throws IOException {

		this.server = new ServerSocket(port);
		System.out.println("Server runner on Port" + port);
		
		

		
	}
	
	private void startServer() {
		new Thread(() -> {
			while(true) {
				try {
					Socket socket = this.server.accept();
					System.out.println("Cient has connected");
					//new ClientHandler(socket).start();
					Thread thread = new Thread(new ClientHandler(socket));
					thread.start();
				} catch (IOException e) {
					closeServer();
				}

				
			}
		}).start();
	} 
	
	private void closeServer() {
		
			try {
				if(this.server != null) {
					this.server.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	


	public static void main(String[] args) {
		final int PORT = 3000;
		Server server = null ;

		try {
			server = new Server(PORT);
			server.startServer();
			
		}catch (Exception e) {
			if(server != null)server.closeServer();
		}
	}

}
