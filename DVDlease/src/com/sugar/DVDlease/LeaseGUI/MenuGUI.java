package com.sugar.DVDlease.LeaseGUI;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.sugar.DVDlease.Debtor.Member;
import com.sugar.DVDlease.Debtor.Management;
import com.sugar.DVDlease.Leasing.DVD;
import com.sugar.DVDlease.Leasing.DVDlist;

enum Identity {
	DEBTOR, MANAGER
}

public class MenuGUI {
	JFrame frame;
	Management m;
	Member customer;
	JPanel titlePanel, centerPanel, leftPanel;
	Identity registerIdentity;

	public MenuGUI() throws Exception {// 生成frame

		customer = new Member();

		try {// 尝试读取文件
			FileInputStream fis = new FileInputStream(".\\src\\member.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			m = (Management) ois.readObject();
			ois.close();
		} catch (Exception e) {

		} finally {
			if (m == null) {// 如果没生成，则进行初始化
				m = new Management();
				Member boss = new Member("tang", "480128", "480128");
				m.addDebtor(boss);
				boss.setIdentityToBOSS();
				m.addDVD(new DVD("Harry Potter", "100", "2:23", 40));
				m.addDVD(new DVD("Harry Potter", "101", "2:23", 40));
				m.addDVD(new DVD("Harry Potter", "102", "2:23", 40));
				m.addDVD(new DVD("Harry Potter", "103", "2:23", 40));
				m.addDVD(new DVD("The Lord of The Ring", "104", "3:11", 30));
				m.addDVD(new DVD("The Lord of The Ring", "105", "3:11", 30));
				m.addDVD(new DVD("The Lord of The Ring", "106", "2:40", 25));
				m.addDVD(new DVD("Miss Peregrine's Home", "107", "3:23", 30));
				m.addDVD(new DVD("Miss Peregrine's Home", "108", "3:23", 30));
			}
		}

		// 生成3块容器，上，左下，中右下。

		frame = new JFrame("DVD Tenancy");
		frame.setLayout(new BorderLayout());

		titlePanel();

		leftPanel = new JPanel();
		leftPanel("");
		leftPanel.setOpaque(false);
		frame.getContentPane().add(leftPanel, BorderLayout.WEST);

		centerPanel = new JPanel();
		centerPanel();

		frame.setBounds(100, 100, 800, 600);
		GUIclose();
		frame.setDefaultCloseOperation(3);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void GUIclose() {
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					saveData();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

	}

	public void saveData() throws Exception {
		FileOutputStream fos = new FileOutputStream(".\\src\\member.dat");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(m);
		oos.close();
	}

	public void titlePanel() {
		titlePanel = new JPanel();
		titlePanel.setPreferredSize(new Dimension(800, 60));
		JLabel titleLabel = new JLabel("Welcome To The DVD Renting");
		titleLabel.setFont(new Font("", Font.PLAIN, 40));
		titlePanel.add(titleLabel);
		titlePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		frame.getContentPane().add(titlePanel, BorderLayout.NORTH);
	}

	public void centerPanel() {
		centerPanel.removeAll();
		centerPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		centerPanel.setPreferredSize(new Dimension(600, 540));
		centerPanel.setLayout(new GridLayout(2, 3));
//dasdad
		MyCanvas[] mc = new MyCanvas[6];
		Image[] imgs = new Image[6];
		for (int i = 0; i < 6; i++) {
			mc[i] = new MyCanvas();
			imgs[i] = Toolkit.getDefaultToolkit().getImage("./image/" + i + ".jpg");
			mc[i].setImage(imgs[i]);
			centerPanel.add(mc[i]);
		}

		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
	}

	class MyCanvas extends Canvas {
		Image img;

		public void setImage(Image img) {
			this.img = img;
		}

		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, 200, 270, this);
		}
	}

	public void leftPanel(String warning) {
		leftPanel.removeAll();
		leftPanel.setLayout(new GridLayout(4, 1));
		leftPanel.setPreferredSize(new Dimension(200, 500));
		JPanel[] emptyRow = new JPanel[4];
		for (int i = 0; i < 4; i++) {
			emptyRow[i] = new JPanel();
		}
		emptyRow[1].setLayout(new GridLayout(3, 2));

		String[] chooseText = { "    id", "    password" };
		JLabel[] chooseLabel = new JLabel[2];
		for (int i = 0; i < 2; i++) {
			chooseLabel[i] = new JLabel();
			chooseLabel[i].setText(chooseText[i]);
		}

		JTextField loginTF;
		JPasswordField passwordTF;
		loginTF = new JTextField(8);
		passwordTF = new JPasswordField();
		emptyRow[1].add(chooseLabel[0]);
		emptyRow[1].add(loginTF);
		emptyRow[1].add(chooseLabel[1]);
		emptyRow[1].add(passwordTF);

		JButton loginButton = new JButton("login");
		loginButton.addActionListener((e) -> {
			checkPassword(loginTF, passwordTF);
		});
		emptyRow[1].add(loginButton);

		JButton registerButton = new JButton("register");
		registerButton.addActionListener((e) -> {
			registerIdentity = Identity.DEBTOR;
			registerMenu("");
			paint(frame);
		});
		emptyRow[1].add(registerButton);

		emptyRow[emptyRow.length - 1].add(new JLabel(warning), BorderLayout.PAGE_END);

		leftPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		for (int i = 0; i < 4; i++) {
			leftPanel.add(emptyRow[i]);
		}

	}

	private void checkPassword(JTextField loginTF, JPasswordField passwordTF) {
		if (m.isDebtorIdExisted(loginTF.getText())) {
			customer = m.findDebtor(loginTF.getText());
			if (customer.getPassword().equals(new String(passwordTF.getPassword()))) {
				if (customer.isBoss() || customer.isManager()) {
					registerIdentity = Identity.MANAGER;
					manageMenu("welcome manager");
				} else {
					registerIdentity = Identity.DEBTOR;
					OpearteMenu("");
					loginMenu("welcome customer");
				}
				paint(frame);
				// frame.repaint();//still wrong
			} else {
				leftPanel("password wrong");
				paint(frame);
			}
		} else {
			leftPanel("you must register first");
			paint(frame);
		}
	}

	public void manageMenu(String string) {
		leftPanel.removeAll();
		JPanel[] emptyRow = new JPanel[3];
		for (int i = 0; i < 3; i++) {
			emptyRow[i] = new JPanel();
		}
		emptyRow[1].setLayout(new GridLayout(4, 1, 10, 10));

		String[] str = { "       name:" + customer.getName() + " ", "       id:     " + customer.getId() + " " };

		for (int i = 0; i < 2; i++) {
			JLabel label = new JLabel(str[i]);
			label.setFont(new Font("", Font.PLAIN, 20));
			emptyRow[1].add(label);
		}

		emptyRow[2].setLayout(new GridLayout(0, 2));

		JButton addMemberbutton = new JButton("Add M");
		addMemberbutton.addActionListener((e) -> {
			registerIdentity = Identity.MANAGER;
			registerMenu("");
			paint(frame);
		});
		emptyRow[2].add(addMemberbutton);

		JButton addDVDbutton = new JButton("Add DVD");
		addDVDbutton.addActionListener((e) -> {
			addDVDMenu("");
			paint(frame);
		});
		emptyRow[2].add(addDVDbutton);

		JButton ChangeButton = new JButton("Change");
		ChangeButton.addActionListener((e) -> {
			changepasswordMenu();
			paint(frame);
		});
		emptyRow[2].add(ChangeButton);

		JButton Exitbutton = new JButton("Exit");
		Exitbutton.addActionListener((e) -> {
			leftPanel("");
			centerPanel();
			paint(frame);
		});
		emptyRow[2].add(Exitbutton);

		if (customer.isBoss()) {
			JButton deleteButton = new JButton("Delete");
			deleteButton.addActionListener((e) -> {
				deleteMenu();
				paint(frame);
			});
			emptyRow[2].add(deleteButton);
		}

		for (int i = 0; i < 3; i++) {
			leftPanel.add(emptyRow[i]);
		}

		// 生成centerPanel

		centerPanel.removeAll();
		centerPanel.setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.weightx = 6;
		constraints.weighty = 15;

		JTextField inputTF = new JTextField(20);
		addComponent(centerPanel, inputTF, constraints, 1, 0, 3, 1);

		JButton search = new JButton("search");
		search.addActionListener((e) -> {
			String name = inputTF.getText();
			manageMenu(name);
			paint(frame);
		});
		addComponent(centerPanel, search, constraints, 4, 0, 1, 1);

		String[] titletext = { "name", "id", "length", "money", "status", "" };
		JLabel[] title = new JLabel[6];
		for (int i = 0; i < 6; i++) {
			title[i] = new JLabel(titletext[i]);
			title[i].setFont(new Font("", Font.ROMAN_BASELINE, 20));
			addComponent(centerPanel, title[i], constraints, i, 1, 1, 1);
		}

		int count = 0;
		for (DVDlist list : m.storeroomlist) {
			if (list.getName().contains(string)) {
				count += list.getdisklist().size();
			}
		}
		JLabel[][] resultLabel;
		if (count != 0) {
			resultLabel = new JLabel[count][5];
		} else {
			resultLabel = new JLabel[1][5];
		}
		int row = 2;
		if (!string.isEmpty()) {
			m.printDVD(string);
			for (DVDlist list : m.storeroomlist) {
				if (list.getName().contains(string)) {
					for (DVD disk : list.getdisklist()) {
						String[] resulttext = { disk.getName(), disk.getId(), disk.getLength(),
								String.valueOf(disk.getMoney()), disk.getStringState() };
						for (int i = 0; i < 5; i++) {
							resultLabel[row - 2][i] = new JLabel(resulttext[i]);
							resultLabel[row - 2][i].setFont(new Font("", Font.ROMAN_BASELINE, 20));
							addComponent(centerPanel, resultLabel[row - 2][i], constraints, i, row, 1, 1);
						}
						JButton removeButton = new JButton("Remove");
						removeButton.addActionListener(new ManagerButtonAction(resultLabel[row - 2][1]));
						addComponent(centerPanel, removeButton, constraints, 5, row, 1, 1);
						row++;
					}
				}
			}
		}
		for (int i = row; i < constraints.weighty; i++) {
			JLabel emptyLabel = new JLabel("");
			addComponent(centerPanel, emptyLabel, constraints, 0, i, 6, 1);
		}

	}

	class ManagerButtonAction implements ActionListener {
		JLabel idLabel;

		public ManagerButtonAction(JLabel idLabel) {
			this.idLabel = idLabel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String id = idLabel.getText();
			m.removeDVD(id);
			manageMenu("");
			paint(frame);
		}
	}

	public void deleteMenu() {
		centerPanel.removeAll();
		centerPanel.setLayout(new GridLayout(3, 1));
		JPanel[] emptyRow = new JPanel[3];
		for (int i = 0; i < 3; i++) {
			emptyRow[i] = new JPanel();
			centerPanel.add(emptyRow[i]);
		}

		emptyRow[1].setLayout(new GridLayout(1, 3));
		JPanel[] emptyColumn = new JPanel[3];
		for (int i = 0; i < 3; i++) {
			emptyColumn[i] = new JPanel();
			emptyRow[1].add(emptyColumn[i]);
		}

		emptyColumn[1].setLayout(new GridLayout(4, 1));
		JLabel tip = new JLabel("input the id");
		emptyColumn[1].add(tip);

		JTextField idTF = new JTextField(10);
		emptyColumn[1].add(idTF);

		JButton deleteButton = new JButton("delete");
		JButton backButton = new JButton("Back");

		deleteButton.addActionListener((e) -> {
			String id = idTF.getText();
			if (!id.equals("480128")) {
				m.removeDebtor(id);
			}
			deleteMenu();
			paint(frame);
		});
		backButton.addActionListener((e) -> {
			centerPanel();
			manageMenu("");
			paint(frame);
		});

		emptyColumn[1].add(deleteButton);
		emptyColumn[1].add(backButton);

		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
	}

	public void addDVDMenu(String warning) {
		centerPanel.removeAll();
		centerPanel.setLayout(new GridLayout(1, 3));

		JPanel[] emptyColumn = new JPanel[3];
		for (int i = 0; i < 3; i++) {
			emptyColumn[i] = new JPanel();
			centerPanel.add(emptyColumn[i]);
		}

		emptyColumn[1].setLayout(new GridLayout(3, 1));

		JPanel[] emptyRow = new JPanel[3];
		for (int i = 0; i < 3; i++) {
			emptyRow[i] = new JPanel();
			emptyColumn[1].add(emptyRow[i]);
		}

		emptyRow[1].setLayout(new GridLayout(5, 2));

		JLabel nameLabel = new JLabel("name:");
		emptyRow[1].add(nameLabel);

		JTextField nameTF = new JTextField(10);
		emptyRow[1].add(nameTF);

		JLabel idLabel = new JLabel("id:");
		emptyRow[1].add(idLabel);

		JTextField idTF = new JTextField(10);
		emptyRow[1].add(idTF);

		JLabel lengthLabel = new JLabel("length:");
		emptyRow[1].add(lengthLabel);

		JTextField lengthTF = new JTextField(10);
		emptyRow[1].add(lengthTF);

		JLabel moneyLabel = new JLabel("money:");
		emptyRow[1].add(moneyLabel);

		JTextField moneyTF = new JTextField(10);
		emptyRow[1].add(moneyTF);

		JButton addButton = new JButton("add");
		addButton.addActionListener((e) -> {
			if (m.isDebtorIdExisted(idTF.getText())) {
				addDVDMenu("id existed");
			} else if (Integer.valueOf(moneyTF.getText()) > 0) {

				DVD disk = new DVD(nameTF.getText(), idTF.getText(), lengthTF.getText(),
						Integer.valueOf(moneyTF.getText()));
				m.addDVD(disk);

				paint(frame);
			} else {
				addDVDMenu("输入要大于0");
			}
		});
		emptyRow[1].add(addButton);

		JButton backButton = new JButton("back");
		backButton.addActionListener((e) -> {
			manageMenu("");
			paint(frame);
		});
		emptyRow[1].add(backButton);
		emptyRow[emptyRow.length - 1].add(new JLabel(warning), BorderLayout.PAGE_END);

	}

	public void registerMenu(String warning) {
		leftPanel.removeAll();
		centerPanel.removeAll();
		centerPanel.setLayout(new GridLayout(1, 3));

		JPanel[] emptyColumn = new JPanel[3];
		for (int i = 0; i < 3; i++) {
			emptyColumn[i] = new JPanel();
			centerPanel.add(emptyColumn[i]);
		}

		emptyColumn[1].setLayout(new GridLayout(3, 1));

		JPanel[] emptyRow = new JPanel[3];
		for (int i = 0; i < 3; i++) {
			emptyRow[i] = new JPanel();
			emptyColumn[1].add(emptyRow[i]);
		}

		emptyRow[1].setLayout(new GridLayout(5, 2));

		JLabel name = new JLabel("name:");
		emptyRow[1].add(name);

		JTextField nameTF = new JTextField(10);
		emptyRow[1].add(nameTF);

		JLabel idLabel = new JLabel("id:");
		emptyRow[1].add(idLabel);

		JTextField idTF = new JTextField(10);
		emptyRow[1].add(idTF);

		JLabel passwordLabel = new JLabel("password:");
		emptyRow[1].add(passwordLabel);

		JTextField passwordTF = new JTextField(10);
		emptyRow[1].add(passwordTF);

		JLabel checkpasswordLabel = new JLabel("again:");
		emptyRow[1].add(checkpasswordLabel);

		JTextField checkpasswordTF = new JTextField(10);
		emptyRow[1].add(checkpasswordTF);

		JButton registerButton = new JButton("register");
		registerButton.addActionListener((e) -> {
			if (m.isDebtorIdExisted(idTF.getText())) {
				System.out.println("id existed");
				registerMenu("id existed");
				paint(frame);
			} else if (passwordTF.getText().equals(checkpasswordTF.getText())) {
				Member debtor = new Member(nameTF.getText(), idTF.getText(), passwordTF.getText());
				m.addDebtor(debtor);
				switch (registerIdentity) {
				case DEBTOR:
					debtor.setIdentityToDebtor();
					leftPanel("");
					centerPanel();
					break;
				case MANAGER:
					debtor.setIdentityToManager();
					manageMenu("");
					break;
				default:
					break;
				}
				paint(frame);
			}
		});
		emptyRow[1].add(registerButton);

		JButton backButton = new JButton("back");
		backButton.addActionListener((e) -> {
			switch (registerIdentity) {
			case DEBTOR:
				leftPanel("");
				centerPanel();
				break;
			case MANAGER:
				manageMenu("");
				break;
			default:
				break;
			}
			paint(frame);
		});
		emptyRow[1].add(backButton);

		emptyRow[emptyRow.length - 1].add(new JLabel(warning), BorderLayout.PAGE_END);
	}

	public void loginMenu(String warning) {
		leftPanel.removeAll();
		JPanel[] emptyRow = new JPanel[4];
		for (int i = 0; i < 4; i++) {
			emptyRow[i] = new JPanel();
		}
		emptyRow[1].setLayout(new GridLayout(4, 1, 10, 10));

		String[] str = { "       name:" + customer.getName() + " ", "       id:     " + customer.getId() + " ",
				"       vip:    " + String.valueOf(customer.getVip()) + " " };

		for (int i = 0; i < 3; i++) {
			JLabel label = new JLabel(str[i]);
			label.setFont(new Font("", Font.PLAIN, 20));
			emptyRow[1].add(label);
		}
		JButton returnButton = new JButton("Return");
		JButton rechargeButton = new JButton("Recharge");
		JButton exitButton = new JButton("Exit");
		JButton changeButton = new JButton("Change");
		returnButton.addActionListener((e) -> {
			returnMenu();
			paint(frame);
		});
		rechargeButton.addActionListener((e) -> {
			rechargeMenu(customer);
			paint(frame);
		});
		exitButton.addActionListener((e) -> {
			leftPanel("");
			centerPanel();
			paint(frame);
		});
		changeButton.addActionListener((e) -> {
			changepasswordMenu();
			paint(frame);
		});
		emptyRow[2].setLayout(new GridLayout(0, 2, 20, 20));
		emptyRow[2].add(returnButton);
		emptyRow[2].add(rechargeButton);
		emptyRow[2].add(changeButton);
		emptyRow[2].add(exitButton);

		emptyRow[emptyRow.length - 1].add(new JLabel(warning), BorderLayout.PAGE_END);

		for (int i = 0; i < 4; i++) {
			leftPanel.add(emptyRow[i]);
		}

	}

	public void changepasswordMenu() {
		centerPanel.removeAll();
		centerPanel.setLayout(new GridLayout(3, 1));
		JPanel[] emptyRow = new JPanel[3];
		for (int i = 0; i < 3; i++) {
			emptyRow[i] = new JPanel();
			centerPanel.add(emptyRow[i]);
		}

		emptyRow[1].setLayout(new GridLayout(1, 3));
		JPanel[] emptyColumn = new JPanel[3];
		for (int i = 0; i < 3; i++) {
			emptyColumn[i] = new JPanel();
			emptyRow[1].add(emptyColumn[i]);
		}

		emptyColumn[1].setLayout(new GridLayout(5, 1));
		JLabel tipLabel = new JLabel("input new password");
		emptyColumn[1].add(tipLabel);

		JTextField passwordTF = new JTextField(10);
		emptyColumn[1].add(passwordTF);
		JTextField passwordCheckTF = new JTextField(10);
		emptyColumn[1].add(passwordCheckTF);

		JButton changeButton = new JButton("change");
		JButton backButton = new JButton("Back");
		changeButton.addActionListener((e) -> {
			if (passwordTF.getText().equals(passwordCheckTF.getText())) {
				customer.setPassword(passwordTF.getText());
				leftPanel("");
				centerPanel();
				paint(frame);
			}
		});
		backButton.addActionListener((e) -> {
			if (registerIdentity == Identity.DEBTOR) {
				loginMenu("");
				OpearteMenu("");
			} else {
				manageMenu("");
			}
			paint(frame);
		});
		emptyColumn[1].add(changeButton);
		emptyColumn[1].add(backButton);

		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
	}

	public void returnMenu() {
		centerPanel.removeAll();
		centerPanel.setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.weightx = 6;
		constraints.weighty = 15;

		String[] titleText = { "name", "id", "money", "starttime", "endtime", "" };
		JLabel[] title = new JLabel[6];
		for (int i = 0; i < 6; i++) {
			title[i] = new JLabel(titleText[i]);
			title[i].setFont(new Font("", Font.ROMAN_BASELINE, 20));
			addComponent(centerPanel, title[i], constraints, i, 0, 1, 1);
		}
		int row = 1;
		JLabel[][] resultLabel = new JLabel[customer.getRent().size()][5];
		JButton[] unleaseButton = new JButton[customer.getRent().size()];
		for (DVD disk : customer.getRent()) {
			String[] resultText = { "   " + disk.getName(), disk.getId(), String.valueOf(disk.getMoney()),
					disk.getStarttime(), disk.getEndtime() };
			for (int i = 0; i < 5; i++) {
				resultLabel[row - 1][i] = new JLabel(resultText[i]);
				resultLabel[row - 1][i].setFont(new Font("", Font.ROMAN_BASELINE, 20));
				addComponent(centerPanel, resultLabel[row - 1][i], constraints, i, row, 1, 1);
			}
			unleaseButton[row - 1] = new JButton("return");
			unleaseButton[row - 1].addActionListener(new ReturnButtonAction(resultLabel[row - 1][1]));
			addComponent(centerPanel, unleaseButton[row - 1], constraints, 5, row, 1, 1);
			row++;
		}
		for (int i = row; i < constraints.weighty; i++) {
			JLabel emptyLabel = new JLabel("");
			addComponent(centerPanel, emptyLabel, constraints, 0, i, 6, 1);
		}
		JButton backButton = new JButton("back");
		backButton.addActionListener((e) -> {
			centerPanel();
			OpearteMenu("");
			paint(frame);
		});
		addComponent(centerPanel, backButton, constraints, 4, 14, 1, 1);

	}

	class ReturnButtonAction implements ActionListener {
		String id;
		JLabel idLabel;
		DVD disk;

		public ReturnButtonAction(JLabel idLabel) {
			this.idLabel = idLabel;
			disk = new DVD("", "", "", 0);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			id = idLabel.getText();
			disk = m.findDVDFromId(id);
			if (disk != null) {
				m.returnDVD(disk, customer);
			} else {
				customer.getRent().remove(customer.findDVDfromid(id));
			}
			returnMenu();
			paint(frame);
		}
	}

	public void rechargeMenu(Member d) {
		centerPanel.removeAll();
		centerPanel.setLayout(new GridLayout(3, 1));
		JPanel[] emptyrow = new JPanel[3];
		for (int i = 0; i < 3; i++) {
			emptyrow[i] = new JPanel();
			centerPanel.add(emptyrow[i]);
		}

		emptyrow[1].setLayout(new GridLayout(1, 3));
		JPanel[] emptycolumn = new JPanel[3];
		for (int i = 0; i < 3; i++) {
			emptycolumn[i] = new JPanel();
			emptyrow[1].add(emptycolumn[i]);
		}

		emptycolumn[1].setLayout(new GridLayout(4, 1));
		JLabel menoy = new JLabel("you have:" + String.valueOf(d.getMoney()));
		emptycolumn[1].add(menoy);

		JTextField moneyTF = new JTextField(10);
		emptycolumn[1].add(moneyTF);

		JButton rechargeButton = new JButton("Recharge");
		JButton backButton = new JButton("Back");
		rechargeButton.addActionListener((e) -> {
			if (Double.valueOf(moneyTF.getText()) < 0) {
				loginMenu("输入的数要大于0");
			} else {
				customer.addMoney(Double.valueOf(moneyTF.getText()));
				loginMenu("");
			}
			rechargeMenu(customer);
			paint(frame);
		});
		backButton.addActionListener((e) -> {
			loginMenu("");
			OpearteMenu("");
			paint(frame);
		});
		emptycolumn[1].add(rechargeButton);
		emptycolumn[1].add(backButton);

		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
	}

	public void OpearteMenu(String string) {
		centerPanel.removeAll();
		centerPanel.setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.weightx = 6;
		constraints.weighty = 15;

		JTextField inputTF = new JTextField(string, 10);
		addComponent(centerPanel, inputTF, constraints, 1, 0, 3, 1);

		JButton searchButton = new JButton("search");
		searchButton.addActionListener((e) -> {
			OpearteMenu(inputTF.getText());
			paint(frame);
		});
		addComponent(centerPanel, searchButton, constraints, 4, 0, 1, 1);

		String[] titleText = { "name", "id", "length", "money", "status", "" };
		JLabel[] title = new JLabel[6];
		for (int i = 0; i < 6; i++) {
			title[i] = new JLabel(titleText[i]);
			title[i].setFont(new Font("", Font.ROMAN_BASELINE, 20));
			addComponent(centerPanel, title[i], constraints, i, 1, 1, 1);
		}

		int count = 0;
		for (DVDlist list : m.storeroomlist) {
			if (list.getName().contains(string)) {
				count += list.getdisklist().size();
			}
		}
		JLabel[][] resultLabel;
		if (count != 0) {
			resultLabel = new JLabel[count][5];
		} else {
			resultLabel = new JLabel[1][5];
		}
		int row = 2;
		if (!string.isEmpty()) {
			m.printDVD(string);
			for (DVDlist list : m.storeroomlist) {
				if (list.getName().contains(string)) {
					for (DVD disk : list.getdisklist()) {
						String[] resulttext = { disk.getName(), disk.getId(), disk.getLength(),
								String.valueOf(disk.getMoney()), disk.getStringState() };
						for (int i = 0; i < 5; i++) {
							resultLabel[row - 2][i] = new JLabel(resulttext[i]);
							resultLabel[row - 2][i].setFont(new Font("", Font.ROMAN_BASELINE, 20));
							addComponent(centerPanel, resultLabel[row - 2][i], constraints, i, row, 1, 1);
						}
						JButton lease = new JButton("lease");
						lease.addActionListener(new OpearateButtonAction(resultLabel[row - 2][1], inputTF));
						addComponent(centerPanel, lease, constraints, 5, row, 1, 1);
						row++;
					}
				}
			}
		}
		for (int i = row; i < constraints.weighty; i++) {
			JLabel emptyLabel = new JLabel("");
			addComponent(centerPanel, emptyLabel, constraints, 0, i, 6, 1);
		}

	}

	class OpearateButtonAction implements ActionListener {
		String id;
		JLabel idLabel;
		JTextField searchTF;

		public OpearateButtonAction(JLabel idLabel, JTextField searchTF) {
			this.idLabel = idLabel;
			this.searchTF = searchTF;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			id = idLabel.getText();
			System.out.println(id);
			DVD temp = new DVD("", "", "", 0);
			temp = m.findDVDFromId(id);
			if (customer.isCustom(temp.money)) {
				m.leaseDVD(temp, customer);
				OpearteMenu(searchTF.getText());
				loginMenu("parchase success");
			} else {
				OpearteMenu(searchTF.getText());
				loginMenu("parchase failed");
			}
			paint(frame);
		}
	}

	private static void addComponent(JPanel p, Component c, GridBagConstraints constraints, int x, int y, int w,
			int h) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = w;
		constraints.gridheight = h;
		p.add(c, constraints);
	}

	public static void paint(JFrame f) {
		f.setVisible(false);
		f.setVisible(true);
	}
}
