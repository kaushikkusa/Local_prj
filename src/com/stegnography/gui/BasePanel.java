package com.stegnography.gui;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class BasePanel extends JPanel {

	public void setButtonHeight(JButton jbButton) {
		jbButton.setFont(new Font(null, Font.PLAIN, 20));
	}

	public void setTextFieldheight(JTextField textField) {
		textField.setFont(new Font("Serif", Font.PLAIN, 20));
	}

	public void setFont(JLabel label) {
		label.setFont(new Font("Serif", Font.PLAIN, 20));
	}

	public void adjustSizes(JPanel jpanel) {
		jpanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 5));
	}

	public static JTextArea outputTextArea;

	
}
