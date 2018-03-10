package preprocessing;

import classifier.decisiontree.DecisionTreeClassifier;
import entity.AbstractSegment;
import entity.Segment;
import preprocessing.exception.FileReadingException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) throws FileReadingException {
		SegmentsGenerator segmentsGenerator = new SegmentsGenerator();
		Map<Integer, List<Segment>> segments = segmentsGenerator.generateSegments("");
		DecisionTreeClassifier decisionTreeClassifier = new DecisionTreeClassifier();
		List<AbstractSegment> abstractSegments = segments.get(78).stream().map(seg->(AbstractSegment)seg).collect(Collectors.toList());
		//decisionTreeClassifier.classifySegments(abstractSegments);
	}
}
