package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ServerBackground {

	// Client ������ �ޱ� ���� serverSocket
	private ServerSocket serverSocket = null;
	private Socket socket;
	
	// ����� ���� �����ϴ� ��
	public Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();
	
	// �޼��� ���� �ִ� ����
	private String msg;
	
	// Gui�� �ּҰ��� ������������ getter setter
	private ServerGui gui;
		
	public static String nick;
	
	public void setting() {
		try {
			Collections.synchronizedMap(clientsMap); // ��������
			serverSocket = new ServerSocket(7777);
			
			// ���� ������ ������ ����ؼ� ����ڸ� �޴´�
			while(true) {
				System.out.println("�����...");
				socket = serverSocket.accept(); 
				System.out.println(socket.getInetAddress()+"���� �����߽��ϴ�.");
				// ���ο� ����� ������ Ŭ���� �����ؼ� ���������� �־��ش�.
				Receiver receiver = new Receiver(socket);
				receiver.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ServerBackground serverBackground = new ServerBackground();
		serverBackground.setting();
	}


	// ���� ����/(Ŭ���̾�Ʈ) ����� ����
	public void addClient(String nick, DataOutputStream out) {
		clientsMap.put(nick, out);
		
		sendMessage(nick + "���� �����ϼ̽��ϴ�.\n");
	}
	
	public void removeClient(String nick) {
		sendMessage(nick + "���� �����̽��ϴ�");
		clientsMap.remove(nick);
	}
	
	//////////////////////////////////////////////////////////
	// Ŭ���̾�Ʈ�� �޼��� ������
	public void sendMessage(String msg) {
		// �ݺ��� ���
		// � ����Ʈ�����ſ��� �ϳ��� �̾Ƴ��� ó���� 
		Iterator<String> it = clientsMap.keySet().iterator();
		String key = "";
		
		while(it.hasNext()) {
			try {
				key = it.next();
				clientsMap.get(key).writeUTF(msg);
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}
		
	//------------------------------------------------------
	// ���ù� ��Ʈ��ũ ó�� ��� ���, ó�����ִ� ��
	class Receiver extends Thread{
		//�޼��� ���������� ������ ��Ʈ�� 
		private DataInputStream in;
		private DataOutputStream out;
		private String nick;
		
		// ���ù��� �� ���� ��Ʈ��ũ ������ �޾Ƽ� ��� ���, ��û�ϴ� ��
		public Receiver(Socket socket) {
			//�޼��� �����
			try {
				out = new DataOutputStream(socket.getOutputStream());
				in = new DataInputStream(socket.getInputStream());
				
				// ���ù��� ó������ Ŭ���̾�Ʈ �г����� �޾ƿ��� ����
				nick = in.readUTF();
				addClient(nick, out);
				
			} catch (IOException e) {
			}
			
		}
		
		@Override
		public void run() {
			try {
				//��� ��⸸�Ѵ�
				while(in != null) {
					msg = in.readUTF(); // Ŭ���̾�Ʈ���� �� �޼��� �о��
					sendMessage(msg); // Ŭ���̾�Ʈ�� �޼��� ����
				}
				//����� ����
			} catch (IOException e) {
				removeClient(nick);
			}
		}
	}

}
