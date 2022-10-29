package client;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client {
	
	public Client(int port) throws Exception {
		Socket socket = new Socket("localhost", port);
		
		BufferedReader msgFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter msgToServer = new PrintWriter(socket.getOutputStream(), true);
		
		Scanner scanner = new Scanner(System.in);
		String msgInput;
		
		do {
			System.out.println("Enter msg to Server");
			msgInput = scanner.nextLine();
			
			msgToServer.println(msgInput);
			
			if(!msgInput.equals("exit")) {
				System.out.println("From Server: " + msgFromServer.readLine());
			}
			
			
		}while(!msgInput.equals("exit"));
	}
	
	public static void main(String[] args) {
		try {
			new Client(3000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
