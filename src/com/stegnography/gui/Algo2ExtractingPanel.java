package com.stegnography.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;

import com.stegnography.Algorithm1Extract;
import com.stegnography.EmbeddingBasePanel;

public class Algo2ExtractingPanel extends ExtractingBasePanel {

	public Algo2ExtractingPanel() {
		algorithm = 2;

		init();
	}

}
