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

	// Client 접속을 받기 위한 serverSocket
	private ServerSocket serverSocket = null;
	private Socket socket;
	
	// 사용자 정보 저장하는 맵
	public Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();
	
	// 메세지 내용 넣는 변수
	private String msg;
	
	// Gui의 주소값을 가져오기위한 getter setter
	private ServerGui gui;
		
	public static String nick;
	
	public void setting() {
		try {
			Collections.synchronizedMap(clientsMap); // 교통정리
			serverSocket = new ServerSocket(7777);
			
			// 먼저 서버가 할일은 계속해서 사용자를 받는다
			while(true) {
				System.out.println("대기중...");
				socket = serverSocket.accept(); 
				System.out.println(socket.getInetAddress()+"에서 접속했습니다.");
				// 새로운 사용자 쓰레드 클래스 생성해서 소켓정보를 넣어준다.
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


	// 맵의 내용/(클라이언트) 저장과 삭제
	public void addClient(String nick, DataOutputStream out) {
		clientsMap.put(nick, out);
		
		sendMessage(nick + "님이 접속하셨습니다.\n");
	}
	
	public void removeClient(String nick) {
		sendMessage(nick + "님이 나가셨습니다");
		clientsMap.remove(nick);
	}
	
	//////////////////////////////////////////////////////////
	// 클라이언트에 메세지 보내기
	public void sendMessage(String msg) {
		// 반복자 사용
		// 어떤 리스트같은거에서 하나씩 뽑아내서 처리함 
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
	// 리시버 네트워크 처리 계속 듣기, 처리해주는 것
	class Receiver extends Thread{
		//메세지 전송을위한 데이터 스트림 
		private DataInputStream in;
		private DataOutputStream out;
		private String nick;
		
		// 리시버가 할 일은 네트워크 소켓을 받아서 계속 듣고, 요청하는 일
		public Receiver(Socket socket) {
			//메세지 입출력
			try {
				out = new DataOutputStream(socket.getOutputStream());
				in = new DataInputStream(socket.getInputStream());
				
				// 리시버가 처음에는 클라이언트 닉네임을 받아오고 싶음
				nick = in.readUTF();
				addClient(nick, out);
				
			} catch (IOException e) {
			}
			
		}
		
		@Override
		public void run() {
			try {
				//계속 듣기만한다
				while(in != null) {
					msg = in.readUTF(); // 클라이언트에서 온 메세지 읽어옴
					sendMessage(msg); // 클라이언트에 메세지 보냄
				}
				//사용자 종료
			} catch (IOException e) {
				removeClient(nick);
			}
		}
	}

}
