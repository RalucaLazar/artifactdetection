package business.conversion;

import java.io.IOException;

public class BinaryToTextConversionThread extends Thread {

    private String pathToBinaryFile;
    private String pathToTextFile;

    public BinaryToTextConversionThread(String pathToBinaryFile, String pathToTextFile) {
        this.pathToBinaryFile = pathToBinaryFile;
        this.pathToTextFile = pathToTextFile;
    }

    @Override
    public void run() {
        try {
            BinaryToTextConversion.binaryToText(pathToBinaryFile, pathToTextFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
