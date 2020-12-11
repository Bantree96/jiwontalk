/**
 * 만든이 : 남지원
 * 비밀번호 찾기
 * 만든날 : 2020.05.29
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
	
	private JLabel logo, loginlbl, joinlbl; // 하단 메뉴
	
	private JTextField idField,phoneField, pwField;
	private JButton checkBtn;
	
	// 다른 ui 가져옴
	private Join JoinPanel;
	private MainState main;
	
	// 로고 이미지 가져옴
	ImageIcon logoimg = new ImageIcon("img/logo4.png");
		
	public RePW(String title) {
		this.setTitle(title); //this없어도됨 //title설정
		this.setSize(360, 500); //size설정(width,height)
		setLocation(800, 300); //위치 설정(x,y)
		
		this.setLayout(null);
		
		Color bgColor = new Color(204,234,187);
		
		
		//가지고온 Pane에 레이아웃 색상변경
		Container con = getContentPane();
		con.setBackground(bgColor);
		
		// 로고
		logo = new JLabel(logoimg);
		logo.setSize(70,70);
		logo.setLocation(135,80);
	
		// ID입력창
		idField = new JTextField(12);
		idField.setSize(200,25);
		idField.setLocation(70,200);
		idField.setText("아이디를 입력해주세요");
		idField.addFocusListener(this);

		// 폰번호 입력창
		phoneField = new JTextField(12);
		phoneField.setSize(200,25);
		phoneField.setLocation(70,230);
		phoneField.setText("ex)010-0000-0000");
		phoneField.addFocusListener(this);
		
		// PW입력창
		pwField = new JTextField(12);
		pwField.setSize(200,25);
		pwField.setLocation(70,260);
		pwField.setText("이곳에 PW가 나옵니다");
		
		// 로그인 버튼
		checkBtn = new JButton("비밀번호 찾기");
		checkBtn.setSize(200,25);
		checkBtn.setLocation(70,290);
		checkBtn.addMouseListener(this);	
		
		// 로그인
		loginlbl = new JLabel("로그인");
		loginlbl.setSize(50,25);
		loginlbl.setLocation(110,400);
		loginlbl.setBackground(Color.BLACK);
		loginlbl.addMouseListener(this); // 회원가입으로 넘어가기 위한 리스너등록
				
		// 회원가입
		joinlbl = new JLabel("회원가입");
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
		//창그리기
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
			JoinPanel = new Join("회원가입");
		
		// checkBtn 눌럿을 때
		} else if(obj == checkBtn) {
			String phone = phoneField.getText();
			String id = idField.getText();
			
			// DB에서 값불러오기
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
							JOptionPane.showMessageDialog(null, "정보가 일치하지 않습니다.");
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
			idField.setText("아이디를 입력해주세요");
					
		} else if(obj == phoneField && phoneField.getText().isEmpty()) {
			phoneField.setText("ex)010-0000-0000");

		}
	}
		
}

