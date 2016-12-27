//package com.sugar.DVDlease.LeaseGUI;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.GridLayout;
//
//import javax.swing.JButton;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JPasswordField;
//import javax.swing.JTextField;
//import javax.swing.border.LineBorder;
//
//public class LoginMenu extends JPanel{
//	JPanel[] emptyRow;
//	JLabel loginLabel;
//	JLabel passwordLabel;
//	JTextField loginTF;
//	JPasswordField passwordTF;
//	public LoginMenu(){
//		setLayout(new GridLayout(4, 1));
//		setPreferredSize(new Dimension(200, 500));
//	}
//	private void initEmptyRow(){
//		emptyRow = new JPanel[4];
//		for (int i = 0; i < 4; i++) {
//			emptyRow[i] = new JPanel();
//		}
//		emptyRow[1].setLayout(new GridLayout(3, 2));
//	}
//	private void initInputArea(){
//		loginLabel=new JLabel("    id");
//		loginTF = new JTextField(8);
//		passwordLabel=new JLabel("    password");
//		passwordTF = new JPasswordField();
//		emptyRow[1].add(loginLabel);
//		emptyRow[1].add(loginTF);
//		emptyRow[1].add(passwordLabel);
//		emptyRow[1].add(passwordTF);
//	}
//	private void initButtonArea(){
//		JButton loginButton = new JButton("login");
//		loginButton.addActionListener((e) -> {
//			checkPassword(loginTF, passwordTF);
//		});
//		emptyRow[1].add(loginButton);
//
//		JButton registerButton = new JButton("register");
//		registerButton.addActionListener((e) -> {
//			registerIdentity = Identity.DEBTOR;
//			registerMenu("");
//			paint(frame);
//		});
//		emptyRow[1].add(registerButton);
//	}
//	public void buildLeftPanel(){
//		initEmptyRow();
//		initInputArea();
//		initButtonArea();
//		
//		
//		JButton loginButton = new JButton("login");
//		loginButton.addActionListener((e) -> {
//			checkPassword(loginTF, passwordTF);
//		});
//		emptyRow[1].add(loginButton);
//
//		JButton registerButton = new JButton("register");
//		registerButton.addActionListener((e) -> {
//			registerIdentity = Identity.DEBTOR;
//			registerMenu("");
//			paint(frame);
//		});
//		emptyRow[1].add(registerButton);
//
//		emptyRow[emptyRow.length - 1].add(new JLabel(warning), BorderLayout.PAGE_END);
//
//		leftPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
//		for (int i = 0; i < 4; i++) {
//			leftPanel.add(emptyRow[i]);
//		}
//	}
//	private void checkPassword(JTextField loginTF, JPasswordField passwordTF) {
//		if (m.isDebtorIdExisted(loginTF.getText())) {
//			customer = m.findDebtor(loginTF.getText());
//			if (customer.getPassword().equals(new String(passwordTF.getPassword()))) {
//				if (customer.isBoss() || customer.isManager()) {
//					registerIdentity=Identity.MANAGER;
//					manageMenu("welcome manager");
//				} else {
//					registerIdentity=Identity.DEBTOR;
//					OpearteMenu("");
//					loginMenu("welcome customer");
//				}
//				paint(frame);
//				// frame.repaint();//still wrong
//			} else {
//				leftPanel("password wrong");
//				paint(frame);
//			}
//		} else {
//			leftPanel("you must register first");
//			paint(frame);
//		}
//	}
//}
