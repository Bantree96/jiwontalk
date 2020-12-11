package server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerGui extends JFrame implements ActionListener {
	
	private JTextArea jta = new JTextArea(40, 25);
	private JTextField jtf = new JTextField(25);
	
	// 서버 연동
	private ServerBackground server = new ServerBackground();
	
	public ServerGui() {
		add(jta, BorderLayout.CENTER);
		add(jtf, BorderLayout.SOUTH);
		jtf.addActionListener(this);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(200,100,400,600);
		setTitle("서버부분");
		
		// 서버 GUI 주소넘겨줌
		//server.setGui(this);
		// 서버 연결
		server.setting();
	}
	
	// 메인 시작
	public static void main(String[] args) {
		new ServerGui();
	}
	
	// 서버 GUI에 append메세지
	public void appendMsg(String msg) {
		jta.append(msg);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String msg = jtf.getText()+"\n";
		System.out.println(msg);
		jtf.setText("");
	}

	
}
