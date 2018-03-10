package business.conversion;

import java.io.*;
import java.text.DecimalFormat;

public class BinaryToTextConversion {

    public static void binaryToText(String pathToBinaryFile, String pathToTextFile) throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileOutputStream(pathToTextFile));
        DataInputStream input = new DataInputStream(new FileInputStream(pathToBinaryFile));

        while (input.available() > 0) {
            try {
                printWriter.println(Float.intBitsToFloat(Integer.reverseBytes(input.readInt())));
            } catch (EOFException e) {
                break;
            }
        }
        printWriter.close();
    }

    public static String changeFileExtension(String file) {
        if (file.contains(".")) {
            int index = file.indexOf(".");
            String filename = file.substring(0, index);
            return filename + ".txt";
        } else {
            return file;
        }
    }

}
