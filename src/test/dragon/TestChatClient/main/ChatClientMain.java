package test.dragon.TestChatClient.main;

import java.awt.EventQueue;

import test.dragon.TestChatClient.view.ChatClientView;

public class ChatClientMain {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatClientView frame = new ChatClientView();
					frame.setVisible(true);
					
					ChatManager.getInstance().setTheView(frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
