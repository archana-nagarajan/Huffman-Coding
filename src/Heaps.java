

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Heaps {

	encoder en=new encoder();
	public void constructFourWayCacheOptimizedHeap(Map<Integer, Integer> map) {
		Map<String,String>codeTableMap=new HashMap<String,String>();
		String encodedString="";
		FourWayCacheOptimizedHeap fwh=new FourWayCacheOptimizedHeap();
		HeapNode root=null;
		fwh.buildHeap(map);
		root=fwh.constructHuffmanTree();
	}

	public void constructBinaryHeap(Map<Integer,Integer> map) {
		Map<String,String>codeTableMap=new HashMap<String,String>();
		String encodedString="";
		BinaryHeap bh=new BinaryHeap();
		HeapNode root=null;
		bh.buildHeap(map);
		root=bh.constructHuffmanTree();
		bh.traverseHuffmanTree(root,bh.str,codeTableMap);
		encodedString=en.encodeData(codeTableMap);
		en.writeEncodedDataToFile(encodedString);
		en.writeCodeTableToFile(codeTableMap);
	}

	public void constructPairingHeap(Map<Integer,Integer> map) {
		// PairingHeap ph=new PairingHeap();
		HeapNode root=null;
		// ph.buildHeap(map);
		// root=ph.constructHuffmanTree();
	}
}
