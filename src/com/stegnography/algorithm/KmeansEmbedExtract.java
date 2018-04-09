package com.stegnography.algorithm;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class KmeansEmbedExtract {

	private String ctype;
	private String message="";
	private String outputmessage="";
	
	BufferedImage p;
	private String saveFile;
	List<Integer> inputData;
	List<Integer> outputData = new ArrayList<>();

	public void embed(String message, List<Integer> inputData) {
		ctype = "E";
		this.message = message;
		this.inputData = inputData;
		convert();
	}

	public void extract(List<Integer> inputData) {
		ctype = "D";
		this.inputData = inputData;

		convert();

	}

	public void convert() {
		int count = 0;

		if (ctype.equals("E")) {
			message = convertToBinary(message);
		}

		for (int i = 0; i < inputData.size(); i++) {
			// for (int j = 0; j < p.getHeight(); j++) {

			int color = inputData.get(i);
			int alpha = color >> 24 & 0xFF;

			int r = color >> 16 & 0xFF;
			int g = color >> 8 & 0xFF;
			int b = color >> 0 & 0xFF;

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
					outputmessage = outputmessage+pc;
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

				//int newValue = 0xff000000 | newRed << 16 | newGreen << 8 | newBlue;

				int newValue = combine(newRed, newGreen, newBlue, alpha);
				System.out.println("Converted " + color + " to " + combine(newRed, newGreen, newBlue, alpha));
				outputData.add(newValue);

			}
			
			
		}
		
		

		// }

//		if (ctype.equals("E")) {
//			p.save(saveFile);
//		}
	}
	
	public static int combine(int r, int g, int b, int a) {
	    return ((a & 0xFF) << 24) |
	            ((r & 0xFF) << 16) |
	            ((g & 0xFF) << 8)  |
	            ((b & 0xFF) << 0);
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
		System.out.println(bin);
		int num = 0;
		for (int i = 0; i < 8; i++) {
			num += Math.pow(2, 7 - i) * Integer.parseInt("" + bin.charAt(i));
		}
		return (char) num;
	}

	public static void main(String[] args) {

		KmeansEmbedExtract kmeansEmbed = new KmeansEmbedExtract();
		List<Integer> inputData = new ArrayList<>();
		inputData.add(13945814);
		inputData.add(13945814);
		inputData.add(13945814);
		inputData.add(13945814);
		inputData.add(13945814);
		inputData.add(13945814);
		inputData.add(13945814);
		inputData.add(13945814);
		inputData.add(13945814);
		inputData.add(13945814);
		inputData.add(13945814);
		inputData.add(13945814);
		inputData.add(13945814);
		inputData.add(13945814);
		inputData.add(13945814);
		inputData.add(13945814);

		kmeansEmbed.embed("thisiste", inputData);

		KmeansEmbedExtract kmeansExtract = new KmeansEmbedExtract();

		System.out.println(kmeansEmbed.outputData.size());
		kmeansExtract.extract(kmeansEmbed.outputData);
		System.out.println(kmeansExtract.outputmessage);

	}

}
