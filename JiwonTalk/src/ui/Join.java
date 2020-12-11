/**
 * 만든이 : 남지원
 * 회원가입 창
 * 만든날 : 2020.06.05
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
	
	private JLabel logo, loginlbl, rePWlbl; // 하단 메뉴 및 로고
	
	private JTextField idField,phoneField, nickField;
	private JPasswordField pwField, pwField2;
	private JButton joinBtn;
	
	private char passwordChar, passwordChar2;
	
	// 다른 ui 가져옴
	private MainState main;
	private RePW RePWPanel;
	
	// 로고 이미지 가져옴
	ImageIcon logoimg = new ImageIcon("img/logo4.png");
		
	public Join(String title) {
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
		
		// PW입력창
		pwField = new JPasswordField(12);
		pwField.setSize(200,25);
		pwField.setLocation(70,230);
		
		passwordChar = pwField.getEchoChar();
		pwField.setEchoChar((char)0); // 점점점으로 안보이고 문자로 보이게함
		pwField.setText("비밀번호를 입력해주세요"); 
		pwField.addFocusListener(this);
		
		// PW입력창(중복검사)
		pwField2 = new JPasswordField(12);
		pwField2.setSize(200,25);
		pwField2.setLocation(70,260);
		
		passwordChar2 = pwField.getEchoChar();
		pwField2.setEchoChar((char)0); // 점점점으로 안보이고 문자로 보이게함
		pwField2.setText("비밀번호를 다시 입력해주세요"); 
		pwField2.addFocusListener(this);
				
		// 닉네임 입력창
		nickField = new JTextField(12);
		nickField.setSize(200,25);
		nickField.setLocation(70,290);
		nickField.setText("닉네임을 입력해주세요");
		nickField.addFocusListener(this);
		
		// phone입력창
		phoneField = new JTextField(12);
		phoneField.setSize(200,25);
		phoneField.setLocation(70,320);
		phoneField.setText("ex)010-0000-0000");
		phoneField.addFocusListener(this);
		
		// 회원가입 버튼
		joinBtn = new JButton("회원가입");
		joinBtn.setSize(200,25);
		joinBtn.setLocation(70,350);
		joinBtn.addMouseListener(this);
		
		// 로그인
		loginlbl = new JLabel("로그인");
		loginlbl.setSize(100,25);
		loginlbl.setLocation(100,400);
		loginlbl.addMouseListener(this); // 회원가입으로 넘어가기 위한 리스너등록
	
		// 비밀번호 재설정
		rePWlbl = new JLabel("비밀번호 찾기");
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
		
		//창그리기
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
				JOptionPane.showMessageDialog(null, "회원가입 되었습니다.");
				check = true;
			} else {
				System.out.println("실패");
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
		// 다른 창 전환
		if(obj == loginlbl) {
			dispose();
			main = new MainState();
		} else if(obj == rePWlbl) {
			dispose();
			RePWPanel = new RePW("비밀번호 찾기");
			
		// 회원가입버튼 눌렀을 때
		} else if(obj == joinBtn) {
			
			// 지역변수로 텍스트값 가져옴
			String id = idField.getText();
			String pw = pwField.getText();
			String pw2 = pwField2.getText();
			String phone = phoneField.getText();
			String nickname = nickField.getText();
			
			// db에서 값 불러오기
			ResultSet rs = null;
			try {
				rs = DB.stmt.executeQuery("SELECT * FROM MEMBER");
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			
			// Aes암호화 하면 좋다
			// 비밀번호 확인작업
			if(!pw.equals(pw2)) {
				JOptionPane.showMessageDialog(null, "비밀번호가 다릅니다.");
				pwField.setText("");
				pwField2.setText("");
				pwField.requestFocus();
				
			
			} else
				// 아이디 중복 확인
				try {
					while(rs.next()) {
						String dbid = rs.getString("id");
						if(dbid.equals(id)) {
							JOptionPane.showMessageDialog(null, "아이디가 중복됩니다.");
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
			
				// 정상적일 때 회원가입
				boolean check = joinM(id, pw, phone,nickname);
				if(check) {
					System.out.println("회원가입 성공");
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

	// 입력하기 위해 포커스 in했을때
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

	// 아무것도 입력하지 않고 포커스 out됐을떄
	@Override
	public void focusLost(FocusEvent e) {
		Object obj = e.getSource();
		if(obj == idField && idField.getText().isEmpty()) {
			idField.setText("아이디를 입력해주세요");
			
		} else if(obj == pwField && pwField.getText().isEmpty()) {	
			passwordChar = pwField.getEchoChar();
			pwField.setEchoChar((char)0); // 점점점으로 안보이고 문자로 보이게함
			pwField.setText("비밀번호를 입력해주세요"); 
			
		} else if(obj == pwField2 && pwField2.getText().isEmpty()) {
			passwordChar2 = pwField2.getEchoChar();
			pwField2.setEchoChar((char)0); // 점점점으로 안보이고 문자로 보이게함
			pwField2.setText("비밀번호를 다시 입력해주세요"); 
			
		} else if(obj == nickField && nickField.getText().isEmpty()) {
			nickField.setText("닉네임을 입력해주세요");

		} else if(obj == phoneField && phoneField.getText().isEmpty()) {
			phoneField.setText("ex)010-0000-0000");

		}
	}
}

