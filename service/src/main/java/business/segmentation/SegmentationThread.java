package business.segmentation;

import java.io.File;

public class SegmentationThread extends Thread {

    private File file;
    private int count;

    public SegmentationThread(File file, int count) {
        this.file = file;
        this.count = count;
    }

    @Override
    public void run() {
        Segmentation.parseFile(file, count);
    }
}
