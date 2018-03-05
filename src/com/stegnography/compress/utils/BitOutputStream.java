package com.stegnography.compress.utils;

import java.io.IOException;
import java.io.OutputStream;
 
public class BitOutputStream {
	
	private OutputStream iOs;
 
	
	private int iBuffer;
 
	
	private int iBitCount;
 
	
	public BitOutputStream(OutputStream aOs)
	{
		iOs = aOs;
	}
 
	/**
	 * Write a single bit to the stream. It will only be flushed
	 * to the underlying OutputStream when a byte has been 
	 * completed or when flush() manually.
	 * @param aBit 1 if the bit should be set, 0 if not
	 * @throws IOException
	 */
	synchronized public void writeBit(int aBit) throws IOException
	{
		if (iOs == null)
			throw new IOException("Already closed");
 
		if (aBit != 0 && aBit != 1)
		{
			throw new IOException(aBit + " is not a bit");
		}
 
		iBuffer |= aBit << iBitCount;
		iBitCount++;
 
		if (iBitCount == 8)
		{
			flush();
		}
	}
 
	/**
	 * Write the current cache to the stream and reset
	 * the buffer.
	 * @throws IOException
	 */
	public void flush() throws IOException
	{
		if (iBitCount > 0)
		{
			iOs.write((byte) iBuffer);
			iBitCount = 0;
			iBuffer = 0;
		}
	}
 
	/**
	 * Flush the data and close the underlying output stream.
	 * @throws IOException
	 */
	public void close() throws IOException
	{
		flush();
		iOs.close();
		iOs = null;
	}
 
	/**
	 * Write the specified number of bits from the int value
	 * to the stream. Correspondig to the InputStream,
	 * the bits are written starting at the highest bit 
	 * ( >> aNumberOfBits ), going down to the lowest bit ( >> 0 ).
	 * @param aValue the int containing the bits that should
	 * be written to the stream.
	 * @param aNumBits how many bits of the integer should
	 * be written to the stream.
	 * @throws IOException
	 */
	synchronized public void writeBits(final int aValue, final short aNumBits) 
            throws IOException
	{
		for (int i = aNumBits - 1; i >= 0; i--)
		{
			writeBit((aValue >> i) & 0x01);
		}
	}
 
	/**
	 * Calculate how many bits are needed to store the specified
	 * value. Can be used to optimize data transfer.
	 * @param aMaxValue the value that you want to test.
	 * @return how many bits the specified value needs to be
	 * stored.
	 */
	public static int getRequiredNumOfBits(final int aMaxValue)
	{
		// 0 still requires 1 bit to write it.
		if (aMaxValue == 0)
			return 1;
 
		// Go from left to right and search for the first 1
		// 00011010
		//    |---- First 1
		int curBit;
		for (curBit = 31; curBit >= 0; curBit--)
		{
			if ((aMaxValue & (0x01 << curBit)) > 0)
			{
				// Found first bit that is not null - max. value
				break;
			}
		}
		return curBit + 1;
 
		// Real maximum value is everything filled with 1 from this point on.
		// 00011111
		//int maxVal = powOf2(curBit + 1) - 1;
	}
 
}