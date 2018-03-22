package com.stegnography.algorithm;

public class LSBAlgorithm {

	public float[][] embedding( float[][] bufferedImage,  String message,  int offset, int width, int height)
			throws Exception {
		 float[][] image = startEmbedding(bufferedImage, message, offset, width, height);
		return image;
	}

	public String extract( float[][] bufferedImage,  int startingOffset, int width, int height)
			throws Exception {
		System.out.println("Applying LSB extraction");
		byte[] decode = null;
		try {
			decode = decodeText(bufferedImage, startingOffset, height);
			 String decodedMessage = new String(decode);
			return decodedMessage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private float[][] startEmbedding(float[][] image,  String text,  int offset, int width, int height)
			throws Exception {
		 byte msg[] = text.getBytes();
		 byte len[] = convert(msg.length);
		try {
			image = encodeText(image, len, offset, width, height);
			image = encodeText(image, msg, offset + 32, width, height);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return image;
	}

	private byte[] convert( int number) {
	 	 System.out.println("Converting "+number+" to 4 byte ");
	 	 System.out.println("The unsigned right shift operator  shifts a zero into the leftmost position");
		 byte byte3 = (byte) ((number & 0xFF000000) >>> 24);
		 byte byte2 = (byte) ((number & 0x00FF0000) >>> 16);
		 byte byte1 = (byte) ((number & 0x0000FF00) >>> 8);
		 byte byte0 = (byte) ((number & 0x000000FF));
		return (new byte[] { byte3, byte2, byte1, byte0 });
	}

	private float[][] encodeText( float[][] image,  byte[] addition,  int offset, int width, int height)
			throws Exception {

		System.out.println("Encoding text into image");
		int number = offset / height;
		int temp = offset % height;

		if ((width * height) >= (addition.length * 8 + offset)) {
			for ( byte add : addition) {
				for (int bit = 7; bit >= 0; --bit) {
					 int imageValue = (int) image[number][temp];

					int b = (add >>> bit) & 1;
					 int imageNewValue = ((imageValue & 0xFFFFFFFE) | b);

					image[number][temp] = imageNewValue;
					System.out.println("Converted value "+image[number][temp]+" to "+imageNewValue);

					if (temp < (height - 1)) {
						++temp;
					} else {
						++number;
						temp = 0;
					}
				}

			}
		} else {
			System.out.println("Error");
		}

		return image;
	}

	private byte[] decodeText( float[][] image,  int startingOffset, int height) {
		 int offset = startingOffset + 32;
		int length = 0;

		for (int number = startingOffset; number < offset; ++number) {
			 int h = number / height;
			 int w = number % height;

			 int imageValue = (int) image[h][w];
			 System.out.println("Converted value "+image[h][w]+" to "+imageValue);

			length = (length << 1) | (imageValue & 1);
		}

		byte[] result = new byte[length];

		int number = offset / height;
		int temp = offset % height;

		for (int letter = 0; letter < length; ++letter) {
			for (int bit = 7; bit >= 0; --bit) {
				 int imageValue = (int) image[number][temp];

				result[letter] = (byte) ((result[letter] << 1) | (imageValue & 1));

				if (temp < (height - 1)) {
					++temp;
				} else {
					++number;
					temp = 0;
				}
			}
		}

		return result;
	}
	
	public static void test(Object object1,Object object2){
		System.out.println(String.valueOf(object1));
		System.out.println(String.valueOf(object2));
	}
	
	public static void main(String[] args) {
		
		test(1, 2);
		test("abc","def");
	}
}
