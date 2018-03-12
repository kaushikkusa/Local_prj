package com.stegnography.algorithm;

import com.stegnography.utils.Constants;

public class LSBAlgorithm {

	public int[][] embedding(final int[][] bufferedImage, final String message, final int offset, int width, int height)
			throws Exception {
		final int[][] image = startEmbedding(bufferedImage, message, offset, width, height);
		return image;
	}

	public String extract(final int[][] bufferedImage, final int startingOffset, int width, int height)
			throws Exception {
		byte[] decode = null;
		try {
			decode = decodeText(bufferedImage, startingOffset, height);
			final String decodedMessage = new String(decode);
			return decodedMessage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private int[][] startEmbedding(int[][] image, final String text, final int offset, int width, int height)
			throws Exception {
		final byte msg[] = text.getBytes();
		final byte len[] = bitConversion(msg.length);
		try {
			image = encodeText(image, len, offset, width, height);
			image = encodeText(image, msg, offset + Constants.HIDDEN_MESSAGE_BIT_LENGTH, width, height);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return image;
	}

	private byte[] bitConversion(final int i) {
		final byte byte3 = (byte) ((i & 0xFF000000) >>> 24);
		final byte byte2 = (byte) ((i & 0x00FF0000) >>> 16);
		final byte byte1 = (byte) ((i & 0x0000FF00) >>> 8);
		final byte byte0 = (byte) ((i & 0x000000FF));
		return (new byte[] { byte3, byte2, byte1, byte0 });
	}

	private int[][] encodeText(final int[][] image, final byte[] addition, final int offset, int width, int height)
			throws Exception {

		int i = offset / height;
		int j = offset % height;

		if ((width * height) >= (addition.length * 8 + offset)) {
			for (final byte add : addition) {
				for (int bit = 7; bit >= 0; --bit) {
					final int imageValue = image[i][j];

					int b = (add >>> bit) & 1;
					final int imageNewValue = ((imageValue & 0xFFFFFFFE) | b);

					image[i][j] = imageNewValue;

					if (j < (height - 1)) {
						++j;
					} else {
						++i;
						j = 0;
					}
				}

			}
		} else {
			System.out.println("Error");
		}

		return image;
	}

	private byte[] decodeText(final int[][] image, final int startingOffset, int height) {
		final int offset = startingOffset + Constants.HIDDEN_MESSAGE_BIT_LENGTH;
		int length = 0;

		for (int i = startingOffset; i < offset; ++i) {
			final int h = i / height;
			final int w = i % height;

			final int imageValue = image[h][w];
			length = (length << 1) | (imageValue & 1);
		}

		byte[] result = new byte[length];

		int i = offset / height;
		int j = offset % height;

		for (int letter = 0; letter < length; ++letter) {
			for (int bit = 7; bit >= 0; --bit) {
				final int imageValue = image[i][j];

				result[letter] = (byte) ((result[letter] << 1) | (imageValue & 1));

				if (j < (height - 1)) {
					++j;
				} else {
					++i;
					j = 0;
				}
			}
		}

		return result;
	}
}
