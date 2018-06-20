package classifier.knn;

import classifier.Classifier;
import entity.AbstractSegment;
import entity.Feature;
import entity.ResultType;
import entity.Segment;

import java.util.List;


public class KnnClassifier implements Classifier {

    private static final int NUMBER_FEATURES = 9;
//    Logger logger = LoggerUtil.logger(getClass());


    @Override
    public List<Segment> classifySegments(List<Segment> segments) {
        int numberOfSegments = segments.size();
        TestRecord[] testRecords = new TestRecord[numberOfSegments];
        int i = 0;
        for (AbstractSegment segment : segments) {
            TestRecord currentRecord = new TestRecord(getFeaturesValues(segment.getFeatures()), -1);
            testRecords[i] = currentRecord;
            i++;
        }
        int[] predictedValues = KnnManager.predict("artefacte_train2.txt", testRecords, 10, new ManhattenDistance());
//        logger.info("KNN algoritm is running - K=10 and distance Manhatten");
        for (int j = 0; j < segments.size(); j++) {
            // logger.info(segments.get(j) + "going to be set " +
            // calculateResultType(predictedValues[j])
            // + "and it was " + segments.get(j).getCorrectType());
            segments.get(j).setCorrectType(calculateResultType(predictedValues[j]));
            // logger.info(segments.get(j) + "has now " +
            // segments.get(j).getCorrectType());
        }
//        logger.info("Prediction is over!");
        return segments;
    }

    @Override
    public Segment classifySegment(Segment segment) {
        return null;
    }

    @Override
    public void createModel(String trainSetPath) {

    }

    private double[] getFeaturesValues(Feature[] features) {
        double[] values = new double[NUMBER_FEATURES];
        values[0] = features[2].getValue();
        values[1] = features[3].getValue();
        values[2] = features[4].getValue();
        values[3] = features[7].getValue();
        values[4] = features[8].getValue();
        values[5] = features[10].getValue();
        values[6] = features[13].getValue();
        values[7] = features[14].getValue();
        values[8] = features[15].getValue();

        return values;
    }

    private ResultType calculateResultType(int label) {
        if (label == 1) {
            return ResultType.BRAIN_SIGNAL;
        } else {
            if (label == 2) {
                return ResultType.OCULAR;
            } else {
                if (label == 3) {
                    return ResultType.MUSCLE;
                }
            }
        }
        return null;
    }

}
