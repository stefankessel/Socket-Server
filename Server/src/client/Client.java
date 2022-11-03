package client;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client {
	private BufferedReader reader;
	private BufferedWriter writer;
	private Socket socket;
	private String username;
	private Scanner scanner;
	
	public Client(int port) throws Exception {
		try {
			MyScanner myscanner = MyScanner.createInstance();
			this.scanner = myscanner.getScanner();
			socket = new Socket("localhost", port);
			this.username = getUsername();
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new BufferedWriter(new PrintWriter(socket.getOutputStream()));		

		}
		catch(Exception e) {
			closeStreams();
		}

	}
	public void sendMessage() throws InterruptedException {
		try {
			writer.write(username);
			writer.newLine();
			writer.flush();
			
			String msgInput;
			//Thread.sleep(50);
			//System.out.println("Hi " + username+ "! Lets chat!");

			 
			
			while(this.socket.isConnected()){
			
				if(scanner.hasNext()) {
					msgInput = scanner.nextLine();
					
					
					switch(msgInput) {
					case "exit": 
						writer.write(msgInput);
						writer.newLine();
						writer.flush();
						socket.close();
						break;
					
					case "ls": 
						writer.write(msgInput);
						writer.newLine();
						writer.flush();
						break;
					
					default:
						writer.write(username + ": " + msgInput);
						writer.newLine();
						writer.flush();
						
					
					}
				}

				
				
			}
		} catch (IOException e) {
			closeStreams();
		}


	}
	
	public void listenForMessage() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(socket.isConnected()) {
					try {
						String msg = reader.readLine();
						System.out.println(msg);
					} catch (IOException e) {
						closeStreams();
					}
				}
				
			}
		}).start();
	}
	
	public String getUsername() {
	
		System.out.println("Enter Your Username");
		String username = scanner.nextLine();
		return username;
	}
	
	public void closeStreams() {
	
		
		try {
			if(writer != null) writer.close();
			if(reader != null) reader.close();
			if(socket != null) socket.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			Client client = new Client(3000);
			client.listenForMessage();
			client.sendMessage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
}
