package business.segmentation;

import business.builders.FeatureBuilder;
import entity.AbstractSegment;
import entity.Feature;
import entity.ResultType;

import java.util.ArrayList;
import java.util.List;

public class TrainDatasetHandler {

    /**
     * Function for saving a no of brain signals equal to no of artifacts
     *
     * @param segments - List<AbstractSegment>
     * @return List<AbstractSegment>
     */
    public List<AbstractSegment> getRandomUndersampling(List<AbstractSegment> segments) {
        int noOfArtifacts = 0, noOfBrainSignals = 0, consecutiveCount = 0;
        List<AbstractSegment> outputSegmentList = new ArrayList<AbstractSegment>();

        for (AbstractSegment segment : segments) {
            if (segment.getCorrectType() == ResultType.BRAIN_SIGNAL) {
                consecutiveCount++;
                if (consecutiveCount % 2 == 0 && noOfBrainSignals <= noOfArtifacts + 1000) {
                    noOfBrainSignals++;
                    outputSegmentList.add(segment);
                }
            } else {
                noOfArtifacts++;
                outputSegmentList.add(segment);
            }
        }

        return outputSegmentList;
    }

    /**
     * Function for saving a no of brain and muscle artifacts signals equal to
     * no of occular artifacts
     *
     * @param segments - List<AbstractSegment>
     * @return List<AbstractSegment>
     */
    public List<AbstractSegment> getRandomUndersamplingClasses(
            List<AbstractSegment> segments) {
        int noOfOcularArtifacts = 0, noOfMuscularArtifacts = 0, noOfBrainSignals = 0, consecutiveCount = 0;
        List<AbstractSegment> outputSegmentList = new ArrayList<AbstractSegment>();

        for (AbstractSegment segment : segments) {
            if (segment.getCorrectType() == ResultType.BRAIN_SIGNAL) {
                consecutiveCount++;
                if (consecutiveCount % 5 == 0
                        && noOfBrainSignals <= noOfOcularArtifacts) {
                    noOfBrainSignals++;
                    outputSegmentList.add(segment);
                }
            } else if (segment.getCorrectType() == ResultType.OCULAR) {
                noOfOcularArtifacts++;
                outputSegmentList.add(segment);
            } else {
                if (noOfMuscularArtifacts <= noOfOcularArtifacts) {
                    noOfMuscularArtifacts++;
                    outputSegmentList.add(segment);
                }
            }
        }

        return outputSegmentList;
    }

    /**
     * Function for creating noOfInstances artifacts by doubling the existing
     * ones
     *
     * @param segments         List<AbstractSegment>
     * @param noOfNewInstances
     * @return List<AbstractSegment>
     */
    public List<AbstractSegment> getRandomOversampling(List<AbstractSegment> segments, int noOfNewInstances) {
        List<AbstractSegment> outputSegmentList = new ArrayList<AbstractSegment>();
        outputSegmentList.addAll(segments);

        for (AbstractSegment segment : segments) {
            if (segment.getCorrectType() != ResultType.BRAIN_SIGNAL) {
                outputSegmentList.add(segment);
                noOfNewInstances--;
            }
            if (noOfNewInstances <= 0) {
                break;
            }
        }

        return outputSegmentList;
    }

    /**
     * Function for computing the euclidian distance between 2 segments
     *
     * @param segment1 AbstractSegment
     * @param segment2 AbstractSegment
     * @return euclidian distance between 2 segments
     */
    private double computeDistance(AbstractSegment segment1, AbstractSegment segment2) {
        double distance = 0;
        Feature[] features1 = segment1.getFeatures();
        Feature[] features2 = segment2.getFeatures();
        for (int i = 0; i < features1.length; i++) {
            distance += (features1[i].getValue() - features2[i].getValue()) * (features1[i].getValue() - features2[i].getValue());
        }
        return Math.sqrt(distance);
    }

    /**
     * @param testSegment
     * @param segments
     * @return nearest neighbor of testSegment from segments with respect to
     * euclidian distance
     */
    private AbstractSegment getNearestNeighbor(AbstractSegment testSegment, List<AbstractSegment> segments) {
        AbstractSegment nearestSegment = null;
        double distance = 100000, testDistance;
        for (AbstractSegment segment : segments) {
            if (segment.getCorrectType() == testSegment.getCorrectType()) {
                testDistance = computeDistance(testSegment, segment);
                if (testDistance < distance && testDistance > 0) {
                    distance = testDistance;
                    nearestSegment = segment;
                }
            }
        }

        return nearestSegment;
    }

    /**
     * Creates new artifacts based on the existing ones following SMOTE
     * algorithm and adds them to segments list
     *
     * @param segments
     * @param noOfNewSamples - samples to be computed
     * @return List<AbstractSegment>
     */
    public List<AbstractSegment> applySMOTE(List<AbstractSegment> segments, int noOfNewSamples) {
        AbstractSegment newSegment, neighbourSegment;
        Feature[] features, neighbourFeatures, newFeatures;
        double randomValue;
        List<AbstractSegment> outputSegmentList = new ArrayList<>(segments);
        for (AbstractSegment segment : segments) {
            if (segment.getCorrectType() != ResultType.BRAIN_SIGNAL) {
                neighbourSegment = getNearestNeighbor(segment, segments);
                newSegment = new AbstractSegment();
                newSegment.setCorrectType(segment.getCorrectType());
                features = segment.getFeatures();
                neighbourFeatures = neighbourSegment.getFeatures();
                newFeatures = FeatureBuilder.createStandardFeaturesInstances();
                randomValue = Math.random();
                for (int i = 0; i < features.length; i++) {
                    newFeatures[i].setValue(features[i].getValue()
                            + (features[i].getValue() - neighbourFeatures[i]
                            .getValue()) * randomValue);
                }
                newSegment.setFeatures(newFeatures);
                outputSegmentList.add(newSegment);
                noOfNewSamples--;
            }
            if (noOfNewSamples == 0) {
                break;
            }
        }
        return outputSegmentList;
    }

}
