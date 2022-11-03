package server;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable{
	private Socket socket;
	private BufferedWriter bufferedWriter;
	private BufferedReader bufferedReader;
	private static List<ClientHandler> roomMembers = new ArrayList<>();
	private String userName;
	
	public ClientHandler(Socket socket) {
		
		try {
			this.socket = socket;
			this.bufferedWriter = new BufferedWriter(new PrintWriter(socket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.userName = bufferedReader.readLine();
			addUser();
			broadcastMessage(this.userName + " has entered the room");
		} catch (IOException e) {
			closeStreams();
		}
		
	}
	


	
	public void addUser() {
		roomMembers.add(this);
		System.out.println(roomMembers.size());
	}
	
	public void broadcastMembers(){
		String members = "";
		for(ClientHandler client : roomMembers) {
				members += client.userName + " ";
			
		}
		try {
			
				this.bufferedWriter.write("room members: "+members);
				this.bufferedWriter.newLine();
				this.bufferedWriter.flush();
			
		} catch (IOException e) {
			closeStreams();
		}
		
		
	}


	@Override
	public void run() {
		try {
			//BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// wrap the byte stream with a character stream
			//PrintWriter output = new PrintWriter(socket.getOutputStream());
			
			while(this.socket.isConnected()) {
				String msgFromClient = this.bufferedReader.readLine();

				
				if(msgFromClient.equals("exit")) {
					removeClientFromRoom();
					socket.close();
					break;
				}
				else if(msgFromClient.equals("ls")) {
					broadcastMembers();
				}
				else {
				
					this.broadcastMessage(msgFromClient);
				}
			}
			
		} catch (IOException e) {
			closeStreams();
		}
	}
	
	public void broadcastMessage(String msg){

			try {
				if(roomMembers.size() == 0) {
					throw new Exception();
				}
				for(ClientHandler client: roomMembers) {
				if(!client.userName.equals(this.userName)) {
					client.bufferedWriter.write(msg);
					client.bufferedWriter.newLine();
					client.bufferedWriter.flush();
				
				}}
			} catch (Exception e) {
				
				closeStreams();
			}
		
	}
	
	public void removeClientFromRoom() {	
		//String removedClient = this.userName;
		if(roomMembers.remove(this)) broadcastMessage(this.userName + " left the room");;
	}
	
	public void closeStreams() {
		removeClientFromRoom();
		
		try {
			if(bufferedWriter != null) bufferedWriter.close();
			if(bufferedReader != null) bufferedReader.close();
			if(socket != null) socket.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	

}

