/**
 * 만든이 : 남지원
 * 로그인 후 메인 창
 * 만든날 : 2020.06.05
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
	// 유저 세팅
	private static String userId;
	private static String userPw;
	private static String userPhone;
	private String userNickName;
		
	// 우측 패널
	private static JPanel basePanel,userPanel, chatPanel , myPanel;
	
	// 좌측 툴바
	private static JToolBar toolBar;
		
	// 툴바 이미지
	private static ImageIcon imgChat = new ImageIcon("img/toolBar/chat.png");
	private static ImageIcon imgUser = new ImageIcon("img/toolBar/user.png");
	private static ImageIcon imgSet = new ImageIcon("img/toolBar/gear2.png");
		
	//툴바에 사용될 라벨
	private JLabel lblChat, lblUser, lblMy;
	
	// 유저 패널에서 사용되는것
	private JTextArea jtaUser;
	// 로그인한 ID
	private String id = MainState.getId();
	
	// 채팅 패널에서 사용되는것
	private static JTextArea ta;
	private static JTextField tf;
	private static JButton btn;
	private static JPanel panCenter, panBottom;
	
	// 마이 패널에서 사용되는것
	private JLabel logo, lbl;
	
	private ImageIcon logoimg = new ImageIcon("img/logo4.png");
	
			
	private JTextField nickField, phoneField;
	private JPasswordField pwField, pwField2;
	private char passwordChar, passwordChar2;
	private JButton btnSubmit;
	
	// 배경 색상
	private static Color bgColor = new Color(204,234,187);
			
	// 서버 연동
	private static ClientBackground client = new ClientBackground();
	private ServerBackground server;
	
	public void ClientDB()  {
	////////////////////////////////////////////
	// 유저 정보 DB에서 추출
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
		
		}  catch (SQLException e) { //접속하다 발생할 수 있는 오류
		System.err.println("DB 연결 오류 or 쿼리 오류 입니다.");
		e.printStackTrace();
		} 
		
		
		
	}
	///////////////////////////////////////////////////////
	// 메인프레임 
	
	public MainIn(String title)  {
		
		// DB에서 user값 가져옴
		ClientDB();
		
		setTitle(title); 
		setSize(360, 500);	// size설정(width,height)
		setLocation(800, 300); // 위치 설정(x,y)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 우측에 베이스 패널 생성 
		basePanel = new JPanel();
		basePanel.setLayout(new CardLayout());
		
		// 베이스 패널을 center에 넣음
		add(basePanel, BorderLayout.CENTER);
		
		this.makeChatPanel();
		this.makeUserPanel();
		this.makeMyPanel();
		
		// 좌측에 툴바 생성
		makeToolBar();
		add(toolBar,BorderLayout.WEST);
		
		this.setVisible(true); // 창그리기
		
		// 채팅 ClientBackground 연결
		client.setGui(this);
  		client.setNickname(userNickName);
  		client.connet();
	}
	
	// 종료시점 파악 hook
	static class HookThread extends Thread {
		@Override
		public void run() {
			db.DB.getResultSet("UPDATE MEMBER SET " + "LOGIN = '0'" +"WHERE ID='"+userId+"'"); // 로그인 성공시 LOGIN = 1
		}
	}
	
	
	public static void main(String[] args) {
		new MainIn("지원톡");
	}
	
	//////////////////////////////////////////////////////////////
	// 툴바 생성
	private void makeToolBar() {
		toolBar = new JToolBar();
		toolBar.setFloatable(false); // 툴바 고정
		
		toolBar.setOrientation(1); // 툴바 방향설정
		
		// 유저 버튼
		lblUser = new JLabel(imgUser);
		lblUser.addMouseListener(this);
				
		// 채팅 버튼
		lblChat = new JLabel(imgChat);
		lblChat.addMouseListener(this);
		
		// 마이 버튼
		lblMy = new JLabel(imgSet);
		lblMy.addMouseListener(this);
				
		// add
		toolBar.addSeparator(); //줄 구분
		toolBar.add(lblUser);
		toolBar.addSeparator(); //줄 구분
		toolBar.add(lblChat);
		toolBar.addSeparator(); //줄 구분
		toolBar.add(lblMy);
	}
	
	////////////////////////////////////////////
	// 유저 패널 생성
	private void makeUserPanel() {
		JLabel lblName, lbl1;
		
		userPanel = new JPanel();
		JPanel userPanelNorth = new JPanel();
		userPanel.setLayout(new BorderLayout());
		userPanelNorth.setBackground(bgColor);
		
		//lbluser = new JLabel(userId + "\t| " + userPw + "\t| " + userPhone + "\t| " + userNickName);
		
		//
		lblName = new JLabel("내이름 : " + userNickName);
		lbl1 = new JLabel("▼접속자▼");
		
		jtaUser = new JTextArea();
		jtaUser.setEditable(false); //에디트 금지설정
		
		userPanelNorth.add(lblName,BorderLayout.NORTH);
		userPanelNorth.add(lbl1, BorderLayout.NORTH);
		userPanel.add(userPanelNorth, BorderLayout.NORTH);
		userPanel.add(jtaUser,BorderLayout.CENTER);
		basePanel.add(userPanel); // 베이스 패널에 유저패널 add
	}
	
	//////////////////////////////////////////////////////
	// 채팅 패널 생성
	private void makeChatPanel() {
		chatPanel = new JPanel();
		chatPanel.setLayout(new BorderLayout());
		
		// Center 채팅 올라오는 곳
		panCenter = new JPanel();
		panCenter.setLayout(new BorderLayout());
		
		// textarea 
		ta = new JTextArea();
		ta.setEditable(false); //에디트 금지설정
		
		// 스크롤 만들기
		JScrollPane sp = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panCenter.add(sp); //스크롤바를 만든 팬을 add 함
		
		
		//////////////////////////////////////////////
		// Bottom 채팅 치는곳
		panBottom = new JPanel();
		panBottom.setBackground(Color.GRAY);
		
		tf = new JTextField(18);
		tf.addActionListener(this); //엔터마다 보내기위해 리스너등록
		tf.requestFocus();
		
		btn = new JButton("입력");
		btn.addActionListener(this);
		
		panBottom.add(tf);
		panBottom.add(btn);
		
		// 채팅패널 레이아웃 설정
		chatPanel.add(panCenter, BorderLayout.CENTER);
		chatPanel.add(panBottom, BorderLayout.SOUTH);
		
		basePanel.add(chatPanel);
		
	}
	
	// 개인 패널 생성
	private void makeMyPanel() {
		myPanel = new JPanel();
		myPanel.setBackground(bgColor);
		
		myPanel.setLayout(null);
		
		// 로고
		logo = new JLabel(logoimg);
		logo.setSize(70,70);
		logo.setLocation(105,80);
		
		// 닉네임 입력창
		nickField = new JTextField(12);
		nickField.setSize(200,25);
		nickField.setLocation(40,230);
		nickField.setText(userNickName);
		// phone입력창
		phoneField = new JTextField(12);
		phoneField.setSize(200,25);
		phoneField.setLocation(40,260);
		phoneField.setText(userPhone);
		
		// PW입력창
		pwField = new JPasswordField(12);
		pwField.setSize(200,25);
		pwField.setLocation(40,290);
		
		passwordChar = pwField.getEchoChar();
		pwField.setEchoChar((char)0); // 점점점으로 안보이고 문자로 보이게함
		pwField.setText(userPw); 
		
		// PW입력창(중복검사)
		pwField2 = new JPasswordField(12);
		pwField2.setSize(200,25);
		pwField2.setLocation(40,320);
		
		passwordChar2 = pwField.getEchoChar();
		pwField2.setEchoChar((char)0); // 점점점으로 안보이고 문자로 보이게함
		pwField2.setText(userPw); 
		
		// 회원수정 버튼
		btnSubmit = new JButton("수정");
		btnSubmit.setSize(200,25);
		btnSubmit.setLocation(40,350);
		btnSubmit.addMouseListener(this);
		
		myPanel.add(logo);
		myPanel.add(nickField);
		myPanel.add(pwField);
		myPanel.add(pwField2);
		myPanel.add(phoneField);
		myPanel.add(btnSubmit);
		
		basePanel.add(myPanel); // 베이스 패널에 유저패널 add
	}
	//////////////////////////////////////////
	// 클라이언트 백그라운드로 메세지 전송
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
				JOptionPane.showMessageDialog(null, "수정 되었습니다.");
				ClientDB(); // 닉네임 수정 후 DB 다시 가져옴
				makeUserPanel();
				check = true;
			} else {
				System.out.println("실패");
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
      
      // 패널 변경
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
    	// 지역변수로 텍스트값 가져옴
		String id = userId;
		String pw = pwField.getText();
		String pw2 = pwField2.getText();
		String phone = phoneField.getText();
		String nickname = nickField.getText();
		
		
		// 비밀번호 확인작업
		if(!pw.equals(pw2)) {
			JOptionPane.showMessageDialog(null, "비밀번호가 다릅니다.");
			pwField.setText("");
			pwField2.setText("");
			pwField.requestFocus();
			
		// 아이디값 비었는지 확인	시간나면 StringUtils 써보기
		} else if(nickname.isEmpty()) {
			JOptionPane.showMessageDialog(null, "닉네임은 필수사항입니다.");
			nickField.requestFocus();
		
		} else {
			
			// 정상적일 때 수정
			boolean check = updateM(id, pw, phone,nickname);
			if(check) {
				System.out.println("수정 성공");
				
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
		//만약 버튼이나 enter라면 실행
		if(obj == btn || obj == tf) {
			String msg = tf.getText();
			//문자열 비교에는 equals 
			if(!msg.equals("")) {
				msg = tf.getText()+"\n";
				client.sendMessage(userNickName+" : "+msg);
				tf.setText(""); //입력하면 지우지않아도 됨
				
			}
		}
	}

	
		
}

