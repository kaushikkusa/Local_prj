package com.stegnography.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.io.UnsupportedEncodingException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class MainFrame extends JFrame {

	public static void main(String[] args) throws UnsupportedEncodingException {

		UIDefaults def = UIManager.getLookAndFeelDefaults();
		def.put("TabbedPane.foreground", Color.RED);
		def.put("TabbedPane.textIconGap", new Integer(16));
		def.put("TabbedPane.background", Color.BLUE);
		def.put("TabbedPane.tabInsets", new Insets(10, 10, 10, 10));
		def.put("TabbedPane.selectedTabPadInsets", new Insets(10, 20, 10, 20));

		for (LookAndFeelInfo lookandfeel : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(lookandfeel.getName())) {
				try {
					UIManager.setLookAndFeel(lookandfeel.getClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
		MainFrame main = new MainFrame();
		main.init();
	}

	private JTabbedPane tabbedPane;

	public void init() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 800);
		setLocation(100, 100);
		setTitle("Steganography");

		JPanel rootPanel = new JPanel();

		BoxLayout box = new BoxLayout(rootPanel, BoxLayout.Y_AXIS);
		rootPanel.setLayout(box);

		setVisible(true);
		setResizable(false);

		JTabbedPane tabbedPane = new JTabbedPane();
		final Algo1EmbeddingPanel embeddingPanel = new Algo1EmbeddingPanel();
		final Algo1ExtractionPanel extractionPanel = new Algo1ExtractionPanel();

		final Algo2EmbeddingPanel algo2EmbeddingPanel = new Algo2EmbeddingPanel();
		final Algo2ExtractingPanel algo1ExtractionPanel = new Algo2ExtractingPanel();

		tabbedPane.addTab("LBP Embedding", embeddingPanel);
		tabbedPane.addTab("LBP Extraction", extractionPanel);
		tabbedPane.addTab("K means Embedding", algo2EmbeddingPanel);
		tabbedPane.addTab("K means Extraction", algo1ExtractionPanel);
		tabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
			@Override
			protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
				return 32;
			}

		});
		// tabbedPane.addTab("Statistics", statisticsPanel);

		// tabbedPane.addChangeListener(changeListener);
		tabbedPane.setForeground(Color.BLUE);

		tabbedPane.setTabPlacement(JTabbedPane.LEFT);

		rootPanel.add(tabbedPane, BorderLayout.CENTER);
		add(rootPanel);
		getContentPane().setBackground(Color.WHITE);

		setVisible(true);

	}

}
