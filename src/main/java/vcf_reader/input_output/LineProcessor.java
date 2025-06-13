package vcf_reader.input_output;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public abstract class LineProcessor {

	public abstract void read(BufferedReader bufferedReader) throws IOException;
	
	public void process(File file) throws FileNotFoundException {
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			this.read(bufferedReader);
		} catch (IOException e) {
			throw new FileNotFoundException("There's been an unknown I/O error while reading the file.");
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
