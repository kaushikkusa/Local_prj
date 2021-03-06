package com.stegnography.dwt.wavelets;

public class VCBand implements WaveletTransform {
	
	public String getCaption(){return "VCBand";}
	
	@Override
	public float [] perform(float [] coef){
		float a,h,v,d;
		
		a = (coef[0]+coef[1]+coef[2]+coef[3])/4;
	
		v = (coef[0]-coef[1]+coef[2]-coef[3]);

		h = (coef[0]-coef[2]);
		
		d = (coef[1]-coef[3]);
		return new float []{a,v,h,d};
	}

	public float [] inverse(float [] coef){
		float c1,c2,c3,c4;
		float a,v,h,d;
		a = coef[0];
		v = coef[1];
		h = coef[2];
		d = coef[3];
		c1=a+(2*h+v)/4;
		c2=a+(2*d-v)/4;
		c3=a+(v-2*h)/4;
		c4=a-(v+2*d)/4;
		return new float[]{c1,c2,c3,c4};
	}
	@Override
	public int getLength() {
		return 2;
	}
}