package com.stegnography.compress.utils;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
 

public class BitInputStream {
	/**
	 * The Java InputStream this class is working on.
	 */
	private InputStream iIs;

	private int iBuffer;
 
	
	private int iNextBit = 8;
 
	
	public BitInputStream(InputStream aIs)
	{
		iIs = aIs;
	}
 
	
	synchronized public int readBits(final short aNumberOfBits) 
            throws IOException
	{
		int value = 0;
		for (int i = aNumberOfBits - 1; i >= 0; i--)
		{
			value |= (readBit() << i);
		}
		return value;
	}
 
	
	synchronized public int readBit() throws IOException
	{
		
		if (iIs == null)
			throw new IOException("Already closed");
 
		if (iNextBit == 8)
		{
			iBuffer = iIs.read();
 
			if (iBuffer == -1)
				throw new EOFException();
 
			iNextBit = 0;
		}
 
		int bit = iBuffer & (1 << iNextBit);
		iNextBit++;
 
		bit = (bit == 0) ? 0 : 1;
 
		return bit;
	}
 
	
	public void close() throws IOException
	{
		iIs.close();
		iIs = null;
	}
}