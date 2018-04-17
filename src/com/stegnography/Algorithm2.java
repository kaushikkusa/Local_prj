package com.stegnography;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.stegnography.algorithm.KMeans;
import com.stegnography.algorithm.KmeansEmbedExtract;
import com.stegnography.algorithm.LSBAlgorithm;
import com.stegnography.model.IWTContent;
import com.stegnography.util.FileUtil;
import com.stegnography.utils.Constants;
import com.stegnography.video.ExtractAudio;
import com.stegnography.video.ExtractFrames;
import com.stegnography.video.ImagetoVideoConverter;

public class Algorithm2 {

	private static String videoFile = "small.mp4";
	private static String secretMessage = "Java 8 is the most awaited and is a major feature release of Java programming language. This is an introductory tutorial that explains the basic-to-advanced features of Java 8 and their usage in a simple and intuitive way.";
	private static String outputDirectory = "results/finalVideo";

	public void start(String videoFile,String secretMessage,String outputDirectory) throws IOException {

		new File(outputDirectory).mkdirs();
		if (new File(outputDirectory).exists()) {
			FileUtils.cleanDirectory(new File(outputDirectory));
		}
		if (new File("frames").exists()) {
			FileUtils.cleanDirectory(new File("frames"));
		}
		if (new File("results/frames").exists()) {
			FileUtils.cleanDirectory(new File("results/frames"));
		}

		ExtractFrames extractFrames = new ExtractFrames();
		extractFrames.extractframes(videoFile, "bmp");
		LogData.printData("Vido extraction finished.");

		ExtractAudio.extractAudio(videoFile);

		int index = 1;
		int secretMessageIndex = 0;
		String originalMessage = secretMessage;
		secretMessage = new KmeansEmbedExtract().convertToBinary(secretMessage);
		String remainingString = secretMessage;

		while (true) {

			String frameFile = "frames/frame" + index + Constants.extBMP;
			String resultFrame = "frames/frame" + index + Constants.extBMP;

			KMeans kMeansAlgorithm = new KMeans();
			int embeddingIndex = kMeansAlgorithm.applyKmeans(frameFile, resultFrame, 20, remainingString, 1);

			// secretMessageIndex = secretMessageIndex + embeddingIndex;
			LogData.printData("Message embedded till index" + embeddingIndex+" Input string length"+remainingString.length());
			LogData.printData("Embedded unitl now " + secretMessageIndex);

			// if (secretMessageIndex < remainingString.length()) {
			//
			// }
			if(embeddingIndex < remainingString.length())
			remainingString = remainingString.substring(embeddingIndex);
			else{
				remainingString="";
			}

			index++;

			FileUtils.copyFileToDirectory(new File(resultFrame), new File(outputDirectory));
			writeToFile(new File(outputDirectory).getAbsolutePath() + "/" + new File(resultFrame).getName(),
					secretMessageIndex, originalMessage.length(), kMeansAlgorithm.clusterPositions);
			writeToFile(resultFrame, secretMessageIndex, originalMessage.length(), kMeansAlgorithm.clusterPositions);

			LogData.printData(remainingString);

			if (remainingString.equals("")) {
				LogData.printData("Completed embedding");
				break;
			}
		}

		LogData.printData("Writing meta data to file");
		writeToFile(videoFile, secretMessage.length(), secretMessage.length(), "");
		new ImagetoVideoConverter().imagetovideo(outputDirectory + "/output." + FilenameUtils.getExtension(videoFile));

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeToFile(String file, int messageLength, int totalMessageLength, String positions) {
		LogData.printData("Writing meta data to file" + file);
		FileUtil.setUserDefinedAttribute(file, "secretmessage.length", messageLength + "");
		FileUtil.setUserDefinedAttribute(file, "final.secretmessage.length", totalMessageLength + "");
		FileUtil.setUserDefinedAttribute(file, "index.positions", positions + "");

	}

	public static void main(String[] args) throws IOException {

		new Algorithm2().start(videoFile,secretMessage,outputDirectory);
	}
}
