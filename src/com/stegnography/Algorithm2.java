package com.stegnography;

import java.io.IOException;

import com.stegnography.algorithm.KMeans;
import com.stegnography.utils.Constants;
import com.stegnography.video.ExtractFrames;

public class Algorithm2 {

	private static String videoFile = "small.mp4";
	private static String secretMessage = "This is asecret message";

	public void start() throws IOException {

		ExtractFrames extractFrames = new ExtractFrames();
		extractFrames.extractframes(videoFile, "bmp");
		LogData.printData("Vido extraction finished.");

		int index = 1;

		while (true) {

			String filecontextpath = "frames/frame" + index;
			String frameFile = filecontextpath + Constants.extBMP;

			KMeans kMeansAlgorithm = new KMeans();
			kMeansAlgorithm.applyKmeans(frameFile, filecontextpath + "output" + Constants.extBMP, 10,"This is as",1);

			// create result image
			

			/*
			 * int clusterIndex = Util.chosenCluster(map); List<Integer>
			 * chosenClusterData = map.get(clusterIndex);
			 * 
			 * System.out.println(chosenClusterData.size());
			 * 
			 * double[][] chosenClusterData2d = new
			 * double[kMeansAlgorithm.width][kMeansAlgorithm.height];
			 * 
			 * int count = 0; for (int i = 0; i < kMeansAlgorithm.width; i++) {
			 * for (int j = 0; j < kMeansAlgorithm.height; j++) {
			 * 
			 * System.out.println(chosenClusterData.get(i));
			 * chosenClusterData2d[i][j] = chosenClusterData.get(i); count++; }
			 * }
			 * 
			 * LBP lbp = new LBP(16, 500); byte[][] imageDataLBP =
			 * lbp.getLBP(chosenClusterData2d); lbp.printMatrix(imageDataLBP);
			 */
			break;
		}
	}

	public static void main(String[] args) throws IOException {

		new Algorithm2().start();
	}
}
