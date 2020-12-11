/**
 * ������ : ������
 * �α��� �� ����â
 * ���糯 : 2020.05.29
 */
package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
import javax.swing.JTextField;

import db.DB;
import ui.MainIn.HookThread;

public class MainState extends JFrame implements MouseListener, FocusListener {
	
	///////////////////////////////////////////////
	// ����� 
	private JPanel main;
	private JLabel logo, joinlbl, rePWlbl;
	
	private JTextField idField;
	private JPasswordField pwField;
	private JButton loginBtn;
	
	private Join JoinPanel;
	private RePW RePWPanel;
	
	private char passwordChar;
	// �ΰ� �̹��� ������
	private ImageIcon logoimg = new ImageIcon("img/logo4.png");
	
	//////////////////////
	// id�� getter setter
	private static String id;
	
	public static String getId() {
		return id;
	}

	public static void setId(String id) {
		MainState.id = id;
	}

	////////////////////////////////////////////////////////
	// ���� ���� 
	public MainState() {
		this.setTitle("������"); //this����� //title����
		this.setSize(360, 500);	//size����(width,height)
		setLocation(800, 300); //��ġ ����(x,y)
		
		//�������(���� EXIT_ON_CLOSE�� ���, ���3)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLayout(null);
		
		// ��� ����
		Color bgColor = new Color(204,234,187);
		
		//������� Pane�� ���̾ƿ� ���󺯰�
		Container con = getContentPane();
		con.setBackground(bgColor);
		
		// �ΰ�
		logo = new JLabel(logoimg);
		logo.setSize(70,70);
		logo.setLocation(135,80);
		
		// ID�Է�â
		idField = new JTextField(12);
		idField.setSize(200,25);
		idField.setLocation(70,200);
		idField.setText("���̵�");
		idField.addFocusListener(this);
		
		// PW�Է�â
		pwField = new JPasswordField(12);
		pwField.setSize(200,25);
		pwField.setLocation(70,230);
	
		passwordChar = pwField.getEchoChar();
		pwField.setEchoChar((char)0); // ���������� �Ⱥ��̰� ���ڷ� ���̰���
		pwField.setText("��й�ȣ"); 
		pwField.addFocusListener(this);
		
		// �α��� ��ư
		loginBtn = new JButton("�α���");
		loginBtn.setSize(200,25);
		loginBtn.setLocation(70,260);
		loginBtn.addMouseListener(this);
		
		// ȸ������
		joinlbl = new JLabel("ȸ������");
		joinlbl.setSize(50,25);
		joinlbl.setLocation(110,400);
		joinlbl.addMouseListener(this); // ȸ���������� �Ѿ�� ���� �����ʵ��
		
		// ��й�ȣ �缳��
		rePWlbl = new JLabel("��й�ȣ ã��");
		rePWlbl.setSize(80,25);
		rePWlbl.setLocation(170,400);
		rePWlbl.addMouseListener(this);
		
		// add 
		add(logo);
		add(idField);
		add(pwField);
		add(loginBtn);
		
		add(joinlbl);
		add(rePWlbl);
		
		//â�׸���
		this.setVisible(true);		
	}
	
	////////////////////////////////////////////////////
	// ���� 
	public static void main(String[] args) {
		db.DB.init(); // db����
		
		new MainState();
		Runtime.getRuntime().addShutdownHook(new HookThread());
	}
	
	///////////////////////////////////////////////////////
	//�α��� üũ
	private boolean checkIDPW(String id, String pw) {
		boolean check = false;
		
		String sql = "SELECT * FROM MEMBER WHERE ID = '" + id + "' and PW='"+ pw + "'";
		ResultSet rs = db.DB.getResultSet(sql);
		try {
			if(rs.next()) {
				check = true;
				db.DB.getResultSet("UPDATE MEMBER SET " + "LOGIN = '1'" +"WHERE ID='"+id+"'"); // �α��� ������ LOGIN = 1
			} else {
				check = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}

	
	// ���콺 Ŭ�������� 
	@Override
	public void mouseClicked(MouseEvent e) {
		Object obj = e.getSource();
		
		// ȸ������ Ŭ����
		if(obj == joinlbl) {
			dispose();
			JoinPanel = new Join("ȸ������");
		} else if(obj == rePWlbl) {
			dispose();
			RePWPanel = new RePW("��й�ȣ ã��");
		} else if(obj == idField && idField.equals("ID")) {
			idField.setText(" ");
			
		//�α��� ��ư Ŭ����
		} else if(obj == loginBtn) {
			setId(idField.getText());
			String pw = pwField.getText();
			
			boolean check = checkIDPW(id, pw);
			
			// �α��� ����
			if(check) {
				System.out.println("�α��� ����");
				this.dispose();
				new MainIn("������");
				return;
			
			// �α��� ����
			} else {
				JOptionPane.showMessageDialog(null, "�α��� ����");
				idField.setText("");
				pwField.setText("");
				idField.requestFocus();
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
	public void focusGained(FocusEvent e) {
		Object obj = e.getSource();
		if(obj == pwField) {
			pwField.setText("");
			pwField.setEchoChar(passwordChar);
		} else if(obj == idField) {	
			idField.setText("");
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}

