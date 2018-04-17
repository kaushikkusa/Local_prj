package com.stegnography;

import java.io.File;

import com.stegnography.algorithm.KMeans;
import com.stegnography.util.FileUtil;
import com.stegnography.utils.Constants;
import com.stegnography.video.ExtractFrames;

public class Algorithm2Extract {

	// private static String videoFile = "results/finalVideo";

	public void extract(String videoFile) throws Exception {

		ExtractFrames extractFrames = new ExtractFrames();
		extractFrames.extractframes(videoFile, "bmp");

		int index = 1;

		int messageLength = 0;
		String secretMessage = "";

		// String folder = new File(videoFile).getParent();

		while (true) {

			String frameFile = videoFile + "/frame" + index + Constants.extBMP;
			messageLength = Integer.parseInt(FileUtil.getUserDefinedAttribute(frameFile, "final.secretmessage.length"));

			LogData.printData(frameFile + " " + messageLength);

			KMeans kmMeans = new KMeans();
			kmMeans.applyKmeans(frameFile, "", 3, "", 2);

			secretMessage += kmMeans.outputMessage;
			index++;

			if (secretMessage.length() >= messageLength) {
				secretMessage = secretMessage.substring(0, messageLength);
				break;
			}
		}
		LogData.printData(secretMessage);
	}

}
