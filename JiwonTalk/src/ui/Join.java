/**
 * ������ : ������
 * ȸ������ â
 * ���糯 : 2020.06.05
 */
package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.HeadlessException;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import db.DB;

public class Join extends JFrame implements MouseListener, FocusListener {
	
	private JLabel logo, loginlbl, rePWlbl; // �ϴ� �޴� �� �ΰ�
	
	private JTextField idField,phoneField, nickField;
	private JPasswordField pwField, pwField2;
	private JButton joinBtn;
	
	private char passwordChar, passwordChar2;
	
	// �ٸ� ui ������
	private MainState main;
	private RePW RePWPanel;
	
	// �ΰ� �̹��� ������
	ImageIcon logoimg = new ImageIcon("img/logo4.png");
		
	public Join(String title) {
		this.setTitle(title); //this����� //title����
		this.setSize(360, 500); //size����(width,height)
		setLocation(800, 300); //��ġ ����(x,y)
		
		this.setLayout(null);
		
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
		idField.setText("���̵� �Է����ּ���");
		idField.addFocusListener(this);
		
		// PW�Է�â
		pwField = new JPasswordField(12);
		pwField.setSize(200,25);
		pwField.setLocation(70,230);
		
		passwordChar = pwField.getEchoChar();
		pwField.setEchoChar((char)0); // ���������� �Ⱥ��̰� ���ڷ� ���̰���
		pwField.setText("��й�ȣ�� �Է����ּ���"); 
		pwField.addFocusListener(this);
		
		// PW�Է�â(�ߺ��˻�)
		pwField2 = new JPasswordField(12);
		pwField2.setSize(200,25);
		pwField2.setLocation(70,260);
		
		passwordChar2 = pwField.getEchoChar();
		pwField2.setEchoChar((char)0); // ���������� �Ⱥ��̰� ���ڷ� ���̰���
		pwField2.setText("��й�ȣ�� �ٽ� �Է����ּ���"); 
		pwField2.addFocusListener(this);
				
		// �г��� �Է�â
		nickField = new JTextField(12);
		nickField.setSize(200,25);
		nickField.setLocation(70,290);
		nickField.setText("�г����� �Է����ּ���");
		nickField.addFocusListener(this);
		
		// phone�Է�â
		phoneField = new JTextField(12);
		phoneField.setSize(200,25);
		phoneField.setLocation(70,320);
		phoneField.setText("ex)010-0000-0000");
		phoneField.addFocusListener(this);
		
		// ȸ������ ��ư
		joinBtn = new JButton("ȸ������");
		joinBtn.setSize(200,25);
		joinBtn.setLocation(70,350);
		joinBtn.addMouseListener(this);
		
		// �α���
		loginlbl = new JLabel("�α���");
		loginlbl.setSize(100,25);
		loginlbl.setLocation(100,400);
		loginlbl.addMouseListener(this); // ȸ���������� �Ѿ�� ���� �����ʵ��
	
		// ��й�ȣ �缳��
		rePWlbl = new JLabel("��й�ȣ ã��");
		rePWlbl.setSize(200,25);
		rePWlbl.setLocation(160,400);
		rePWlbl.addMouseListener(this);
		
		con.add(logo);
		con.add(idField);
		con.add(pwField);
		con.add(pwField2);
		con.add(nickField);
		con.add(phoneField);
		con.add(joinBtn);
		
		con.add(loginlbl);
		con.add(rePWlbl);
		
		//â�׸���
		this.setVisible(true);
	}
	
	private boolean joinM(String id, String pw, String phone, String nickname) {
		boolean check = false;
		
		String sql = 
				"INSERT INTO MEMBER " + 
				"(ID, PW, PHONE, NICKNAME, LOGIN) " + 
				"VALUES('"+ id +"', '"+ pw +"', '" + phone + "', '"+ nickname+ "','0')";
		
		ResultSet rs = DB.getResultSet(sql);
		try {
			if(rs.next()) {
				JOptionPane.showMessageDialog(null, "ȸ������ �Ǿ����ϴ�.");
				check = true;
			} else {
				System.out.println("����");
				check = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return check;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Object obj = e.getSource();
		// �ٸ� â ��ȯ
		if(obj == loginlbl) {
			dispose();
			main = new MainState();
		} else if(obj == rePWlbl) {
			dispose();
			RePWPanel = new RePW("��й�ȣ ã��");
			
		// ȸ�����Թ�ư ������ ��
		} else if(obj == joinBtn) {
			
			// ���������� �ؽ�Ʈ�� ������
			String id = idField.getText();
			String pw = pwField.getText();
			String pw2 = pwField2.getText();
			String phone = phoneField.getText();
			String nickname = nickField.getText();
			
			// db���� �� �ҷ�����
			ResultSet rs = null;
			try {
				rs = DB.stmt.executeQuery("SELECT * FROM MEMBER");
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			
			// Aes��ȣȭ �ϸ� ����
			// ��й�ȣ Ȯ���۾�
			if(!pw.equals(pw2)) {
				JOptionPane.showMessageDialog(null, "��й�ȣ�� �ٸ��ϴ�.");
				pwField.setText("");
				pwField2.setText("");
				pwField.requestFocus();
				
			
			} else
				// ���̵� �ߺ� Ȯ��
				try {
					while(rs.next()) {
						String dbid = rs.getString("id");
						if(dbid.equals(id)) {
							JOptionPane.showMessageDialog(null, "���̵� �ߺ��˴ϴ�.");
							idField.setText("");
							idField.requestFocus();		
							break;
						}
					}
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
			
				// �������� �� ȸ������
				boolean check = joinM(id, pw, phone,nickname);
				if(check) {
					System.out.println("ȸ������ ����");
					this.dispose();
					new MainState();
				}
			}
				
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
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

	// �Է��ϱ� ���� ��Ŀ�� in������
	@Override
	public void focusGained(FocusEvent e) {
		Object obj = e.getSource();
		if(obj == idField) {
			idField.setText("");
		} else if(obj == pwField) {	
			pwField.setText("");
			pwField.setEchoChar(passwordChar);
		} else if(obj == pwField2) {
			pwField2.setText("");
			pwField2.setEchoChar(passwordChar);
		} else if(obj == nickField) {
			nickField.setText("");
		} else if(obj == phoneField) {
			phoneField.setText("");
		}
	}

	// �ƹ��͵� �Է����� �ʰ� ��Ŀ�� out������
	@Override
	public void focusLost(FocusEvent e) {
		Object obj = e.getSource();
		if(obj == idField && idField.getText().isEmpty()) {
			idField.setText("���̵� �Է����ּ���");
			
		} else if(obj == pwField && pwField.getText().isEmpty()) {	
			passwordChar = pwField.getEchoChar();
			pwField.setEchoChar((char)0); // ���������� �Ⱥ��̰� ���ڷ� ���̰���
			pwField.setText("��й�ȣ�� �Է����ּ���"); 
			
		} else if(obj == pwField2 && pwField2.getText().isEmpty()) {
			passwordChar2 = pwField2.getEchoChar();
			pwField2.setEchoChar((char)0); // ���������� �Ⱥ��̰� ���ڷ� ���̰���
			pwField2.setText("��й�ȣ�� �ٽ� �Է����ּ���"); 
			
		} else if(obj == nickField && nickField.getText().isEmpty()) {
			nickField.setText("�г����� �Է����ּ���");

		} else if(obj == phoneField && phoneField.getText().isEmpty()) {
			phoneField.setText("ex)010-0000-0000");

		}
	}
}

