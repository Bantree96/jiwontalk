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
	
	// ���� ����
	private ServerBackground server = new ServerBackground();
	
	public ServerGui() {
		add(jta, BorderLayout.CENTER);
		add(jtf, BorderLayout.SOUTH);
		jtf.addActionListener(this);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(200,100,400,600);
		setTitle("�����κ�");
		
		// ���� GUI �ּҳѰ���
		//server.setGui(this);
		// ���� ����
		server.setting();
	}
	
	// ���� ����
	public static void main(String[] args) {
		new ServerGui();
	}
	
	// ���� GUI�� append�޼���
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
