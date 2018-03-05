package com.stegnography.image;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.stegnography.utils.Constants;

public class ImageReader {
	
	public ImageDto readImageFile(String filename) throws IOException{
		File file = new File(filename);
		BufferedImage bufferedImage = ImageIO.read(file);

		int w = bufferedImage.getWidth(null);
		int h = bufferedImage.getHeight(null);

		int[] rgbs = new int[w*h];
		bufferedImage.getRGB(0, 0, w, h, rgbs, 0, w);
		return new ImageDto(rgbs, w, h).setFilename(filename);
	}

	public ImageDto readImageCoefficients(String [] files){
		float [][] pixelsR = null;
		float [][] pixelsG = null;
		float [][] pixelsB = null;
		float [][] currentPixels;
		
		BufferedReader br = null;
		String line = null;
		String [] splitted;
		String color;
		int width=0, height=0;
		try {
			for (String file:files){
				br = new BufferedReader(new FileReader(Constants.resultsFolder+file));
				line = br.readLine();
				System.out.println("Reading file.. "+line);
				color = line.split(" ")[2];
				
				line = br.readLine(); // "\n"
				
				line = br.readLine();
				splitted = line.split(" ");
				if (Constants.propSize.equals(splitted[0]+" ")){
					splitted = splitted[1].split(Constants.propSizesDelimeter);
					width = Integer.parseInt(splitted[0]);
					height = Integer.parseInt(splitted[1]);
				} else {
					System.err.println("Wrong file format("+file.toString()+"), cannot find \""+Constants.propSize+"\" property");
					return null;
				}	
				currentPixels = new float [height][width];
				if (Constants.cRed.equals(color)){
					pixelsR = currentPixels;
				} else if (Constants.cGreen.equals(color)){
					pixelsG = currentPixels;
				} else if (Constants.cBlue.equals(color)){
					pixelsB = currentPixels;
				} else {
					System.err.println("Wrong file format, color read = "+color);
					return null;
				} 
				br.readLine(); // "{"
					
				int curRow = 0;
				while ( (line = br.readLine()) != null){
					if ("}".equals(line)) break;
					int curColumn = 0;
					splitted = line.split("\t");
					for (String s:splitted){
						currentPixels[curRow][curColumn++] = 
							Float.parseFloat(s);
					}
					curRow++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	return new ImageDto(pixelsR, pixelsG, pixelsB, width, height);		
	}
}
