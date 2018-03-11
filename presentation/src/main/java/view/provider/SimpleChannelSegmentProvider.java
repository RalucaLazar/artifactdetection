package view.provider;


import entity.Segment;
import preprocessing.SegmentsGenerator;
import preprocessing.SegmentsGeneratorInterface;
import preprocessing.exception.FileReadingException;

import java.util.List;
import java.util.Map;

public class SimpleChannelSegmentProvider {

	private static final String PATH = "E:\\Scoala\\licenta\\#0IMPLEMENTATION\\Data\\newDataSet\\elena-doua-canale-13-04\\Occular_Eval.ser";

	private int nrChannel;
	private String inputSegmentsFilename;

	public SimpleChannelSegmentProvider(int nrChannel) {
		this.nrChannel = nrChannel;
	}

	// !!!! ADDED !!!!
	public SimpleChannelSegmentProvider(int nrChannel,String inputSegmentsFilename) {
		this.nrChannel = nrChannel;
		this.inputSegmentsFilename = inputSegmentsFilename;
	}

	/**
	 * Bring the channels from the channel nrChannel
	 */
	public List<Segment> provideSegments(int channel) {
//		logger.info("Start deserializing" + PATH);
//		SegmentDeserializer segmentDeserializer = new SegmentDeserializer();
//		SegmentRepository repo = segmentDeserializer.deserializeSegmentsFromFile(PATH);
//		List<AbstractSegment> segmentsAbstract = repo.getSegments();
//		List<Segment> segments = segmentsAbstract.stream().map(s -> (Segment) s).collect(Collectors.toList());
//		logger.info("Deserialized all");
//		for (Segment s : segments) {
//			logger.info(s.getInitIdx());
//		}
		
		SegmentsGeneratorInterface gen=new SegmentsGenerator();
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
