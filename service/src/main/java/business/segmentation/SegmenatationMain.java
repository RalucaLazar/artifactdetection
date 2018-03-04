package business.segmentation;

import business.features.export.SegmentExporter;
import entity.Configuration;
import entity.SegmentRepository;

import java.io.File;
import java.util.List;

public class SegmenatationMain {

    public static void main(String[] args) {

        AbstractSegmentation segmentation = /*new Segmentation();*/new SimpleSegmentation();
        List<SegmentRepository> repositories = segmentation.parseDataDirectory(new File(Configuration.TXT_INPUT_FILES));
        SegmentExporter.exportAll(repositories);
    }
}
