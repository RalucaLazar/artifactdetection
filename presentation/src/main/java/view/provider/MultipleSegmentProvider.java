package view.provider;

import entity.*;
import helpers.SegmentDeserializer;
import preprocessing.SegmentsGenerator;
import preprocessing.SegmentsGeneratorInterface;
import preprocessing.exception.FileReadingException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MultipleSegmentProvider {

	private String region;

	public MultipleSegmentProvider(String region) {
		this.region = region;
	}

	public List<MultiChannelSegment> provideSegments() {
		Map<Integer, List<Segment>> map;
		SegmentsGeneratorInterface gen = new SegmentsGenerator();
		List<Integer> channels = RegionChannel.getChannelsForRegion(region);
		List<MultiChannelSegment> multiChannelSegments = new ArrayList<>();

		try {
			map = gen.generateSegments(Configuration.INPUT_SEGMENTS_FILENAME);
			int size = Configuration.MAX_INDEX;
			for(int i=0;i<size;i++){
				MultiChannelSegment multiChannelSegment;
				List<Segment> segments = new ArrayList<>();
				for(int key:map.keySet()){
					if(channels.contains(key)){
						segments.add(map.get(key).get(i));
					}
				}
				multiChannelSegment = new MultiChannelSegment(segments);
				multiChannelSegments.add(multiChannelSegment);
			}

		} catch (FileReadingException e) {
			e.printStackTrace();
		}
		return multiChannelSegments;
	}

}
