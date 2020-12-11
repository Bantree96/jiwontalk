/**
 * 만든이 : 남지원
 * 로그인 전 메인창
 * 만든날 : 2020.05.29
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
	// 선언부 
	private JPanel main;
	private JLabel logo, joinlbl, rePWlbl;
	
	private JTextField idField;
	private JPasswordField pwField;
	private JButton loginBtn;
	
	private Join JoinPanel;
	private RePW RePWPanel;
	
	private char passwordChar;
	// 로고 이미지 가져옴
	private ImageIcon logoimg = new ImageIcon("img/logo4.png");
	
	//////////////////////
	// id값 getter setter
	private static String id;
	
	public static String getId() {
		return id;
	}

	public static void setId(String id) {
		MainState.id = id;
	}

	////////////////////////////////////////////////////////
	// 메인 내용 
	public MainState() {
		this.setTitle("지원톡"); //this없어도됨 //title설정
		this.setSize(360, 500);	//size설정(width,height)
		setLocation(800, 300); //위치 설정(x,y)
		
		//종료관리(보통 EXIT_ON_CLOSE를 사용, 상수3)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLayout(null);
		
		// 배경 색상
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
		idField.setText("아이디");
		idField.addFocusListener(this);
		
		// PW입력창
		pwField = new JPasswordField(12);
		pwField.setSize(200,25);
		pwField.setLocation(70,230);
	
		passwordChar = pwField.getEchoChar();
		pwField.setEchoChar((char)0); // 점점점으로 안보이고 문자로 보이게함
		pwField.setText("비밀번호"); 
		pwField.addFocusListener(this);
		
		// 로그인 버튼
		loginBtn = new JButton("로그인");
		loginBtn.setSize(200,25);
		loginBtn.setLocation(70,260);
		loginBtn.addMouseListener(this);
		
		// 회원가입
		joinlbl = new JLabel("회원가입");
		joinlbl.setSize(50,25);
		joinlbl.setLocation(110,400);
		joinlbl.addMouseListener(this); // 회원가입으로 넘어가기 위한 리스너등록
		
		// 비밀번호 재설정
		rePWlbl = new JLabel("비밀번호 찾기");
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
		
		//창그리기
		this.setVisible(true);		
	}
	
	////////////////////////////////////////////////////
	// 메인 
	public static void main(String[] args) {
		db.DB.init(); // db연결
		
		new MainState();
		Runtime.getRuntime().addShutdownHook(new HookThread());
	}
	
	///////////////////////////////////////////////////////
	//로그인 체크
	private boolean checkIDPW(String id, String pw) {
		boolean check = false;
		
		String sql = "SELECT * FROM MEMBER WHERE ID = '" + id + "' and PW='"+ pw + "'";
		ResultSet rs = db.DB.getResultSet(sql);
		try {
			if(rs.next()) {
				check = true;
				db.DB.getResultSet("UPDATE MEMBER SET " + "LOGIN = '1'" +"WHERE ID='"+id+"'"); // 로그인 성공시 LOGIN = 1
			} else {
				check = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}

	
	// 마우스 클릭했을때 
	@Override
	public void mouseClicked(MouseEvent e) {
		Object obj = e.getSource();
		
		// 회원가입 클릭시
		if(obj == joinlbl) {
			dispose();
			JoinPanel = new Join("회원가입");
		} else if(obj == rePWlbl) {
			dispose();
			RePWPanel = new RePW("비밀번호 찾기");
		} else if(obj == idField && idField.equals("ID")) {
			idField.setText(" ");
			
		//로그인 버튼 클릭시
		} else if(obj == loginBtn) {
			setId(idField.getText());
			String pw = pwField.getText();
			
			boolean check = checkIDPW(id, pw);
			
			// 로그인 성공
			if(check) {
				System.out.println("로그인 성공");
				this.dispose();
				new MainIn("지원톡");
				return;
			
			// 로그인 실패
			} else {
				JOptionPane.showMessageDialog(null, "로그인 실패");
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

