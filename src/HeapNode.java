

public class HeapNode {
	private int frequency;
	private int data;
	private HeapNode left;
	private HeapNode right;

	public HeapNode(int data,int frequency,HeapNode left, HeapNode right){
		this.frequency=frequency;
		this.data=data;
		this.left=left;
		this.right=right;
	}

	public HeapNode(int data){
		this.data=data;
	}
	public HeapNode getLeft() {
		return left;
	}
	public void setLeft(HeapNode left) {
		this.left = left;
	}
	public HeapNode getRight() {
		return right;
	}
	public void setRight(HeapNode right) {
		this.right = right;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}

	public int compareTo(HeapNode node){
	    int firstFreq = node.getFrequency();
	    if (firstFreq ==getFrequency())
	        return 0;
	    else if (firstFreq>getFrequency())
	        return -1;
	    else
	        return 1;
    }
}
