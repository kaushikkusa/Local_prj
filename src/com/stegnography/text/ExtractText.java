package com.stegnography.text;

import java.math.BigInteger;

public class ExtractText {

	public String getBinaryString(String text) {
		String binary = new BigInteger(text.getBytes()).toString(2);

		if (binary.length() % 2 != 0)
			binary = "0" + binary;

		return binary;
	}

}
