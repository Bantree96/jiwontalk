package ui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientBackground extends Thread {
	private Socket socket;
	
	//�޼��� ���������� ������ ��Ʈ�� 
	private DataInputStream in;
	private DataOutputStream out;
		
	private MainIn gui;
	private String msg;
	
	// �г��� 
	private String nickname;
	
	// Gui�� �ּҰ��� ������������ getter setter
	public void setGui(MainIn gui) {
		this.gui = gui;
	}

	// 2. Ŭ���̾�Ʈ ������ ������
	public void connet() {
		try {
			socket = new Socket("127.0.0.1", 7777);
			System.out.println("���������");
			
			//�޼��� ����
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			
			out.writeUTF(nickname); // �������ڸ��� �г��� �����ϸ�, ������ �̰� �г������� �ν��ϰ� �� �ʿ� �������
		
			// ���ο� ����� ������ Ŭ���� �����ؼ� ���������� �־��ش�.
			Receiver2 receiver2 = new Receiver2();
			receiver2.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	class Receiver2 extends Thread{
		@Override
		public void run() {
			try {
				// ���⼭ ������ ������ �����ذ� gui�� ������
				while(in != null) {
					msg = in.readUTF();
					System.out.println(msg);
					gui.appendMsg(msg);
							}
				//����� ����
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		ClientBackground clientBackground = new ClientBackground();
		clientBackground.connet();

		clientBackground.run();
	}
	
	// 3. Ŭ���̾�Ʈ���� ���� �޼����� ������ ����
	public void sendMessage(String msg2) {
		try {
			out.writeUTF(msg2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 1. �г��� �޾ƿ��� �޼ҵ�
	public void setNickname(String userNickName) {
		this.nickname = userNickName;
	}
}
