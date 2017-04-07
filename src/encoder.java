
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

public class encoder {
	static String filePath="";
	public static void main(String args[]){
		Map<Integer,Integer> map=new HashMap<Integer,Integer>();
		filePath=args[0];
		try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
		    String line = br.readLine();
		    while (line!=null && !(line.isEmpty())) {
		    	int value=Integer.parseInt(line);
		    	if(map.containsKey(value)){
		    		map.put(value,map.get(value)+1);
		    	}
		    	else{
		    		map.put(value, 1);
		    	}
		        line = br.readLine();
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Heaps h=new Heaps();
		long lStartTime = System.nanoTime();
		h.constructBinaryHeap(map);
		long lEndTime = System.nanoTime();
		long output = lEndTime - lStartTime;
	//	System.out.println("Elapsed time in milliseconds: BinaryHeap " + output / 1000000);
		// lStartTime = System.nanoTime();
		//  for(int i=0;i<10;i++){
		// 	h.constructFourWayCacheOptimizedHeap(map);
		// }
		// lEndTime = System.nanoTime();
		// output = lEndTime - lStartTime;
		// System.out.println("Elapsed time in milliseconds: FourWayCacheOptimizedHeap " + output / 1000000);
		// lStartTime = System.nanoTime();
		// for(int i=0;i<10;i++){
		// 	h.constructPairingHeap(map);
		// }
		// lEndTime = System.nanoTime();
		// output = lEndTime - lStartTime;
		// System.out.println("Elapsed time in milliseconds: PairingHeap " + output / 1000000);
		}


	public void writeCodeTableToFile(Map<String, String> codeTableMap) {
		FileOutputStream fos = null;
		File file;
		StringBuffer sb=new StringBuffer();
		try {

			file = new File("code_table.txt");
			fos = new FileOutputStream(file);

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			for (Entry<String, String> entry : codeTableMap.entrySet()){
				sb.append(entry.getKey()+" "+entry.getValue()+"\n");
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
		StringBuffer sb=new StringBuffer();
		try {

			file = new File("encoded.bin");
			fos = new FileOutputStream(file);

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			StringBuilder encodedDataSb = new StringBuilder(encodedString);
	        while (encodedDataSb.length() % 8 != 0) {
	            encodedDataSb.append("0"); // lets add some extra bits until we have full bytes
	            encodedString =encodedDataSb.toString();
	        }

			for (int i = 0; i < encodedString.length(); i += 8) {
		        String byteString = encodedString.substring(i, i + 8); // grab a byte
		        int parsedByte = 0xFF & Integer.parseInt(byteString, 2);
		        fos.write(parsedByte); // write a byte
		    }
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
		try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
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
