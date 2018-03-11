package postprocessing;


import entity.ResultType;
import entity.Segment;

import java.util.ArrayList;
import java.util.List;

public class ResultsValidator {

	public List<Segment> validateClassificationResults(
			List<Segment> svmSegments, List<Segment> knnSegments,
			List<Segment> dtSegments) {
		List<Segment> resultList = new ArrayList<Segment>();
		int i = 0;

		// prepare for binary classification where artifact=muscle
		for (Segment segm : knnSegments) {
			if (segm.getCorrectType() != ResultType.BRAIN_SIGNAL)
				segm.setCorrectType(ResultType.MUSCLE);
		}

		for (Segment segm : dtSegments) {
			if (segm.getCorrectType() != ResultType.BRAIN_SIGNAL)
				segm.setCorrectType(ResultType.MUSCLE);
		}

		for (Segment segm : svmSegments) {
			if (segm.getCorrectType() == knnSegments.get(i).getCorrectType())
				resultList.add(segm);
			else if (segm.getCorrectType() == dtSegments.get(i)
					.getCorrectType())
				resultList.add(segm);
			else
				resultList.add(dtSegments.get(i));
			i++;
		}

		return resultList;
	}

}
