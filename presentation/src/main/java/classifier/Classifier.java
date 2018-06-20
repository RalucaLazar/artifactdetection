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

	/**
	 * This method assigns a label to the segment.
	 *
	 * @param segment
	 *            An unlabeled segment.
	 * @return A labeled segment.
	 */
	Segment classifySegment(Segment segment);


	/**
	 * This method creates a new model for classifier.
	 *
	 * @param trainSetPath
	 *            The path for training data set.
	 */
	void createModel(String trainSetPath);

	}


