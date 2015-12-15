package test.dragon.TestChatClient.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import test.dragon.TestChatClient.view.ChatClientView;

public class ChatManager {
	private static ChatManager instance = new ChatManager();
	public static ChatManager getInstance() {
		return instance;
	}

	Socket mySocket = null;
	BufferedReader reader = null;
	PrintWriter writer = null;
	String line = null;
	
	ChatClientView theView = null;
	
	public void  setTheView(ChatClientView newView) {
		theView = newView;
	}

	public void connectMethod(String newIP) {
		System.out.println("Manager connectMethod");
		new Thread() {
			@Override
			public void run() {
				try {
					mySocket = new Socket(newIP, 12345);
					
					System.out.println("Manager after new Socket");

					reader = new BufferedReader(
							new InputStreamReader(
									mySocket.getInputStream(), "UTF-8"));

					writer = new PrintWriter(
							new OutputStreamWriter(
									mySocket.getOutputStream(), "UTF-8"));
					
					while ((line = reader.readLine()) != null) {
						System.out.println("Manager get new line");
						//输出到屏幕
						if (theView != null) {
							theView.appendContent("Other say:" + line);
						}
					}
					writer.close();
					reader.close();
					mySocket.close();
					
					line = null;
					writer = null;
					reader = null;
					mySocket = null;
					
					System.out.println("end connected");

				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}.start();
		
//		System.out.println("end connected");
		if (theView != null) {
			theView.appendContent("connect ended");
		}
	}
	
	public void  sendMethod(String newMessage) {
		System.out.println("Manager sendMethod");
		if (writer != null) {
			writer.write(newMessage + "\n");
			writer.flush();
			theView.appendContent("I say:" + newMessage);
		}else {
			theView.appendContent("lost connected");
		}
	}

}
