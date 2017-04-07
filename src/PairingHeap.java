

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

public class PairingHeap {

    private PairingHeapNode [ ] treeArray = new PairingHeapNode[ 10 ];
    private PairingHeapNode root;
    private int size;


    public PairingHeap( )
    {
        root = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return  root == null;
    }

    public HeapNode peek() {
        return null;
    }


    public void add(HeapNode x) {
       PairingHeapNode newNode = new PairingHeapNode(x);
        if( root == null )
            root = newNode;
        else
            root = compareAndLink( root, newNode );
        size++;
    }

    public PairingHeapNode removeMin() {
        PairingHeapNode x = findMin();
        if( root.leftChild == null )
            root = null;
        else
            root = combineSiblings( root.leftChild );

        size--;
        return x;
    }

    public PairingHeapNode findMin( )
    {
        if( isEmpty( ) )
            throw new IllegalArgumentException( "Pairing heap is empty" );
        return root;
    }
    
    public HeapNode constructHuffmanTree(){
		HeapNode newNode=null;
		while(size>1){
			HeapNode node1=removeMin();
			HeapNode node2=removeMin();
			newNode=new HeapNode(-1,node1.getFrequency()+node2.getFrequency(),node1,node2);
			add(newNode);
		}
		return removeMin();
	}

    /**
     * Internal method that is the basic operation to maintain order.
     * Links first and second together to satisfy heap order.
     * @param first root of tree 1, which may not be null.
     *    first.nextSibling MUST be null on entry.
     * @param second root of tree 2, which may be null.
     * @return result of the tree merge.
     */
    private PairingHeapNode compareAndLink( PairingHeapNode first, PairingHeapNode second )
    {
        if( second == null )
            return first;

        if( second.compareTo( first ) < 0 )
        {
            // Attach first as leftmost child of second
            second.prev = first.prev;
            first.prev = second;
            first.nextSibling = second.leftChild;
            if( first.nextSibling != null )
                first.nextSibling.prev = first;
            second.leftChild = first;
            return second;
        }
        else
        {
            // Attach second as leftmost child of first
            second.prev = first;
            first.nextSibling = second.nextSibling;
            if( first.nextSibling != null )
                first.nextSibling.prev = first;
            second.nextSibling = first.leftChild;
            if( second.nextSibling != null )
                second.nextSibling.prev = second;
            first.leftChild = second;
            return first;
        }
    }
    /**
     * Internal method that implements two-pass merging.
     * @param firstSibling the root of the conglomerate;
     *     assumed not null.
     */
    private PairingHeapNode combineSiblings( PairingHeapNode firstSibling )
    {
        if( firstSibling.nextSibling == null )
            return firstSibling;

        // Store the subtrees in an array
        int numSiblings = 0;
        for( ; firstSibling != null; numSiblings++ ){
            treeArray = doubleIfFull( treeArray, numSiblings );
            treeArray[ numSiblings ] = firstSibling;
            firstSibling.prev.nextSibling = null;  // break links
            firstSibling = firstSibling.nextSibling;
        }
        treeArray = doubleIfFull( treeArray, numSiblings );
        treeArray[ numSiblings ] = null;

        // Combine subtrees two at a time, going left to right
        int i = 0;
        for( ; i + 1 < numSiblings; i += 2 )
            treeArray[ i ] = compareAndLink( treeArray[ i ], treeArray[ i + 1 ] );

        int j = i - 2;

        // j has the result of last compareAndLink.
        // If an odd number of trees, get the last one.
        if( j == numSiblings - 3 )
            treeArray[ j ] = compareAndLink( treeArray[ j ], treeArray[ j + 2 ] );

        // Now go right to left, merging last tree with
        // next to last. The result becomes the new last.
        for( ; j >= 2; j -= 2 )
            treeArray[ j - 2 ] = compareAndLink( treeArray[ j - 2 ], treeArray[ j ] );

        return treeArray[ 0 ];
    }

    private PairingHeapNode [ ] doubleIfFull( PairingHeapNode [ ] array, int index ){
        if( index == array.length )
            array = Arrays.copyOf(array, array.length * 2);
        return array;
    }

    public  class PairingHeapNode extends HeapNode {

        public PairingHeapNode   leftChild;
        public PairingHeapNode   nextSibling;
        public PairingHeapNode   prev;

        public PairingHeapNode( HeapNode x) {
            super(x.getData(),x.getFrequency(),x.getLeft(), x.getRight());
        }
    }
    
    public void buildHeap(Map<Integer,Integer> map){
		for (Entry<Integer, Integer> entry : map.entrySet()){
	    	HeapNode hp=new HeapNode(entry.getKey(), entry.getValue(),null,null);
	    	add(hp);
		}
	}
}