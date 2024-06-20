public class MinHeap {
    private Node[] heap;
    private int size;

    public MinHeap() {
        heap = new Node[100];
        size = 0;
    }

    public void add(Node node) {
        if (size >= heap.length - 1) {
            // Resize the heap array if it's full
            Node[] newHeap = new Node[heap.length * 2];
            System.arraycopy(heap, 0, newHeap, 0, heap.length);
            heap = newHeap;
        }
    
        int index = size + 1;
        heap[index] = node;
        while (index > 1) {
            int parentIndex = index / 2;
            if (heap[parentIndex].getF() > node.getF()) {
                heap[index] = heap[parentIndex];
                heap[parentIndex] = node;
                index = parentIndex;
            } else {
                break;
            }
        }
        size++;
    }
    

    public void remove(Node targetNode) {
        int targetIndex = -1;
        for (int i = 1; i <= size; i++) {
            if (heap[i].equals(targetNode)) {
                targetIndex = i;
                break;
            }
        }
    
        if (targetIndex != -1) {
            Node lastNode = heap[size];
            heap[targetIndex] = lastNode;
            heap[size] = null;
    
            int index = targetIndex;
            while (index * 2 < size) {
                int leftIndex = index * 2;
                int rightIndex = leftIndex + 1;
                Node leftNode = heap[leftIndex];
                Node rightNode = lastNode;
    
                if (rightIndex < size) {
                    rightNode = heap[rightIndex];
                }
    
                Node minChild;
                int minIndex;
    
                if (leftNode.getF() < rightNode.getF()) {
                    minChild = leftNode;
                    minIndex = leftIndex;
                } else {
                    minChild = rightNode;
                    minIndex = rightIndex;
                }
    
                if (minChild.getF() < lastNode.getF()) {
                    heap[minIndex] = lastNode;
                    heap[index] = minChild;
                    index = minIndex;
                } else {
                    break;
                }
            }
    
            size--;
        }
    }
    
    

    public Node pop() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty.");
        }
    
        Node rootValue = heap[1];
        Node lastNode = heap[size];
        heap[1] = lastNode;
        heap[size] = null;
    
        int index = 1;
        while (index * 2 < size) {
            int leftIndex = index * 2;
            int rightIndex = leftIndex + 1;
            Node leftNode = heap[leftIndex];
            Node rightNode = lastNode;
    
            if (rightIndex < size) {
                rightNode = heap[rightIndex];
            }
    
            Node minChild;
            int minIndex;
    
            if (leftNode.getF() < rightNode.getF()) {
                minChild = leftNode;
                minIndex = leftIndex;
            } else {
                minChild = rightNode;
                minIndex = rightIndex;
            }
    
            if (minChild.getF() < lastNode.getF()) {
                heap[minIndex] = lastNode;
                heap[index] = minChild;
                index = minIndex;
            } else {
                break;
            }
        }
    
        size--;
        return rootValue;
    }

    public boolean contains(Node targetNode) {
        for (int i = 1; i <= size; i++) {
            if (heap[i].equals(targetNode)) {
                return true; // Found the target node
            }
        }
        return false; // Target node not found
    }
    
    /**
     * Getter returns size of heap.
     * @return int size of heap
     */
    public int getSize()
    {
        return size;
    }
}
