// Author: Abby Owen
// Purpose: Create a map of the characters in a file and create a tree using a priority queue
//Date: 4/29/20

import java.io.*;
import java.util.*;
import java.util.Comparator;

public class HandleCharacters {

    // Create method that creates a map of each character in a given file
    public static Map characters(String filename) throws Exception {
        BufferedReader in = null;
        Map<Character, Integer> charMap = null;
        try {
            // Open the file
            in = new BufferedReader(new FileReader(filename));

            // Initialize a character tree map
            charMap = new TreeMap<Character, Integer>();

            // Integer Unicode value that associates to the first character in the file
            int charToRead = in.read();

            // Loop through each character in the file
            while (charToRead != -1) {

                // Cast the Unicode value of the character from an integer to a character
                char toAdd = (char) charToRead;

                // If the character is already in the map update its frequency
                if (charMap.containsKey(toAdd)) {
                    charMap.replace(toAdd, charMap.get(toAdd) + 1);
                }

                // Add the character to the map if does not already exist
                else {
                    charMap.put(toAdd, 1);

                }

                // Advance to the next character
                charToRead = in.read();
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error: File Not Found");

        } finally {
            // Close file
            in.close();
        }

        // Return our final map
        return charMap;

    }

    // Create the huffman tree
    public static BinaryTreeWithFrequency createTree(String filename) throws Exception {

        // Provide something to return if an exception is caught
        PriorityQueue<BinaryTreeWithFrequency> pq = null;

        try {
            // Use the characters method to create a map
            Map<Character, Integer> charMap = characters(filename);

            // Create new comparator object to pass into the priority queue
            Comparator treeComp = new TreeComparator();

            // Create a new priority queue
            pq = new PriorityQueue<BinaryTreeWithFrequency>(treeComp);

            // Loop through each of the keys in the character map
            for (Map.Entry<Character, Integer> entry : charMap.entrySet()) {
                // Create a new binary tree with each character with their data and frequency
                BinaryTreeWithFrequency<Character, Integer> charTree = new BinaryTreeWithFrequency(entry.getKey(), entry.getValue());
                // Add the tree to the priority queue
                pq.add(charTree);
            }
            // Loop through every item in the priority queue
            // Control for case if there is 1 character
            if (pq.size() != 1) {
                while (pq.size() > 1) {

                    // Retrieve the elements with the highest priority value
                    BinaryTreeWithFrequency t1 = pq.poll();
                    BinaryTreeWithFrequency t2 = pq.poll();

            /* Create a subtree with the combined frequency of the characters as a node and the characters and their
            respective frequencies as children*/
                    BinaryTreeWithFrequency<Character, Integer> subtree = new BinaryTreeWithFrequency<Character, Integer>('*', (int) t1.getFreq() + (int) t2.getFreq(), t1, t2);

                    // Add the subtree to the priority queue
                    pq.add(subtree);
                }
            } else {
                // Remove the only entry in the Priority Queue (only one character)
                BinaryTreeWithFrequency t1 = pq.poll();

                // Make it a leaf node that is the child of a node that just contains the frequency of the character
                BinaryTreeWithFrequency<Character, Integer> subtree = new BinaryTreeWithFrequency<Character, Integer>('*', (int) t1.getFreq(), t1, null);

                // Add it to the priority queue
                pq.add(subtree);
            }

        // Catch Error
        } catch (FileNotFoundException e){
            System.err.println("Error: File Not Found");

        } finally {
            // Return the final tree in the priority queue
            return pq.poll();
        }

    }

    public static void compress(String filename, String newPathname) throws Exception {
        // Create a new BufferedBitWriter and buffered reader
        BufferedBitWriter bitOutput = null;
        BufferedReader in = null;

        try {
            // Create the Huffman tree
            BinaryTreeWithFrequency<Character, Integer> pqTree = createTree(filename);

            // Open the file for reading
            in = new BufferedReader(new FileReader(filename));

            bitOutput = new BufferedBitWriter(newPathname);

            // Control for an empty file
            if (pqTree!= null) {
                // Create a new code map
                Map codeMap = new TreeMap<Character, String>();

                // Pass the code map as a parameter to the helper codeCharacter method
                codeMap = codeCharacter(codeMap, null, pqTree);

                // Look at the first character in the file
                int charToRead = in.read();

                // Loop through all of the characters in the file
                while (charToRead != -1) {

                    // Get the path code in the codeMap
                    String bit = (String) codeMap.get((char) charToRead);

                    // Loop over every character in the path string
                    for (int i = 0; i < bit.length(); i++) {
                        // Write false if the character is 0
                        if (bit.charAt(i) == '0') {

                            bitOutput.writeBit(false);
                        }
                        // Write true if the character is 1
                        else if (bit.charAt(i) == '1') {

                            bitOutput.writeBit(true);
                        }
                    }
                    // Advance the character
                    charToRead = in.read();

                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: File Not Found");

        } finally {
            // Close the file
            bitOutput.close();
            in.close();
        }

    }


    public static Map codeCharacter(Map codeMap, String path, BinaryTreeWithFrequency tree) {
        // Set the path to an empty string if it is null
        if (path == null) path = "";

        // Once we reach a leaf, add its path and its character to the code map
        if (tree.isLeaf()) {
            codeMap.put(tree.getData(), path);

        }
        // Recurse through each part of tree and alter its path accordingly
        if (tree.getLeft() != null) codeCharacter(codeMap, path + "0", tree.getLeft());
        if (tree.getRight() != null) codeCharacter(codeMap, path + "1", tree.getRight());

        // Return the map
        return codeMap;
    }

    public static void decompress(String toDecompress, String orig, String newPathname) throws Exception {

        BufferedWriter output = null;
        BufferedBitReader bitInput = null;
        try {
            // Open a new file to be written
            output = new BufferedWriter(new FileWriter(newPathname));

            // Create a new bit reader to read the output file
            bitInput = new BufferedBitReader(toDecompress);

            // Create a new tree from the original file
            BinaryTreeWithFrequency<Character, Integer> pqTree = createTree(orig);

            // Create a temporary tree that is identical to pqTree that can traversed down to find leaves
            BinaryTreeWithFrequency<Character, Integer> tempTree = pqTree;

            // Read through the bits
            while (bitInput.hasNext()) {
                // Read a bit
                boolean bit = bitInput.readBit();

                // If bit is true, go right (1)
                if (bit) {
                    tempTree = tempTree.getRight();
                }

                // If bit is false, go left (0)
                else {
                    tempTree = tempTree.getLeft();
                }

                // Write the character to the file at the current leaf
                if (tempTree.isLeaf()) {
                    char toWrite = tempTree.getData();
                    output.write(toWrite);

                    // Return to the top of the tree
                    tempTree = pqTree;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: File Not Found");

        } finally {
            // finally
            output.close();
            bitInput.close();

        }
    }
}




