package com.stegnography.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import com.stegnography.Algorithm2Extract;
import com.stegnography.LogData;

public class ExtractingBasePanel extends BasePanel {

	public int algorithm = 1;

	public void init() {

		setLayout(new BorderLayout());

		JPanel rootPanel = new JPanel();

		rootPanel.setBorder(BorderFactory.createTitledBorder(""));
		BoxLayout box = new BoxLayout(rootPanel, BoxLayout.Y_AXIS);
		rootPanel.setLayout(box);

		add(rootPanel, BorderLayout.CENTER);
		File workingDirectory = new File(System.getProperty("user.dir"));

		// JPanel orpanel = new JPanel();
		// JLabel orlabel = new JLabel("(or)");
		// setFont(orlabel);
		// orpanel.add(orlabel);
		// rootPanel.add(orlabel);

		JPanel fileChooserPanel = new JPanel();
		JLabel fileChooserLabel = new JLabel("Choose output file");
		setFont(fileChooserLabel);
		final JTextField fileField = new JTextField("");
		setTextFieldheight(fileField);
		fileField.setColumns(27);
		fileChooserPanel.add(fileChooserLabel);
		fileChooserPanel.add(fileField);
		JButton browseButton = new JButton("Browse");
		setButtonHeight(browseButton);

		browseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(workingDirectory);

				int value = fileChooser.showSaveDialog(ExtractingBasePanel.this);
				if (value == JFileChooser.APPROVE_OPTION) {
					fileField.setText(fileChooser.getSelectedFile().toString());
				}
			}
		});

		JButton cancelButton = new JButton("Cancel");
		setButtonHeight(cancelButton);
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				fileField.setText("");
			}
		});
		fileChooserPanel.add(browseButton);
		fileChooserPanel.add(cancelButton);
		adjustSizes(fileChooserPanel);
		rootPanel.add(fileChooserPanel);

		JPanel submitbuttonpanel = new JPanel();
		JButton submitButton = new JButton("Submit");
		submitbuttonpanel.add(submitButton);

		JTextArea display = new JTextArea(24, 100);

		display.setEditable(false);
		JScrollPane scroll = new JScrollPane(display);
		scroll.setPreferredSize(new Dimension(40, 500));

		scroll.getVerticalScrollBar().setUnitIncrement(1);

		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		rootPanel.add(submitbuttonpanel);
		setButtonHeight(submitButton);
		rootPanel.add(scroll);

		// Thread t = new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// JTextAreaOutputStream out = new JTextAreaOutputStream(display);
		// System.setOut(new PrintStream(out));
		//
		// }
		// });
		// t.start();

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				if (fileField.getText().trim().equals("")) {

					JOptionPane.showMessageDialog(ExtractingBasePanel.this, "Please enter video file/audio files");

					return;
				}

				// if (!new File(fileField.getText().trim()).exists()) {
				//
				// JOptionPane.showMessageDialog(ExtractionPanel.this, "Video
				// file does not exist");
				//
				// return;
				// }
				//
				// if (!new File(audiofileField.getText().trim()).exists()) {
				//
				// JOptionPane.showMessageDialog(ExtractionPanel.this, "Audio
				// file does not exist");
				//
				// return;
				// }

				SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() {

						BasePanel.outputTextArea = display;
						submitButton.setText("Extracting");
						submitButton.setEnabled(false);

						try {

							BasePanel.outputTextArea = display;
							String outputfile = fileField.getText().toString();
							// if (!fileField.getText().endsWith(".wav")) {
							// outputfile = outputfile + ".wav";
							// }
							LogData.outputTextArea = display;

							if (algorithm == 1) {
								final Algorithm1Extract extraction = new Algorithm1Extract();

								extraction.extract(outputfile);
							}
							else  {
								final Algorithm2Extract extraction = new Algorithm2Extract();

								extraction.extract(outputfile);
							}

						} catch (Exception e) {
							e.printStackTrace();
							submitButton.setText("Submit");
							submitButton.setEnabled(true);

							JOptionPane.showMessageDialog(ExtractingBasePanel.this, "" + e.getMessage());
							return null;
						}
						submitButton.setText("Submit");
						submitButton.setEnabled(true);
						// JOptionPane.showMessageDialog(ExtractionPanel.this,
						// "Data Embedded");

						return null;
					}
				};

				mySwingWorker.execute();

			}
		});

	}
}
