package classifier;

import entity.Segment;

import java.util.List;

public interface Classifier {
	/**
	 * This method assigns a label to each segment from the list.
	 * 
	 * @param segments
	 *            A list on unlabeled segments.
	 * @return A list with Segments, all containing label set.
	 */
	List<Segment> classifySegments(List<Segment> segments);
	}
