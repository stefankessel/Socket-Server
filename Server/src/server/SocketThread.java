package server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;

public class SocketThread extends Thread{
	private Socket socket;
	String msgToServer;
	
	public SocketThread(Socket socket) {
		this.socket = socket;
	}
	
	

	public String getMsgToServer() {
		return msgToServer;
	}


	public void setMsgToServer(String msgToServer) {
		this.msgToServer = msgToServer;
	}


	@Override
	public void run() {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter output = new PrintWriter(socket.getOutputStream());
			
			while(true) {
				this.setMsgToServer(input.readLine());
				System.out.println(this.getMsgToServer());
				
				
				if(this.getMsgToServer().equals("exit")) {
					break;
				}
				
				output.println(this.getMsgToServer() + " " + LocalDate.now());
				output.flush();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				socket.close();
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	

}

