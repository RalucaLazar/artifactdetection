package business.segmentation.simplesegmentation;

import java.io.File;
import java.io.IOException;

public class SimpleSegmentationThread extends Thread {
    private File file;
    private int count;

    public SimpleSegmentationThread(File file, int count) {
        this.file = file;
        this.count = count;
    }

    @Override
    public void run() {
        try {
            SimpleSegmentation.parseFile(file, count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
