

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class decoder {

	HeapNode root=new HeapNode(-1);
	public static void main(String args[]){
		Map<String,String> codeMap= new HashMap<String,String>();
		List<String> decodedMessage = new ArrayList<>();
		decoder htd= new decoder();
		String codeTable="/Users/archana.nagarajan/Documents/UF/SEM2/ADS/Project/sample1/output/code_table.txt";
		String encodedBin="/Users/archana.nagarajan/Documents/UF/SEM2/ADS/Project/sample1/output/encoded.bin";
		try(BufferedReader br = new BufferedReader(new FileReader(codeTable))){
		    String line = br.readLine();
		    while (line != null) {
		    	String value[]=line.split(" ");
		    	codeMap.put(value[0], value[1]);
		        line = br.readLine();
		    }
		    long lStartTime = System.nanoTime();
			htd.reconstructHuffmanTree(codeMap);
			decodedMessage=htd.decodeMessage(encodedBin);
			htd.writeDecodedMessageToFile(decodedMessage);
			long lEndTime = System.nanoTime();
			long output = lEndTime - lStartTime;
			System.out.println("Elapsed time in milliseconds: Decoding and writing to file " + output / 1000000);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private List<String> decodeMessage(String encodedBin) {
		String encodedMessage=readEncodedMessage(encodedBin);
		List<String> decodedMessage = new ArrayList<>();
        HeapNode temp = root;
        for(char c :encodedMessage.toCharArray()) {
        	if(c=='0'){
        		temp=temp.getLeft();
        	}
        	else{
        		temp=temp.getRight();
        	}
            if (temp.getData()!=-1){
               decodedMessage.add(temp.getData()+"");
                temp = root;
            }
        }
        return decodedMessage;
	}
	
	private void writeDecodedMessageToFile(List<String> decodedMessage) {
		FileOutputStream fos = null;
		File file;
		StringBuffer sb=new StringBuffer();
		try {

			file = new File("/Users/archana.nagarajan/Documents/UF/SEM2/ADS/Project/sample1/output/decoded.txt");
			fos = new FileOutputStream(file);

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			for (String item : decodedMessage){
				sb.append(item);
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

	private String readEncodedMessage(String encodedBin) {
		StringBuilder encodedDataString = new StringBuilder();
        Path path = Paths.get(encodedBin);
        try {
            byte[] bytes = Files.readAllBytes(path);
            for (byte b : bytes) {
                int i = b;
                String s = Integer.toBinaryString(i);
                if(i<0)
                    i = 256+i;
                s = Integer.toBinaryString(i);
                int diff = 8-s.length();
                for(int l = 0; l<diff;l++)
                    s="0"+s;
                encodedDataString.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encodedDataString.toString();
	}

	void inorder(HeapNode root){
        if(root!=null) {
            inorder(root.getLeft());
            System.out.println(root.getData() + ":"+ root.getFrequency());
            inorder(root.getRight());
        }
    }

	private HeapNode reconstructHuffmanTree(Map<String, String> codeMap) {
		HeapNode node=root;
		for (Entry<String, String> entry : codeMap.entrySet()){
			String key=entry.getKey();
			String value=entry.getValue();
			char ch[]=value.toCharArray();
			for(int i=0;i<ch.length;i++){
				switch (ch[i]){
                case '0':
                    if(node.getLeft()==null){
                    	node.setLeft(new HeapNode(-1));
                    }
                    node = node.getLeft();
                    break;
                case '1':
                    if(node.getRight()==null){
                    	node.setRight(new HeapNode(-1));
                    }
                    node = node.getRight();
                    break;
	            }
			}
			node.setData(Integer.parseInt(key));
            node = root;
		}
	//	inorder(root);
		return node;
	}
}
