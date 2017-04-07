

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

public class encoder {

	public void writeCodeTableToFile(Map<String, String> codeTableMap) {
		FileOutputStream fos = null;
		File file;
		StringBuffer sb=new StringBuffer();
		try {

			file = new File("/Users/archana.nagarajan/Documents/UF/SEM2/ADS/Project/sample1/output/code_table.txt");
			fos = new FileOutputStream(file);

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			for (Entry<String, String> entry : codeTableMap.entrySet()){
				sb.append(entry.getKey()+" "+entry.getValue());
				sb.append("\n");
			}
			byte[] contentInBytes = sb.toString().getBytes();

			fos.write(contentInBytes);
			fos.flush();
			fos.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void writeEncodedDataToFile(String encodedString) {
		FileOutputStream fos = null;
		File file;
		try {

			file = new File("/Users/archana.nagarajan/Documents/UF/SEM2/ADS/Project/sample1/output/encoded.bin");
			fos = new FileOutputStream(file);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			
			StringBuilder encodedDataSB = new StringBuilder(encodedString);
	        while (encodedDataSB.length() % 8 != 0) {
	        	encodedDataSB.append("0"); // lets add some extra bits until we have full bytes
	        }
	        encodedString =encodedDataSB.toString();
	        for (int i = 0; i < encodedString.length(); i += 8) {
		        String byteString = encodedString.substring(i, i + 8); // grab a byte
		        int parsedByte = 0xFF & Integer.parseInt(byteString, 2);
		        fos.write(parsedByte); // write a byte
		    }

			fos.flush();
			fos.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String encodeData(Map<String,String>codeMap) {
		StringBuilder sb=new StringBuilder();
		try(BufferedReader br = new BufferedReader(new FileReader("/Users/archana.nagarajan/Documents/UF/SEM2/ADS/Project/sample1/sample_input_small.txt"))){
		    String line = br.readLine();
		    while (line!=null && !(line.isEmpty())) {
		    	if(codeMap.containsKey(line)){
		    		sb.append(codeMap.get(line));
		    	}
		        line = br.readLine();
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
