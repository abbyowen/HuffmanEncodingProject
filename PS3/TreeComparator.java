//Author: Abby Owen
//Purpose: Create a comparator that will compare frequencies from a tree
//Date: 5/1/20

import java.util.Comparator;

public  class TreeComparator implements Comparator<BinaryTreeWithFrequency<Character, Integer>> {

    // Compare method
    public int compare(BinaryTreeWithFrequency<Character, Integer> o1, BinaryTreeWithFrequency<Character, Integer> o2){
        // Will return 1 if o1> o2
        if(o1.getFreq()>o2.getFreq()){
            return 1;
        }
        // Will return -1 if o1<o2
        else if (o1.getFreq()<o2.getFreq()) {
            return-1;
        }
        // Will return 0 if o1=o2
        else{
            return 0;
        }
        }

    }


