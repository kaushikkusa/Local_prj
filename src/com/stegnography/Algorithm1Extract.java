package com.stegnography;

import com.stegnography.algorithm.LSBAlgorithm;
import com.stegnography.model.IWTContent;
import com.stegnography.utils.Constants;
import com.stegnography.video.ExtractFrames;

public class Algorithm1Extract {

	private static String videoFile = "videoplayback.mp4";

	public static void main(String[] args) throws Exception {

		ExtractFrames extractFrames = new ExtractFrames();
		extractFrames.extractframes(videoFile, "bmp");

		int index = 2;

		while (true) {

			String frameFile = "results/frames/frame" + index + Constants.extBMP;
			System.out.println(frameFile);
			WavTransformation m = new WavTransformation();
			IWTContent iwtContent = m.decomposeImage(frameFile);

			LSBAlgorithm lsbAlgorithm = new LSBAlgorithm();
			String secretMessage = lsbAlgorithm.extract(iwtContent.getDwtCoefs()[2].getMd().get(), 0,
					iwtContent.getImageData().width, iwtContent.getImageData().height);

			System.out.println(secretMessage);
			break;
		}
	}
}
