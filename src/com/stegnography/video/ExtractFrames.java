package com.stegnography.video;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.stegnography.LogData;

public class ExtractFrames {

	public void extractframes(String filename, String ext) {

		LogData.printData("Extracting frames from video file");
		String command = "ffmpeg -y -i " + filename + " frames/frame%001d." + ext;
		try {
			Process process = Runtime.getRuntime().exec(command);
			String line;

			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((line = input.readLine()) != null) {
				LogData.printData(line);
			}

			BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			while ((line = error.readLine()) != null) {
				LogData.printData(line);
			}

			input.close();

			Thread.sleep(7000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
