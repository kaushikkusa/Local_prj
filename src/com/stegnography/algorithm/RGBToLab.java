package com.stegnography.algorithm;

import com.stegnography.model.LAB;

public class RGBToLab {

	public static LAB fromRGB(int red, int green, int blue, double quote) {
		System.out.println("Converting RGB TO LAB");
		System.out.println("RED "+red+" GREEN "+green+" BLUE "+blue);
		double shortandR = red / 255.0;
		double shortandG = green / 255.0;
		double shortandB = blue / 255.0;

		double d50x = 0.950470, d50y = 1.0, d50z = 1.088830;

		shortandR = shortandR <= 0.04045 ? shortandR / 12.92 : Math.pow((shortandR + 0.055) / 1.055, 2.4);
		shortandG = shortandG <= 0.04045 ? shortandG / 12.92 : Math.pow((shortandG + 0.055) / 1.055, 2.4);
		shortandB = shortandB <= 0.04045 ? shortandB / 12.92 : Math.pow((shortandB + 0.055) / 1.055, 2.4);

		double x = (0.4124564 * shortandR + 0.3575761 * shortandG + 0.1804375 * shortandB) / d50x,
				y = (0.2126729 * shortandR + 0.7151522 * shortandG + 0.0721750 * shortandB) / d50y,
				z = (0.0193339 * shortandR + 0.1191920 * shortandG + 0.9503041 * shortandB) / d50z;

		x = x > 0.008856 ? Math.pow(x, 1.0 / 3) : 7.787037 * x + 4.0 / 29;
		y = y > 0.008856 ? Math.pow(y, 1.0 / 3) : 7.787037 * y + 4.0 / 29;
		z = z > 0.008856 ? Math.pow(z, 1.0 / 3) : 7.787037 * z + 4.0 / 29;

		double L = 116 * y - 16, A = 500 * (x - y), B = 200 * (y - z);

		if (quote > 0) {
			L = quote * Math.floor(L / quote);
			A = quote * Math.floor(A / quote);
			B = quote * Math.floor(B / quote);
		}
		System.out.println("Converted L "+L+" A "+A+" B"+B);
		return new LAB(L, A, B);
	}
	
	public static void main(String[] args) {
	
		fromRGB(100, 100, 100, 10);
	}
}
