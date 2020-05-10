import java.io.FileNotFoundException;

    public class Test {
        public static void main(String[] args) throws Exception {
            try {
                HandleCharacters test = new HandleCharacters();
                test.compress("PS3/test.txt", "PS3/testOut.txt");
                test.decompress("PS3/testOut.txt", "PS3/test.txt", "PS3/testDec.txt");
            } catch (FileNotFoundException e) {
                System.err.println("Error: File Not Found");
            }
        }
    }

