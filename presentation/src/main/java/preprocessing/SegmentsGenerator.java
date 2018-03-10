package preprocessing;

import entity.*;
import preprocessing.exception.FileReadingException;
import utils.SegmentDeserializer;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SegmentsGenerator implements SegmentsGeneratorInterface {

    @Override
    public Map<Integer, List<Segment>> generateSegments(String inputSegmentsFilename) throws FileReadingException {
        // !!!!! ADDED !!!!!
        SegmentRepository allTest;
        if (inputSegmentsFilename.equals(""))
            allTest = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.INPUT_SEGMENTS_FILENAME);
        else
            allTest = SegmentDeserializer.deserializeSegmentsFromFile(inputSegmentsFilename);

        List<AbstractSegment> testSegm = allTest.getSegments();
        List<Segment> testSegments = testSegm
                .stream()
                .map(e -> (Segment) e)
                .collect(Collectors.toList());

        Map<Integer, List<Segment>> resultSegments =
                testSegments.stream().collect(Collectors.groupingBy(Segment::getChannelNr));

        for (Integer key : resultSegments.keySet()) {
            List<Segment> segms = resultSegments.get(key);
            Collections.sort(segms);
            resultSegments.put(key, segms);
        }

        return resultSegments;
    }

    @Override
    public List<MultiChannelSegment> generateMultisegments(String path, MultiChannelSegmentSelector selector)
            throws FileReadingException {
        // TODO Auto-generated method stub
        return null;
    }

}
