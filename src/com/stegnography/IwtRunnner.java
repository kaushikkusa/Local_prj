

package com.stegnography;

import java.util.logging.Level;

import com.stegnography.dwt.wavelets.ACBand;
import com.stegnography.utils.Log;

public class IwtRunnner {

	private static String filename = "frames/frame2.jpg";

	public static void main(String[] args) {

		Level logLevel = Level.SEVERE;
		Log.getInstance().setLevel(logLevel);

		int wtLevel = 1;
		int quantLevels = 32;
		Class wavelet = ACBand.class;

		WavTransformation m = new WavTransformation(wtLevel, quantLevels, wavelet);

		m.decomposeImage(filename);

	}

}
