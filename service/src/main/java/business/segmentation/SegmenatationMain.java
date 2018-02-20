package business.segmentation;

import entity.Configuration;

import java.io.File;

public class SegmenatationMain {

    public static void main(String[] args) {
        Segmentation segmentation = new Segmentation();

        System.out.println(segmentation.parseDataDirectory(new File(Configuration.TXT_INPUT_FILES)));
    }
}
