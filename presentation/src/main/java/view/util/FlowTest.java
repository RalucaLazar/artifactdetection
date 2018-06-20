package view.util;

import business.features.export.SegmentExporter;
import business.features.export.SegmentSerializer;
import business.segmentation.*;
import entity.*;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static view.util.FileUtil.regionsAndChannels;

public class FlowTest {

    public static void main(String[] args) {
        FileUtil file = new FileUtil();

        System.out.println("Afisare regiuni si canale citite");
        for (Map.Entry<String, List<String>> entry : regionsAndChannels.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        System.out.println("Afisare regiuni si canale din RegionChannel");
        for (Map.Entry<String, List<Integer>> entry : RegionChannel.getRegionsAndChannels().entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        // this will create All_Segments repository
        AbstractSegmentation segmentation = new Segmentation();
        List<SegmentRepository> repositories = segmentation.parseDataDirectory(new File(Configuration.BINARY_INPUT_FILES),
                SegmentationType.SIMPLE);

        // serialize All_Segments file
        SegmentExporter.exportAll(repositories);

        // use this part to compute the last 2 features (pearson, max correlation)
        SegmentRepository allSegmentsRepo = SegmentDeserializer
                .deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/All_Segments_Test.ser");
        MultiChannelSegmentation multiChannelSegmentation = new MultiChannelSegmentation(Collections.singletonList(allSegmentsRepo));
        multiChannelSegmentation.buildMultichannelSegments();

        List<AbstractSegment> allSegments = allSegmentsRepo.getSegments();

        SegmentRepository cleanR = new SegmentRepository("cleanR");
        SegmentRepository ocularR = new SegmentRepository("ocularR");
        SegmentRepository muscleR = new SegmentRepository("muscleR");

        for (AbstractSegment segment : allSegments) {
            if (segment.getCorrectType().equals(ResultType.BRAIN_SIGNAL)) {
                cleanR.addSegment(segment);
            } else if (segment.getCorrectType().equals(ResultType.OCULAR)) {
                ocularR.addSegment(segment);
            } else {
                muscleR.addSegment(segment);
            }
        }

        System.out.println(cleanR.getSegments().size());
        System.out.println(ocularR.getSegments().size());
        System.out.println(muscleR.getSegments().size());

        allSegmentsRepo.setSegments(allSegments);
        SegmentSerializer.serialize(allSegmentsRepo, Configuration.RESULTS_PATH);
    }
}
