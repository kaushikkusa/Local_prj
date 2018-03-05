package com.stegnography;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import com.stegnography.compress.ImageCompressor;
import com.stegnography.dwt.wavelets.IWT;
import com.stegnography.dwt.wavelets.IWTCoefficients;
import com.stegnography.dwt.wavelets.Matrix;
import com.stegnography.dwt.wavelets.WaveletTransform;
import com.stegnography.image.ImageDto;
import com.stegnography.image.ImageReader;
import com.stegnography.utils.Constants;
import com.stegnography.utils.Log;

public class WavTransformation {
	private int mDecompLevels = 1;
	private int mQuantizLevels = 64;
	private boolean doReconstruct;
	private Class classWaveletTransform;
	private String mOutputFormat = Constants.extBMP;
	private boolean toCopyOriginImageToResults = true;

	public void setOutputFormat(String mOutputFormat) {
		this.mOutputFormat = mOutputFormat;
	}

	WavTransformation(int dLvls, int quantLvls, Class transformClass) {
		mDecompLevels = dLvls;
		mQuantizLevels = quantLvls;
		doReconstruct = true;
		classWaveletTransform = transformClass;
		
		new File(Constants.resultsFolder).mkdirs();
		new File(Constants.resultsFolder, Constants.picsFolder).mkdirs();
		new File(Constants.resultsFolder, Constants.resultsDebugDataFolder).mkdirs();
	}



	public void decomposeImage(String filename) {
		ImageReader ia = new ImageReader();
		ImageDto imageData = null;
		try {
			imageData = ia.readImageFile(filename);
			Log.getInstance().log(Level.FINE,
							"Image from file " + filename + " was read(w=" + imageData.width + ", h="
									+ imageData.height + ").");
			// System.out.println("Image from file "+filename+" was read(w="+imageData.width+", h="+imageData.height+").");
	
			//if (toCopyOriginImageToResults)
				//imageData.saveToImageFile(filename, mOutputFormat);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		final boolean logCoefsToFile = true;

		IWTCoefficients[]  dwtCoefs;
		
		WaveletTransform method = null;
		try {
			method = (WaveletTransform) classWaveletTransform.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		dwtCoefs = decomposeImage(logCoefsToFile, imageData, method);
		System.out.println(imageData.getFilename());
		if (doReconstruct)
			simpleReconstruct(new IWT(method), imageData.getFilename(), imageData.width, imageData.height, false, dwtCoefs);

		

		final String imageFilename = imageData.getFilename() + method.getCaption();
		Log.getInstance().log(Level.INFO,
				"\n -=Quantization=-  [" + mQuantizLevels + " levels]");
		ImageCompressor mQuantization = new ImageCompressor(mQuantizLevels);
		IWTCoefficients decodedCoefs[] = mQuantization.process(dwtCoefs, imageFilename);
			
		if (doReconstruct)
			if (decodedCoefs != null) {
				IWT dwt = new IWT(method);
			
				simpleReconstruct(dwt, imageData.getFilename(), imageData.width, imageData.height, true, decodedCoefs);
			} else {
				Log.getInstance().log(Level.WARNING,
						"Reconstruction received empty image coefs");
			}
	}


	 
	private IWTCoefficients[] decomposeImage(boolean doLogCoefs, ImageDto imageData, WaveletTransform transform) {
		// start Haar decomposition
		IWT dwt = new IWT(transform);
		Log.getInstance().log(
				Level.FINE,
				"\n" + dwt.getTranformation().getCaption()
						+ ": \n\tHor\t\tVer\t\tDiag\t\t\tAverage");
		IWTCoefficients[] coefs = dwt.decompose(
				new Matrix[] {
					new Matrix(imageData.pixelsR), 
					new Matrix(imageData.pixelsG),
					new Matrix(imageData.pixelsB)}, 
					true, doLogCoefs, mDecompLevels);
		return coefs;
	}

	private void simpleReconstruct(IWT dwt, String imageFilename, int w, int h, boolean isHuffman, IWTCoefficients... coef) {
		Log.getInstance().log(Level.FINE,
				"\nReconstruction attempt.. (" + imageFilename + ")");

		Matrix reconstR = dwt.reconstruct(coef[0]);
		Matrix reconstG = dwt.reconstruct(coef[1]);
		Matrix reconstB = dwt.reconstruct(coef[2]);

		ImageDto reconstImage = new ImageDto(reconstR.get(),
				reconstG.get(), reconstB.get(), w, h);
		

		reconstImage.saveToImageFile(imageFilename, mOutputFormat);	
	}




}
