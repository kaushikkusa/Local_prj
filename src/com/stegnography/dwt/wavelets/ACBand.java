package com.stegnography.dwt.wavelets;

public class ACBand implements WaveletTransform {
	private WaveletTransform [] transformations; 
	public ACBand(){
		transformations = new WaveletTransform[] {
			new HaarAdaptive(),
			new VCBand(),
			new HCBand(),
			new DCBand()
		};
	}
	public String getCaption(){return "ACBand";}
	
	float [] minCoef,coefBuf;
	double minNorm, norm;
	int minTranID;
	@Override
	public float[] perform(float[] inCoef) {
		minTranID = 0;
		minNorm = getSquareSum((minCoef = transformations[0].perform(inCoef)));
		for (int i=1;i<transformations.length;i++){
			norm = getSquareSum((coefBuf = transformations[i].perform(inCoef)));
			if (norm < minNorm){
				minNorm = norm;
				minCoef = coefBuf;
				minTranID = i;
			}
		}
		return new float[] {minCoef[0],minCoef[1],minCoef[2],minCoef[3],minTranID};
	}
	
	/**
	 * coefs [a,v,h,d,t]
	 * t - transformation id
	 */
	public float [] inverse(float [] coef) throws ArrayIndexOutOfBoundsException{
		return inverse(coef, (byte)Math.round(coef[4]));
	}
	/**
	 * 
	 * @param tranform
	 * 	0 - HaarClassic(),
	 *	1 - HaarVertical(),
	 *	2 - HaarHorizotal(),
	 *	3 - HaarDiagonal() 
	 * @return
	 */
	public float [] inverse(float [] coef, byte tranform){
		return transformations[tranform].inverse(coef);
	}
	
	/**
	 * @param q coefs array
	 * @return sum of squares of 2nd, 3rd, 4th term 
	 */
	private double getSquareSum(float [] q){
		return q[1]*q[1]+q[2]*q[2]+q[3]*q[3];
	}
	@Override
	public int getLength() {
		return 2;
	}

}
