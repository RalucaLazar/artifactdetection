package business.builders;

import com.google.common.collect.Lists;
import entity.Configuration;
import entity.ResultType;
import entity.Segment;
import entity.SegmentRepository;

import java.util.List;

public class StructureBuilder extends AbstractStructureBuilder {

    private SegmentRepository occularStructTrain;
//    private SegmentRepository muscleStructTrain;
    private SegmentRepository brainStructTrain;
    private SegmentRepository occularStructTest;
//    private SegmentRepository muscleStructTest;
    private SegmentRepository brainStructTest;
    private SegmentRepository occularStructEval;
//    private SegmentRepository muscleStructEval;
    private SegmentRepository brainStructEval;

    public StructureBuilder() {
        super();
        //dataGen = new DataGeneratorForDecisionTree();
        brainStructEval = new SegmentRepository("Clean_Eval");
//        muscleStructEval = new SegmentRepository("Muscle_Eval");
        occularStructEval = new SegmentRepository("Occular_Eval");
        brainStructTest = new SegmentRepository("Clean_Test");
//        muscleStructTest = new SegmentRepository("Muscle_Test");
        occularStructTest = new SegmentRepository("Occular_Test");
        brainStructTrain = new SegmentRepository("Clean_Train");
//        muscleStructTrain = new SegmentRepository("Muscle_Train");
        occularStructTrain = new SegmentRepository("Occular_Train");
    }

    public void buildDataStructures(double[] data, int iter, int localIndex, int channel, int type) {
        int index = localIndex;
        if (type == 2) {
            index = localIndex + Configuration.TRAIN_MAX_INDEX;
        } else if (type == 3) {
            index = localIndex + Configuration.TEST_MAX_INDEX;
        }
        ResultType resultType = computeCorrectType(index, channel);
        if (resultType != null) {
            Segment segment = createSegment(data, iter, index, channel, resultType);
            //dataGen.writeSegment(segment);
            addToSerializableStructure(segment, type);
//			logger.info(segment);
        }
    }

    private void addToSerializableStructure(Segment segment, int type) {
        switch (segment.getCorrectType()) {
            case OCCULAR:
                switch (type) {
                    case 1:
                        occularStructTrain.addSegment(segment);
                        break;
                    case 2:
                        occularStructTest.addSegment(segment);
                        break;
                    default:
                        occularStructEval.addSegment(segment);
                        break;
                }
                break;
//            case MUSCLE:
//                switch (type) {
//                    case 1:
//                        muscleStructTrain.addSegment(segment);
//                        break;
//                    case 2:
//                        muscleStructTest.addSegment(segment);
//                        break;
//                    default:
//                        muscleStructEval.addSegment(segment);
//                        break;
//                }
//                break;
            default:
                switch (type) {
                    case 1:
                        brainStructTrain.addSegment(segment);
                        break;
                    case 2:
                        brainStructTest.addSegment(segment);
                        break;
                    default:
                        brainStructEval.addSegment(segment);
                        break;
                }
                break;
        }
    }

    public List<SegmentRepository> getSerializableStructures() {
        return Lists.newArrayList(occularStructTrain, occularStructEval, occularStructTest, brainStructTrain, brainStructEval, brainStructTest);

    }
}
