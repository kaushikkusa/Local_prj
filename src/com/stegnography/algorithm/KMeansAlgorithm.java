package com.stegnography.algorithm;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class KMeansAlgorithm {

	public void applyKmeans(String inputFile, String outputFile, int clustersCount) {
		try {
			BufferedImage originalImage = ImageIO.read(new File(inputFile));
			BufferedImage kmeansJpg = startKmeans(originalImage, clustersCount);
			ImageIO.write(kmeansJpg, "bmp", new File(outputFile));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private double getRand(Random rand) {
		return rand.nextDouble() * 255;
	}

	private BufferedImage getKmeansImage(BufferedImage originalImage) {
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		BufferedImage kmeansImage = new BufferedImage(width, height, originalImage.getType());
		Graphics2D g = kmeansImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		return kmeansImage;
	}

	private void writeToKmeansImage(BufferedImage kmeansImage, int width, int height, int[] imageData) {

		int count = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				kmeansImage.setRGB(i, j, imageData[count++]);
			}
		}
	}

	private BufferedImage startKmeans(BufferedImage originalImage, int clustersCount) {
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		BufferedImage kmeansImage = getKmeansImage(originalImage);

		int[] imageData = new int[width * height];
		int count = 0;
		int minimum = Integer.MAX_VALUE;
		int maximum = Integer.MIN_VALUE;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgbVal = kmeansImage.getRGB(i, j);
				imageData[count++] = rgbVal;
				if (rgbVal < minimum) {
					minimum = rgbVal;
				}
				if (rgbVal > maximum) {
					maximum = rgbVal;
				}
			}
		}
		findClusters(imageData, clustersCount, minimum, maximum);
		writeToKmeansImage(kmeansImage, width, height, imageData);

		return kmeansImage;
	}

	private void findClusters(int[] imageData, int clustersCount, int minimum, int maximum) {

		double[][] avgs = new double[clustersCount + 1][4];
		long[] sum = new long[clustersCount + 1];
		long[] count = new long[clustersCount + 1];
		for (int i = 1; i <= clustersCount; i++) {
			Random rand = new Random();
			avgs[i][0] = getRand(rand);
			avgs[i][1] = getRand(rand);
			avgs[i][2] = getRand(rand);
			avgs[i][3] = getRand(rand);
			sum[i] = 0;
			count[i] = 0;
		}
		int[] red = new int[imageData.length];
		int[] blue = new int[imageData.length];
		int[] green = new int[imageData.length];
		int[] alpha = new int[imageData.length];
		for (int i = 0; i < imageData.length; i++) {
			Color color = new Color(imageData[i]);
			red[i] = color.getRed();
			blue[i] = color.getBlue();
			green[i] = color.getGreen();
			alpha[i] = color.getAlpha();
		}
		int[] current = new int[imageData.length];
		boolean run = true;
		while (run) {
			run = false;
			int[] temp = new int[imageData.length];
			for (int x = 0; x < imageData.length; x++) {
				double closest = Double.MAX_VALUE;
				for (int m = 1; m <= clustersCount; m++) {

					double distance = (Math.pow((double) (red[x] - avgs[m][0]), 2)
							+ Math.pow((double) (blue[x] - avgs[m][1]), 2)
							+ Math.pow((double) (green[x] - avgs[m][2]), 2)
							+ Math.pow((double) (alpha[x] - avgs[m][3]), 2));

					if (distance < closest) {
						temp[x] = m;
						closest = distance;
					}
				}
			}
			double changeVal = 0;
			for (int x = 0; x < imageData.length; x++) {
				if (current[x] != temp[x])
					changeVal += 1;
			}
			if (changeVal > 0) {
				run = true;
				for (int x = 0; x < imageData.length; x++) {
					current[x] = temp[x];
				}
				for (int m = 1; m <= clustersCount; m++) {
					sum[m] = 0;
					count[m] = 0;
				}

				for (int i = 0; i < imageData.length; i++) {
					int meanInd = temp[i];
					count[meanInd] += 1;
					if (count[meanInd] == 1) {
						avgs[meanInd][0] = red[i];
						avgs[meanInd][1] = blue[i];
						avgs[meanInd][2] = green[i];
						avgs[meanInd][3] = alpha[i];
					} else {
						double redCount = (double) count[meanInd] - 1.0;
						avgs[meanInd][0] = (redCount / (redCount + 1.0)) * (avgs[meanInd][0] + (red[i] / redCount));
						avgs[meanInd][1] = (redCount / (redCount + 1.0)) * (avgs[meanInd][1] + (blue[i] / redCount));
						avgs[meanInd][2] = (redCount / (redCount + 1.0)) * (avgs[meanInd][2] + (green[i] / redCount));
						avgs[meanInd][3] = (redCount / (redCount + 1.0)) * (avgs[meanInd][3] + (alpha[i] / redCount));

					}

				}
			}

		}
		for (int i = 0; i < imageData.length; i++) {
			imageData[i] = (int) avgs[current[i]][3] << 24 | (int) avgs[current[i]][0] << 16
					| (int) avgs[current[i]][2] << 8 | (int) avgs[current[i]][1] << 0;
		}
	}

}