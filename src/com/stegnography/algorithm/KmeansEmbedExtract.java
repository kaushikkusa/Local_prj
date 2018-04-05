package com.stegnography.algorithm;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

public class KmeansEmbedExtract {

	private String ctype;
	private String message;
	BufferedImage p;
	private String saveFile;
	List<Integer> inputData;
	List<Integer> outputData;

	public void embed(String message, List<Integer> inputData) {
		ctype = "E";
		this.message = message;
	}

	public void extract(List<Integer> inputData) {
		ctype = "D";

	}

	public void convert() {
		int count = 0;

		if (ctype.equals("E")) {
			message = convertToBinary(message);
		}

		for (int i = 0; i < inputData.size(); i++) {
			// for (int j = 0; j < p.getHeight(); j++) {

			int color = inputData.get(i);
			int r = color >> 16 & 0x000000FF;
			int g = color >> 8 & 0x000000FF;
			int b = color >> 0 & 0x000000FF;

			if (ctype.equals("D")) {
				if (r % 2 == 1) {
					message += "1";
				} else {
					message += "0";
				}
				if (g % 2 == 1) {
					message += "1";
				} else {
					message += "0";
				}
				if (b % 2 == 1) {
					message += "1";
				} else {
					message += "0";
				}
				if (message.length() > 7) {
					char pc = convertBinary(message.substring(0, 8));
					if (pc <= 31 || pc >= 127) {
						pc = '*';
					}
					System.out.print(pc);
					message = message.substring(8, message.length());
				}
			} else if (ctype.equals("E")) {

				int newRed = r;
				if (count < message.length() && r % 2 != Integer.parseInt("" + message.charAt(count))) {
					if (r % 2 == 0) {
						newRed += 1;
					} else {
						newRed -= 1;
					}
				}
				count++;

				int newGreen = g;
				if (count < message.length() && g % 2 != Integer.parseInt("" + message.charAt(count))) {
					if (g % 2 == 0) {
						newGreen += 1;
					} else {
						newGreen -= 1;
					}
				}
				count++;

				int newBlue = b;
				if (count < message.length() && b % 2 != Integer.parseInt("" + message.charAt(count))) {
					if (b % 2 == 0) {
						newBlue += 1;
					} else {
						newBlue -= 1;
					}
				}
				count++;

				int newValue = 0xff000000 | newRed << 16 | newGreen << 8 | newBlue;
				outputData.add(newValue);

			}
		}

		// }

		// if (ctype.equals("E")) {
		// p.save(saveFile);
		// }
	}

	public String convertToBinary(String message) {
		String bin = "";
		for (int i = 0; i < message.length(); i++) {
			bin += convertLetter(message.charAt(i));
		}
		return bin;
	}

	public String convertLetter(char c) {
		int x = (int) (c);
		String bin = "";
		for (int i = 0; i < 8; i++) {
			bin = "" + (x % 2) + bin;
			x = x / 2;
		}
		return bin;
	}

	public char convertBinary(String bin) {
		int num = 0;
		for (int i = 0; i < 8; i++) {
			num += Math.pow(2, 7 - i) * Integer.parseInt("" + bin.charAt(i));
		}
		return (char) num;
	}

	public static void main(String[] args) {
	}

}
