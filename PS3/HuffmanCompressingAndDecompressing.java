// Author: Abby Owen
// Purpose: Compress and Decompress WarAndPeace.txt and USConstitution.txt
// Date: 5/1/20

import java.io.*;

public class HuffmanCompressingAndDecompressing {
    public static void main(String[] args) throws Exception {
        try {
            // War and Peace compress and decompress
            HandleCharacters WarAndPeace = new HandleCharacters();
            WarAndPeace.compress("PS3/WarAndPeace.txt", "PS3/WarAndPeace_compressed.txt");
            WarAndPeace.decompress("PS3/WarAndPeace_compressed.txt", "PS3/WarAndPeace.txt", "PS3/WarAndPeace_decompressed.txt");

            // US Constitution compress and decompress
            HandleCharacters USConstitution = new HandleCharacters();
            USConstitution.compress("PS3/USConstitution.txt", "PS3/USConstitution_compressed.txt");
            USConstitution.decompress("PS3/USConstitution_compressed.txt", "PS3/USConstitution.txt", "PS3/USConstitution_decompressed.txt");

        } catch (FileNotFoundException e) {
            System.err.println("Error: A File Has Not Been Found in Main");
        }


    }
}
