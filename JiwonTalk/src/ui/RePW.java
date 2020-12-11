/**
 * ������ : ������
 * ��й�ȣ ã��
 * ���糯 : 2020.05.29
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


public class RePW extends JFrame implements MouseListener, FocusListener {
	
	private JLabel logo, loginlbl, joinlbl; // �ϴ� �޴�
	
	private JTextField idField,phoneField, pwField;
	private JButton checkBtn;
	
	// �ٸ� ui ������
	private Join JoinPanel;
	private MainState main;
	
	// �ΰ� �̹��� ������
	ImageIcon logoimg = new ImageIcon("img/logo4.png");
		
	public RePW(String title) {
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

		// ����ȣ �Է�â
		phoneField = new JTextField(12);
		phoneField.setSize(200,25);
		phoneField.setLocation(70,230);
		phoneField.setText("ex)010-0000-0000");
		phoneField.addFocusListener(this);
		
		// PW�Է�â
		pwField = new JTextField(12);
		pwField.setSize(200,25);
		pwField.setLocation(70,260);
		pwField.setText("�̰��� PW�� ���ɴϴ�");
		
		// �α��� ��ư
		checkBtn = new JButton("��й�ȣ ã��");
		checkBtn.setSize(200,25);
		checkBtn.setLocation(70,290);
		checkBtn.addMouseListener(this);	
		
		// �α���
		loginlbl = new JLabel("�α���");
		loginlbl.setSize(50,25);
		loginlbl.setLocation(110,400);
		loginlbl.setBackground(Color.BLACK);
		loginlbl.addMouseListener(this); // ȸ���������� �Ѿ�� ���� �����ʵ��
				
		// ȸ������
		joinlbl = new JLabel("ȸ������");
		joinlbl.setSize(50,25);
		joinlbl.setLocation(180,400);
		joinlbl.addMouseListener(this);
				
		con.add(logo);
		con.add(idField);
		con.add(phoneField);
		con.add(pwField);
		con.add(checkBtn);
		
		con.add(loginlbl);
		con.add(joinlbl);
		//â�׸���
		this.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object obj = e.getSource();
		if(obj == loginlbl) {
			dispose();
			main = new MainState();
		} else if(obj == joinlbl) {
			dispose();
			JoinPanel = new Join("ȸ������");
		
		// checkBtn ������ ��
		} else if(obj == checkBtn) {
			String phone = phoneField.getText();
			String id = idField.getText();
			
			// DB���� ���ҷ�����
			ResultSet rs = null;
			try {
				rs = DB.stmt.executeQuery("SELECT * FROM MEMBER where id = '" + id +"'");
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			
				try {
					while(rs.next()) {
						String dbId = rs.getString("id");
						String dbPhone = rs.getString("phone");
						String dbPw = rs.getString("pw");
						
						if(dbId.equals(id) && dbPhone.equals(phone)) {
							pwField.setText(dbPw);
							break;
						} else {
							JOptionPane.showMessageDialog(null, "������ ��ġ���� �ʽ��ϴ�.");
						}
					}
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
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
		if(obj == idField) {
			idField.setText("");
		} else if(obj == phoneField) {
			phoneField.setText("");
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		Object obj = e.getSource();
		if(obj == idField && idField.getText().isEmpty()) {
			idField.setText("���̵� �Է����ּ���");
					
		} else if(obj == phoneField && phoneField.getText().isEmpty()) {
			phoneField.setText("ex)010-0000-0000");

		}
	}
		
}

