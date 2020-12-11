package ui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientBackground extends Thread {
	private Socket socket;
	
	//메세지 전송을위한 데이터 스트림 
	private DataInputStream in;
	private DataOutputStream out;
		
	private MainIn gui;
	private String msg;
	
	// 닉네임 
	private String nickname;
	
	// Gui의 주소값을 가져오기위한 getter setter
	public void setGui(MainIn gui) {
		this.gui = gui;
	}

	// 2. 클라이언트 서버에 연결함
	public void connet() {
		try {
			socket = new Socket("127.0.0.1", 7777);
			System.out.println("서버연결됨");
			
			//메세지 전송
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			
			out.writeUTF(nickname); // 접속하자마자 닉네임 전송하면, 서버가 이걸 닉네임으로 인식하고 서 맵에 집어넣음
		
			// 새로운 사용자 쓰레드 클래스 생성해서 소켓정보를 넣어준다.
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
				// 여기서 에러남 서버가 보내준걸 gui에 보내줌
				while(in != null) {
					msg = in.readUTF();
					System.out.println(msg);
					gui.appendMsg(msg);
							}
				//사용자 종료
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
	
	// 3. 클라이언트에서 보낸 메세지를 서버로 전송
	public void sendMessage(String msg2) {
		try {
			out.writeUTF(msg2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 1. 닉네임 받아오는 메소드
	public void setNickname(String userNickName) {
		this.nickname = userNickName;
	}
}
