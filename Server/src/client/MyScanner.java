package client;

import java.io.IOException;
import java.util.Scanner;

public class MyScanner {
	private static MyScanner instance = null;
	private Scanner scanner = null;
	
	
	private MyScanner() throws IOException {
		this.scanner = new Scanner(System.in);
	}
	
	public static MyScanner createInstance() {
		if(instance == null) {
			try {
				instance = new MyScanner();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return instance;
	}
	
	public Scanner getScanner() {
		return this.scanner;
	}
}
