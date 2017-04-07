
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Heaps {
	
	encoder en=new encoder();
	public static void main(String args[]){
		Map<Integer,Integer> map=new HashMap<Integer,Integer>();
		try(BufferedReader br = new BufferedReader(new FileReader("/Users/archana.nagarajan/Documents/UF/SEM2/ADS/Project/sample2/sample_input_large.txt"))){
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
		for(int i=0;i<30;i++){
			h.constructBinaryHeap(map);
		}
		long lEndTime = System.nanoTime();
		long output = lEndTime - lStartTime;
		System.out.println("Elapsed time in milliseconds: BinaryHeap " + output / 1000000);
		lStartTime = System.nanoTime();
		for(int i=0;i<30;i++){
			h.constructFourWayCacheOptimizedHeap(map);
		}
		lEndTime = System.nanoTime();
		output = lEndTime - lStartTime;
		System.out.println("Elapsed time in milliseconds: FourWayCacheOptimizedHeap " + output / 1000000);
		lStartTime = System.nanoTime();
		for(int i=0;i<30;i++){
			h.constructPairingHeap(map);
		}
		lEndTime = System.nanoTime();
		output = lEndTime - lStartTime;
		System.out.println("Elapsed time in milliseconds: PairingHeap " + output / 1000000);
	}

	private void constructFourWayCacheOptimizedHeap(Map<Integer, Integer> map) {
		Map<String,String>codeTableMap=new HashMap<String,String>();
		String encodedString="";
		FourWayCacheOptimizedHeap fwh=new FourWayCacheOptimizedHeap();
		HeapNode root=null;
		fwh.buildHeap(map);
		root=fwh.constructHuffmanTree();
//		fwh.inorder(root);
	//	fwh.traverseHuffmanTree(root,fwh.str,codeTableMap);
//		encodedString=en.encodeData(codeTableMap);
//		System.out.println(encodedString);
//		en.writeEncodedDataToFile(encodedString);
//		en.writeCodeTableToFile(codeTableMap);
	}

	private void constructBinaryHeap(Map<Integer,Integer> map) {
		Map<String,String>codeTableMap=new HashMap<String,String>();
		String encodedString="";
		BinaryHeap bh=new BinaryHeap();
		HeapNode root=null;
		bh.buildHeap(map);
		root=bh.constructHuffmanTree();
//		bh.traverseHuffmanTree(root,bh.str,codeTableMap);
//		encodedString=en.encodeData(codeTableMap);
//		en.writeEncodedDataToFile(encodedString);
//		en.writeCodeTableToFile(codeTableMap);
	}
	
	private void constructPairingHeap(Map<Integer,Integer> map) {
		PairingHeap ph=new PairingHeap();
		HeapNode root=null;
		ph.buildHeap(map);
		root=ph.constructHuffmanTree();
	}
}
