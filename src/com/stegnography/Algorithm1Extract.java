package com.stegnography;

import com.stegnography.video.ExtractFrames;

public class Algorithm1Extract {

	private static String videoFile = "small.mp4";

	public static void main(String[] args) {

		ExtractFrames extractFrames = new ExtractFrames();
		extractFrames.extractframes(videoFile, "bmp");

		while (true) {

			break;
		}
	}
}
