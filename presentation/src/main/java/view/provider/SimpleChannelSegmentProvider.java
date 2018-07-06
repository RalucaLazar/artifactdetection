package view.provider;


import entity.Configuration;
import entity.Segment;
import preprocessing.SegmentsGenerator;
import preprocessing.SegmentsGeneratorInterface;
import preprocessing.exception.FileReadingException;

import java.util.List;
import java.util.Map;

public class SimpleChannelSegmentProvider {

    private String inputSegmentsFilename;

    public SimpleChannelSegmentProvider() {
        this(Configuration.INPUT_SEGMENTS_FILENAME);
    }

    // !!!! ADDED !!!!
    public SimpleChannelSegmentProvider(String inputSegmentsFilename) {
        this.inputSegmentsFilename = inputSegmentsFilename;
    }

    /**
     * Bring the channels from the channel nrChannel
     */
    public List<Segment> provideSegments(int channel) {

        SegmentsGeneratorInterface gen = new SegmentsGenerator();
        Map<Integer, List<Segment>> map;
        try {
            map = gen.generateSegments(inputSegmentsFilename);
            return map.get(new Integer(channel));
        } catch (FileReadingException e) {
            e.printStackTrace();
        }
        return null;

    }
}
