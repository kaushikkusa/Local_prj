package com.stegnography;

import com.stegnography.text.ExtractText;
import com.stegnography.video.ExtractFrames;

public class MainClass {

	private static final String VIDEO=  "../samples/videoplayback.mp4";
	private static final String TEXT=  "This is a secret message";
	
	public static void main(String[] args) {
		
		ExtractText extractText = new ExtractText();
		String binaryData = extractText.getBinaryString(TEXT);
		System.out.println(binaryData);
		
		ExtractFrames extractFrames = new ExtractFrames();
		extractFrames.extractframes(VIDEO, "jpg");
	}
}
