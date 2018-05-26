package business.segmentation;

import java.io.File;
import java.io.IOException;

public class SegmentationThread extends Thread {

    private File file;
    private int count;

    public SegmentationThread(File file, int count) {
        this.file = file;
        this.count = count;
    }

    @Override
    public void run() {
        try {
            Segmentation.parseFile(file, count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
