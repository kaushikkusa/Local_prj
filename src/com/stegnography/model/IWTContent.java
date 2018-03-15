package com.stegnography.model;

import com.stegnography.dwt.wavelets.IWTCoefficients;
import com.stegnography.image.ImageDto;

public class IWTContent {

	IWTCoefficients[] dwtCoefs;

	ImageDto imageData;

	public IWTCoefficients[] getDwtCoefs() {
		return dwtCoefs;
	}

	public void setDwtCoefs(IWTCoefficients[] dwtCoefs) {
		this.dwtCoefs = dwtCoefs;
	}

	public ImageDto getImageData() {
		return imageData;
	}

	public void setImageData(ImageDto imageData) {
		this.imageData = imageData;
	}
}
