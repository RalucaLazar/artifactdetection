package view.provider;

import entity.AbstractSegment;
import entity.MultiChannelSegment;
import entity.MultiChannelSegmentSelector;
import entity.SegmentRepository;
import helpers.SegmentDeserializer;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MultipleSegmentProvider {

	private static final String PATH = "D:\\Projects\\Diploma_EEG_Artefact_Detection_Maven\\results\\Clean_Multi_Eval.ser";
	private static final String PATH2 = "D:\\Projects\\Diploma_EEG_Artefact_Detection_Maven\\results\\Muscle_Multi_Eval.ser";
	private static final String PATH3 = "D:\\Projects\\Diploma_EEG_Artefact_Detection_Maven\\results\\Occular_Multi_Eval.ser";
//	Logger logger = LoggerUtil.logger(getClass());

	
	private MultiChannelSegmentSelector selector;

	public MultipleSegmentProvider(MultiChannelSegmentSelector selector) {
		this.selector = selector;
	}

	public List<MultiChannelSegment> provideSegments() {
		SegmentDeserializer segmentDeserializer = new SegmentDeserializer();
		SegmentRepository repo = segmentDeserializer.deserializeSegmentsFromFile(PATH);
		SegmentRepository repo2 = segmentDeserializer.deserializeSegmentsFromFile(PATH2);
		SegmentRepository repo3 = segmentDeserializer.deserializeSegmentsFromFile(PATH3);
		List<AbstractSegment> segmentsAbstract = repo.getSegments();
		segmentsAbstract.addAll(repo2.getSegments());
		segmentsAbstract.addAll(repo3.getSegments());
		List<MultiChannelSegment> multiChannelSegments = segmentsAbstract.stream().map(s -> (MultiChannelSegment) s)
				.collect(Collectors.toList());
		Collections.sort(multiChannelSegments);
		for (MultiChannelSegment segm : multiChannelSegments) {
//			logger.info("Initial index: "+segm.getInitIdx());
		}
		return multiChannelSegments;
	}

}
