package com.stegnography;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import com.stegnography.compress.ImageCompressor;
import com.stegnography.dwt.wavelets.ACBand;
import com.stegnography.dwt.wavelets.IWT;
import com.stegnography.dwt.wavelets.IWTCoefficients;
import com.stegnography.dwt.wavelets.Matrix;
import com.stegnography.dwt.wavelets.WaveletTransform;
import com.stegnography.image.ImageDto;
import com.stegnography.image.ImageReader;
import com.stegnography.model.IWTContent;
import com.stegnography.utils.Constants;
import com.stegnography.utils.Log;

public class WavTransformation {
	private int mDecompLevels = 1;
	private int mQuantizLevels = 32;
	private boolean doReconstruct;
	private Class classWaveletTransform;
	private String mOutputFormat = Constants.extBMP;
	private boolean toCopyOriginImageToResults = true;

	public void setOutputFormat(String mOutputFormat) {
		this.mOutputFormat = mOutputFormat;
	}

	WavTransformation() {
		doReconstruct = true;
		classWaveletTransform = ACBand.class;

		new File(Constants.resultsFolder).mkdirs();
		new File(Constants.resultsFolder, Constants.picsFolder).mkdirs();
		new File(Constants.resultsFolder, Constants.resultsDebugDataFolder).mkdirs();
	}

	public IWTContent decomposeImage(String filename) {
		ImageReader ia = new ImageReader();
		IWTContent iwtContent = new IWTContent();
		ImageDto imageData = null;
		try {
			imageData = ia.readImageFile(filename);
			Log.getInstance().log(Level.FINE, "Image from file " + filename + " was read(w=" + imageData.width + ", h="
					+ imageData.height + ").");
			// System.out.println("Image from file "+filename+" was
			// read(w="+imageData.width+", h="+imageData.height+").");

			// if (toCopyOriginImageToResults)
			// imageData.saveToImageFile(filename, mOutputFormat);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		final boolean logCoefsToFile = true;

		IWTCoefficients[] dwtCoefs;

		WaveletTransform method = null;
		try {
			method = (WaveletTransform) classWaveletTransform.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		dwtCoefs = decomposeImage(logCoefsToFile, imageData, method);

		iwtContent.setDwtCoefs(dwtCoefs);
		iwtContent.setImageData(imageData);

		return iwtContent;

	}

	private IWTCoefficients[] decomposeImage(boolean doLogCoefs, ImageDto imageData, WaveletTransform transform) {
		// start Haar decomposition
		IWT dwt = new IWT(transform);
		Log.getInstance().log(Level.FINE,
				"\n" + dwt.getTranformation().getCaption() + ": \n\tHor\t\tVer\t\tDiag\t\t\tAverage");
		IWTCoefficients[] coefs = dwt.decompose(new Matrix[] { new Matrix(imageData.pixelsR),
				new Matrix(imageData.pixelsG), new Matrix(imageData.pixelsB) }, true, doLogCoefs, mDecompLevels);
		return coefs;
	}

	public void reconstruct(IWTContent dwData) {
		WaveletTransform method = null;
		try {
			method = (WaveletTransform) classWaveletTransform.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		reconstruct(new IWT(method), dwData.getImageData().getFilename(), dwData.getImageData().width,
				dwData.getImageData().height, false, dwData.getDwtCoefs());

	}

	private void reconstruct(IWT dwt, String imageFilename, int w, int h, boolean isHuffman, IWTCoefficients... coef) {
		Log.getInstance().log(Level.FINE, "\nReconstruction attempt.. (" + imageFilename + ")");

		Matrix reconstR = dwt.reconstruct(coef[0]);
		Matrix reconstG = dwt.reconstruct(coef[1]);
		Matrix reconstB = dwt.reconstruct(coef[2]);

		ImageDto reconstImage = new ImageDto(reconstR.get(), reconstG.get(), reconstB.get(), w, h);

		reconstImage.saveToImageFile(imageFilename, mOutputFormat);

	}

}
