package com.stegnography.dwt.wavelets;

public interface WaveletTransform {
	/**
	 * @param coef
	 * @return array [a,v,h,d]
	 */
	public float [] perform(float [] coef);
	
	/**
	 * @param coef [a,v,h,d]
	 * @return array[4] ofreconstructed coefs  
	 */
	public float [] inverse(float [] coef);
	
	public String getCaption();
	public int getLength();

}
