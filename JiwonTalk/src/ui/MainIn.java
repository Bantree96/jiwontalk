/**
 * ������ : ������
 * �α��� �� ���� â
 * ���糯 : 2020.06.05
 */
package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import db.DB;
import server.ServerBackground;
import ui.MainIn.HookThread;

public class MainIn extends JFrame implements MouseListener, ActionListener {
	
		
	
	//private static final Object nick = null;
	// ���� ����
	private static String userId;
	private static String userPw;
	private static String userPhone;
	private String userNickName;
		
	// ���� �г�
	private static JPanel basePanel,userPanel, chatPanel , myPanel;
	
	// ���� ����
	private static JToolBar toolBar;
		
	// ���� �̹���
	private static ImageIcon imgChat = new ImageIcon("img/toolBar/chat.png");
	private static ImageIcon imgUser = new ImageIcon("img/toolBar/user.png");
	private static ImageIcon imgSet = new ImageIcon("img/toolBar/gear2.png");
		
	//���ٿ� ���� ��
	private JLabel lblChat, lblUser, lblMy;
	
	// ���� �гο��� ���Ǵ°�
	private JTextArea jtaUser;
	// �α����� ID
	private String id = MainState.getId();
	
	// ä�� �гο��� ���Ǵ°�
	private static JTextArea ta;
	private static JTextField tf;
	private static JButton btn;
	private static JPanel panCenter, panBottom;
	
	// ���� �гο��� ���Ǵ°�
	private JLabel logo, lbl;
	
	private ImageIcon logoimg = new ImageIcon("img/logo4.png");
	
			
	private JTextField nickField, phoneField;
	private JPasswordField pwField, pwField2;
	private char passwordChar, passwordChar2;
	private JButton btnSubmit;
	
	// ��� ����
	private static Color bgColor = new Color(204,234,187);
			
	// ���� ����
	private static ClientBackground client = new ClientBackground();
	private ServerBackground server;
	
	public void ClientDB()  {
	////////////////////////////////////////////
	// ���� ���� DB���� ����
		try {
		String sql = "SELECT * FROM MEMBER WHERE ID = '" + id + "'";
		ResultSet rs = db.DB.getResultSet(sql);
		
		while(rs.next()) {
		userId = rs.getString("id");
		userPw = rs.getString("pw");
		userPhone = rs.getString("phone");
		userNickName = rs.getString("nickname");
		
		System.out.println(userId + "\t| " + userPw + "\t| " + userPhone + "\t| " + userNickName);
		}
		
		}  catch (SQLException e) { //�����ϴ� �߻��� �� �ִ� ����
		System.err.println("DB ���� ���� or ���� ���� �Դϴ�.");
		e.printStackTrace();
		} 
		
		
		
	}
	///////////////////////////////////////////////////////
	// ���������� 
	
	public MainIn(String title)  {
		
		// DB���� user�� ������
		ClientDB();
		
		setTitle(title); 
		setSize(360, 500);	// size����(width,height)
		setLocation(800, 300); // ��ġ ����(x,y)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// ������ ���̽� �г� ���� 
		basePanel = new JPanel();
		basePanel.setLayout(new CardLayout());
		
		// ���̽� �г��� center�� ����
		add(basePanel, BorderLayout.CENTER);
		
		this.makeChatPanel();
		this.makeUserPanel();
		this.makeMyPanel();
		
		// ������ ���� ����
		makeToolBar();
		add(toolBar,BorderLayout.WEST);
		
		this.setVisible(true); // â�׸���
		
		// ä�� ClientBackground ����
		client.setGui(this);
  		client.setNickname(userNickName);
  		client.connet();
	}
	
	// ������� �ľ� hook
	static class HookThread extends Thread {
		@Override
		public void run() {
			db.DB.getResultSet("UPDATE MEMBER SET " + "LOGIN = '0'" +"WHERE ID='"+userId+"'"); // �α��� ������ LOGIN = 1
		}
	}
	
	
	public static void main(String[] args) {
		new MainIn("������");
	}
	
	//////////////////////////////////////////////////////////////
	// ���� ����
	private void makeToolBar() {
		toolBar = new JToolBar();
		toolBar.setFloatable(false); // ���� ����
		
		toolBar.setOrientation(1); // ���� ���⼳��
		
		// ���� ��ư
		lblUser = new JLabel(imgUser);
		lblUser.addMouseListener(this);
				
		// ä�� ��ư
		lblChat = new JLabel(imgChat);
		lblChat.addMouseListener(this);
		
		// ���� ��ư
		lblMy = new JLabel(imgSet);
		lblMy.addMouseListener(this);
				
		// add
		toolBar.addSeparator(); //�� ����
		toolBar.add(lblUser);
		toolBar.addSeparator(); //�� ����
		toolBar.add(lblChat);
		toolBar.addSeparator(); //�� ����
		toolBar.add(lblMy);
	}
	
	////////////////////////////////////////////
	// ���� �г� ����
	private void makeUserPanel() {
		JLabel lblName, lbl1;
		
		userPanel = new JPanel();
		JPanel userPanelNorth = new JPanel();
		userPanel.setLayout(new BorderLayout());
		userPanelNorth.setBackground(bgColor);
		
		//lbluser = new JLabel(userId + "\t| " + userPw + "\t| " + userPhone + "\t| " + userNickName);
		
		//
		lblName = new JLabel("���̸� : " + userNickName);
		lbl1 = new JLabel("�������ڡ�");
		
		jtaUser = new JTextArea();
		jtaUser.setEditable(false); //����Ʈ ��������
		
		userPanelNorth.add(lblName,BorderLayout.NORTH);
		userPanelNorth.add(lbl1, BorderLayout.NORTH);
		userPanel.add(userPanelNorth, BorderLayout.NORTH);
		userPanel.add(jtaUser,BorderLayout.CENTER);
		basePanel.add(userPanel); // ���̽� �гο� �����г� add
	}
	
	//////////////////////////////////////////////////////
	// ä�� �г� ����
	private void makeChatPanel() {
		chatPanel = new JPanel();
		chatPanel.setLayout(new BorderLayout());
		
		// Center ä�� �ö���� ��
		panCenter = new JPanel();
		panCenter.setLayout(new BorderLayout());
		
		// textarea 
		ta = new JTextArea();
		ta.setEditable(false); //����Ʈ ��������
		
		// ��ũ�� �����
		JScrollPane sp = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panCenter.add(sp); //��ũ�ѹٸ� ���� ���� add ��
		
		
		//////////////////////////////////////////////
		// Bottom ä�� ġ�°�
		panBottom = new JPanel();
		panBottom.setBackground(Color.GRAY);
		
		tf = new JTextField(18);
		tf.addActionListener(this); //���͸��� ���������� �����ʵ��
		tf.requestFocus();
		
		btn = new JButton("�Է�");
		btn.addActionListener(this);
		
		panBottom.add(tf);
		panBottom.add(btn);
		
		// ä���г� ���̾ƿ� ����
		chatPanel.add(panCenter, BorderLayout.CENTER);
		chatPanel.add(panBottom, BorderLayout.SOUTH);
		
		basePanel.add(chatPanel);
		
	}
	
	// ���� �г� ����
	private void makeMyPanel() {
		myPanel = new JPanel();
		myPanel.setBackground(bgColor);
		
		myPanel.setLayout(null);
		
		// �ΰ�
		logo = new JLabel(logoimg);
		logo.setSize(70,70);
		logo.setLocation(105,80);
		
		// �г��� �Է�â
		nickField = new JTextField(12);
		nickField.setSize(200,25);
		nickField.setLocation(40,230);
		nickField.setText(userNickName);
		// phone�Է�â
		phoneField = new JTextField(12);
		phoneField.setSize(200,25);
		phoneField.setLocation(40,260);
		phoneField.setText(userPhone);
		
		// PW�Է�â
		pwField = new JPasswordField(12);
		pwField.setSize(200,25);
		pwField.setLocation(40,290);
		
		passwordChar = pwField.getEchoChar();
		pwField.setEchoChar((char)0); // ���������� �Ⱥ��̰� ���ڷ� ���̰���
		pwField.setText(userPw); 
		
		// PW�Է�â(�ߺ��˻�)
		pwField2 = new JPasswordField(12);
		pwField2.setSize(200,25);
		pwField2.setLocation(40,320);
		
		passwordChar2 = pwField.getEchoChar();
		pwField2.setEchoChar((char)0); // ���������� �Ⱥ��̰� ���ڷ� ���̰���
		pwField2.setText(userPw); 
		
		// ȸ������ ��ư
		btnSubmit = new JButton("����");
		btnSubmit.setSize(200,25);
		btnSubmit.setLocation(40,350);
		btnSubmit.addMouseListener(this);
		
		myPanel.add(logo);
		myPanel.add(nickField);
		myPanel.add(pwField);
		myPanel.add(pwField2);
		myPanel.add(phoneField);
		myPanel.add(btnSubmit);
		
		basePanel.add(myPanel); // ���̽� �гο� �����г� add
	}
	//////////////////////////////////////////
	// Ŭ���̾�Ʈ ��׶���� �޼��� ����
	public void appendMsg(String msg) {
		ta.append(msg);
	}
	
	private boolean updateM(String id, String pw, String phone, String nickname) {
		boolean check = false;
		String oldId = id;
		String sql = 
				"UPDATE MEMBER SET " +
				"PW = '"+pw+"', PHONE='"+phone+"', NICKNAME='"+nickname+"' " +
				"WHERE ID='"+id+"'";
		
		ResultSet rs = DB.getResultSet(sql);
		try {
			if(rs.next()) {
				JOptionPane.showMessageDialog(null, "���� �Ǿ����ϴ�.");
				ClientDB(); // �г��� ���� �� DB �ٽ� ������
				makeUserPanel();
				check = true;
			} else {
				System.out.println("����");
				check = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	   Object obj = e.getSource();
      
      // �г� ����
      if(obj == lblUser) {         
         basePanel.remove(chatPanel);
         basePanel.remove(myPanel);    
         basePanel.add(userPanel);
         
         String sql = "SELECT * FROM MEMBER WHERE LOGIN = '1'";
 		 ResultSet rs = db.DB.getResultSet(sql);
 		
		 try {
			jtaUser.setText("");
			while(rs.next()) {
				String name = rs.getString("nickname");
				
				jtaUser.append(name+"\n");
			}
		 } catch (SQLException e1) {
			e1.printStackTrace();
		 }
 		
         basePanel.validate();
      } else if(obj == lblChat) {
         basePanel.remove(userPanel);   
         basePanel.remove(myPanel);    
         basePanel.add(chatPanel);
         basePanel.validate();    
      }	else if(obj == lblMy) {
         basePanel.remove(userPanel); 
         basePanel.remove(chatPanel);    
         basePanel.add(myPanel);
         basePanel.validate();    
       }
     
      if(obj == btnSubmit) {
    	// ���������� �ؽ�Ʈ�� ������
		String id = userId;
		String pw = pwField.getText();
		String pw2 = pwField2.getText();
		String phone = phoneField.getText();
		String nickname = nickField.getText();
		
		
		// ��й�ȣ Ȯ���۾�
		if(!pw.equals(pw2)) {
			JOptionPane.showMessageDialog(null, "��й�ȣ�� �ٸ��ϴ�.");
			pwField.setText("");
			pwField2.setText("");
			pwField.requestFocus();
			
		// ���̵� ������� Ȯ��	�ð����� StringUtils �Ẹ��
		} else if(nickname.isEmpty()) {
			JOptionPane.showMessageDialog(null, "�г����� �ʼ������Դϴ�.");
			nickField.requestFocus();
		
		} else {
			
			// �������� �� ����
			boolean check = updateM(id, pw, phone,nickname);
			if(check) {
				System.out.println("���� ����");
				
			}
		}
      }
   }

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		//���� ��ư�̳� enter��� ����
		if(obj == btn || obj == tf) {
			String msg = tf.getText();
			//���ڿ� �񱳿��� equals 
			if(!msg.equals("")) {
				msg = tf.getText()+"\n";
				client.sendMessage(userNickName+" : "+msg);
				tf.setText(""); //�Է��ϸ� �������ʾƵ� ��
				
			}
		}
	}

	
		
}

