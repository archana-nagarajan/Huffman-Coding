

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FourWayCacheOptimizedHeap {
	
	private static final int children=4;
	List<HeapNode> heap = new ArrayList<HeapNode>();
	String str="";
	
	public void printHeap(){
		System.out.println("************ Print Heap***********");
		for(HeapNode item : heap){
			if(item==null){
				continue;
			}
			System.out.println(item.getData()+":"+item.getFrequency());
		}
	}
	
	public void buildHeap(Map<Integer,Integer> map){
		heap.add(null);
		heap.add(null);
		heap.add(null);
		for (Entry<Integer, Integer> entry : map.entrySet()){
	    	HeapNode hp=new HeapNode(entry.getKey(), entry.getValue(),null,null);
	    	insert(hp);
		}
	}
	
	public int getParent(int child){
		return (child/children)+2;
	}
	
	public int getNthChild(int parent, int n){
		return (children*(parent-2)+n)-1;
	}
	
	public void insert(HeapNode element){
		heap.add(element);
		heapifyInsert(heap.size()-1);
	}
	
	public void heapifyInsert(int value){
		HeapNode newElem=heap.get(value);
		int parent=getParent(value);
		while(parent>2 && newElem.getFrequency()<heap.get(parent).getFrequency()){
		   	HeapNode swapNode=newElem;
        	heap.set(value, heap.get(parent));
        	heap.set(parent, swapNode);
			newElem=heap.get(parent);
			value=parent;
			parent=getParent(parent);
		}
	}
	
	public boolean isEmpty(){
		return heap.size()==3;
    }
	
	public HeapNode removeMin(){ 
		HeapNode removeItem = heap.get(3);
		heap.set(3, heap.get(heap.size()-1));
		heap.remove(heap.size()-1);
		if(!isEmpty()){
			heapifyRemoveMin(3);
		}
		return removeItem;
    }
	
	private void heapifyRemoveMin(int minIndex){
		int child;
		int newElemFreq = heap.get(minIndex).getFrequency();
		int firstChildIndex=getNthChild(minIndex, 1);
		while (firstChildIndex < heap.size()){
            child = getMinChild(minIndex);
            if (heap.get(child).getFrequency() < newElemFreq){
            	HeapNode swapNode=heap.get(child);
            	heap.set(child, heap.get(minIndex));
            	heap.set(minIndex, swapNode);
            }
            else{
                break;
            }
            minIndex = child;
            firstChildIndex=getNthChild(minIndex, 1);
        }
    }
	
	public int getMinChild(int minIndex){    
		int minChildIndex = getNthChild(minIndex, 1);
		int nextChildIndex = 2;
		int minChildPos = getNthChild(minIndex, nextChildIndex);
		while (nextChildIndex <= children && minChildPos < heap.size()){
			if (heap.get(minChildPos).getFrequency() < heap.get(minChildIndex).getFrequency()){ 
				minChildIndex = minChildPos;
			}
			minChildPos = getNthChild(minIndex, nextChildIndex++);
		}    
		return minChildIndex;
	}
	
	public HeapNode constructHuffmanTree(){
		HeapNode newNode=null;
		while(heap.size()>4){
			HeapNode node1=removeMin();
			HeapNode node2=removeMin();
			newNode=new HeapNode(-1,node1.getFrequency()+node2.getFrequency(),node1,node2);
			insert(newNode);
		}
		HeapNode rootNode=removeMin();
		heap.clear();
		return rootNode;
	}
	
	public void traverseHuffmanTree(HeapNode root, String item, Map<String,String>codeTableMap){
        if(root.getLeft()==null && root.getRight()==null){
        	codeTableMap.put(String.valueOf(root.getData()),item);
        }
        else{
            if(root.getLeft()!=null){
            	traverseHuffmanTree(root.getLeft(),item+0,codeTableMap);
            }
            if(root.getRight()!=null){
            	traverseHuffmanTree(root.getRight(),item+1,codeTableMap);
            }
        }
    }

	void inorder(HeapNode root){
        if(root!=null) {
            inorder(root.getLeft());
            System.out.println(root.getData() + ":"+ root.getFrequency());
            inorder(root.getRight());
        }
    }
}