package business.segmentation;

import business.features.export.SegmentExporter;
import entity.AbstractSegment;
import entity.Configuration;
import entity.SegmentRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SegmenatationMain {

    public static void main(String[] args) throws IOException {

        // use this part for segmentation
//        AbstractSegmentation segmentation = new Segmentation();
//        List<SegmentRepository> repositories = segmentation.parseDataDirectory(new File(Configuration.TXT_INPUT_FILES));
//        SegmentExporter.exportAll(repositories);

        // use this part for creating train data using balancer
        ArffExporter arffExporter = new ArffExporter();
        List<SegmentRepository> segmentRepositories = arffExporter.getSegmentRepositories();

        // use this part to compute the last 2 features (pearson, max correlation)
        MultiChannelSegmentation multiChannelSegmentation = new MultiChannelSegmentation(segmentRepositories);
        multiChannelSegmentation.buildMultichannelSegments();

        // create allForTrain and allForTest files
        arffExporter.export();
    }

}
