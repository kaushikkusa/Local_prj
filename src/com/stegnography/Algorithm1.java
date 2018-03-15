package com.stegnography;

import java.io.File;

import com.stegnography.algorithm.LSBAlgorithm;
import com.stegnography.model.IWTContent;
import com.stegnography.utils.Constants;
import com.stegnography.video.ExtractFrames;

public class Algorithm1 {

	private static String videoFile = "small.mp4";
	private static String secretMessage = "This is a secret message";

	public static void main(String[] args) throws Exception {

		ExtractFrames extractFrames = new ExtractFrames();
		extractFrames.extractframes(videoFile, "bmp");

		int index = 1;

		while (true) {

			String frameFile = "frames/frame" + index + Constants.extBMP;
			System.out.println(frameFile);
			WavTransformation m = new WavTransformation();
			IWTContent iwtContent = m.decomposeImage(frameFile);

			LSBAlgorithm lsbAlgorithm = new LSBAlgorithm();
			float[][] results = lsbAlgorithm.embedding(iwtContent.getDwtCoefs()[2].getMd().get(), secretMessage, 0,
					iwtContent.getImageData().width, iwtContent.getImageData().height);

			iwtContent.getDwtCoefs()[2].getMd().set(results);

			m.reconstruct(iwtContent);

			break;
		}

	}
	
	
}
