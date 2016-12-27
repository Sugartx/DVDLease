package com.sugar.DVDlease.LeaseGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class TopPanel extends JPanel{
	JLabel titleLabel;
	public TopPanel(){
		buildTitlePanel();
	}
	private void buildTitlePanel(){
		setPreferredSize(new Dimension(800, 60));
		titleLabel = new JLabel("Welcome To The DVD Renting");
		titleLabel.setFont(new Font("", Font.PLAIN, 40));
		add(titleLabel);
		setBorder(new LineBorder(new Color(0, 0, 0)));
//		frame.getContentPane().add(titlePanel, BorderLayout.NORTH);
	}
}
