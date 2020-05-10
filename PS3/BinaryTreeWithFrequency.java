// Author: Abby Owen
// Purpose: Create a new binary tree that can hold the frequency of the data in a node
// Date: 5/1/20

public class BinaryTreeWithFrequency<E, V> extends BinaryTree<E>{
    // Instantiate the new frequency
    V freq;
    private BinaryTreeWithFrequency<E, V> left, right;

    // Constructor
    public BinaryTreeWithFrequency(E data, V freq, BinaryTreeWithFrequency<E, V> left, BinaryTreeWithFrequency<E, V> right) {
        super(data, left, right);
        this.freq = freq;
        this.left = left;
        this.right = right;

    }
    // Constructor
    public BinaryTreeWithFrequency(E data, V freq) {
        super(data);
        this.freq = freq;
        this.left = null;
        this.right = null;
    }

    // Retrieve the frequency of the character
    public V getFreq() {
        return this.freq;

    }

    // Retrieve the left node
    public BinaryTreeWithFrequency getLeft() {
        return this.left;
    }

    // Retrieve the right node
    public BinaryTreeWithFrequency getRight() {
        return this.right;
    }

    // Create a new toString method that will print the frequency as well
    @Override
    public String toStringHelper(String indent) {
        String res = indent + data + "=" + freq + "\n";
        if (hasLeft()) res += left.toStringHelper(indent+"  ");
        if (hasRight()) res += right.toStringHelper(indent+"  ");
        return res;


    }


}
