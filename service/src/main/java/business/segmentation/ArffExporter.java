package business.segmentation;

import business.features.export.ArffGenerator;
import business.features.export.SegmentSerializer;
import com.google.common.collect.Lists;
import entity.AbstractSegment;
import entity.Configuration;
import entity.ResultType;
import entity.SegmentRepository;
import utils.SegmentDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArffExporter {

    //	private SegmentRepository trainRepo;
    private SegmentRepository cleanRepo;
    private SegmentRepository occularRepo;
    private SegmentRepository muscleRepo;
    private SegmentRepository cleanTest;
    private SegmentRepository occularTest;
    private SegmentRepository muscleTest;
    private SegmentRepository cleanEval;
    private SegmentRepository occularEval;
    private SegmentRepository muscleEval;
    private SegmentRepository allForTrain;
    private SegmentRepository allForTest;
    private SegmentRepository train1;
    private SegmentRepository train2;
    private SegmentRepository train3;
    private SegmentRepository train4;
    private SegmentRepository train5;
    private SegmentRepository eval;
    private SegmentRepository test;

    public ArffExporter() {
        //trainRepo = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH+"/TrainData.ser");
        this.cleanRepo = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Clean_Train.ser");
        this.occularRepo = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Occular_Train.ser");
        this.muscleRepo = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Muscle_Train.ser");
        this.cleanTest = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Clean_Test.ser");
        this.occularTest = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Occular_Test.ser");
        this.muscleTest = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Muscle_Test.ser");
        this.cleanEval = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Clean_Eval.ser");
        this.occularEval = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Occular_Eval.ser");
        this.muscleEval = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Muscle_Eval.ser");

        this.train1 = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Train1Balanced.ser");
        this.train2 = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Train2Balanced.ser");
        this.train3 = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Train3Balanced.ser");
        this.train4 = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Train4Balanced.ser");
        this.train5 = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Train5Balanced.ser");
        this.eval = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Eval.ser");
        this.test = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH + "/Test.ser");

//        this.allForTrain =  SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH+"/AllForTrain.ser");
//        this.allForTest = SegmentDeserializer.deserializeSegmentsFromFile(Configuration.RESULTS_PATH+"/AllForTestBalanced.ser");

        //System.out.println("Full train "+trainRepo.getSegments().size());
        System.out.println("Clean " + this.cleanRepo.getSegments().size());
        System.out.println("Ocular " + this.occularRepo.getSegments().size());
        System.out.println("Muscle " + this.muscleRepo.getSegments().size());
        System.out.println("Clean test " + this.cleanTest.getSegments().size());
        System.out.println("Ocular test " + this.occularTest.getSegments().size());
        System.out.println("Muscle test " + this.muscleTest.getSegments().size());
        System.out.println("Clean eval " + this.cleanEval.getSegments().size());
        System.out.println("Ocular eval " + this.occularEval.getSegments().size());
        System.out.println("Muscle eval " + this.muscleEval.getSegments().size());
    }

    public SegmentRepository getCleanRepo() {
        return cleanRepo;
    }

    public SegmentRepository getOccularRepo() {
        return occularRepo;
    }

    public SegmentRepository getMuscluRepo() {
        return muscleRepo;
    }


    public SegmentRepository getOccularTest() {
        return occularTest;
    }

    public SegmentRepository getMuscleTest() {
        return muscleTest;
    }

    public SegmentRepository getOccularEval() {
        return occularEval;
    }


    public ArffExporter(boolean nix) {

    }

    public List<SegmentRepository> getSegmentRepositories() {
        return Lists.newArrayList(this.occularRepo, this.occularEval, this.occularTest,
                this.muscleRepo, this.muscleEval, this.muscleTest,
                this.cleanRepo, this.cleanEval, this.cleanTest);
    }

    public SegmentRepository getOcularRepo() {
        return this.occularRepo;
    }

    public SegmentRepository getCleanEval() {
        return this.cleanEval;
    }

    public SegmentRepository getOcularEval() {
        return this.occularEval;
    }

    public SegmentRepository getCleanTest() {
        return this.cleanTest;
    }

    public SegmentRepository getOcularTest() {
        return this.occularTest;
    }

    public void exportArff() throws IOException {
//         TRAIN1
        List<AbstractSegment> train1Segm = new ArrayList<>();
        train1Segm.addAll(this.train1.getSegments());
        Collections.shuffle(train1Segm);
        ArffGenerator arffGenerator1 = new ArffGenerator(Configuration.ARFF_TRAIN1_NAME);
        for (AbstractSegment segment : train1Segm) {
            arffGenerator1.writeSegmentFeatures(segment.getFeatures(), segment.getCorrectType());
        }

//         TRAIN2
        List<AbstractSegment> train2Segm = new ArrayList<>();
        train2Segm.addAll(this.train2.getSegments());
        Collections.shuffle(train2Segm);
        ArffGenerator arffGenerator2 = new ArffGenerator(Configuration.ARFF_TRAIN2_NAME);
        for (AbstractSegment segment : train2Segm) {
            arffGenerator2.writeSegmentFeatures(segment.getFeatures(), segment.getCorrectType());
        }

        // TRAIN3
        List<AbstractSegment> train3Segm = new ArrayList<>();
        train3Segm.addAll(this.train3.getSegments());
        Collections.shuffle(train3Segm);
        ArffGenerator arffGenerator3 = new ArffGenerator(Configuration.ARFF_TRAIN3_NAME);
        for (AbstractSegment segment : train3Segm) {
            arffGenerator3.writeSegmentFeatures(segment.getFeatures(), segment.getCorrectType());
        }

        // TRAIN4
        List<AbstractSegment> train4Segm = new ArrayList<>();
        train4Segm.addAll(this.train4.getSegments());
        Collections.shuffle(train4Segm);
        ArffGenerator arffGenerator4 = new ArffGenerator(Configuration.ARFF_TRAIN4_NAME);
        for (AbstractSegment segment : train4Segm) {
            arffGenerator4.writeSegmentFeatures(segment.getFeatures(), segment.getCorrectType());
        }

        // TRAIN5
        List<AbstractSegment> train5Segm = new ArrayList<>();
        train5Segm.addAll(this.train5.getSegments());
        Collections.shuffle(train5Segm);
        ArffGenerator arffGenerator5 = new ArffGenerator(Configuration.ARFF_TRAIN5_NAME);
        for (AbstractSegment segment : train5Segm) {
            arffGenerator5.writeSegmentFeatures(segment.getFeatures(), segment.getCorrectType());
        }

        // EVAL
        List<AbstractSegment> evalSegm = new ArrayList<>();
        evalSegm.addAll(this.eval.getSegments());
        Collections.shuffle(evalSegm);
        ArffGenerator arffGenerator6 = new ArffGenerator(Configuration.ARFF_EVAL_NAME);
        for (AbstractSegment segment : evalSegm) {
            arffGenerator6.writeSegmentFeatures(segment.getFeatures(), segment.getCorrectType());
        }

        //TEST
        List<AbstractSegment> testSegm = new ArrayList<>();
        testSegm.addAll(this.test.getSegments());
        Collections.shuffle(testSegm);
        ArffGenerator arffGenerator7 = new ArffGenerator(Configuration.ARFF_TEST_NAME);
        for (AbstractSegment segment : testSegm) {
            arffGenerator7.writeSegmentFeatures(segment.getFeatures(), segment.getCorrectType());
        }
    }


    public void export() throws IOException {

        List<AbstractSegment> cleaned = new ArrayList<>();

        for (AbstractSegment segm : this.cleanRepo.getSegments()) {
            if (segm.getCorrectType().equals(ResultType.BRAIN_SIGNAL)) {
                cleaned.add(segm);
            }
        }

        List<AbstractSegment> all = new ArrayList<>();

        all.addAll(cleaned);
        all.addAll(this.occularRepo.getSegments());
        all.addAll(this.muscleRepo.getSegments());

        Collections.shuffle(all);

        SegmentRepository trainsrep = new SegmentRepository("AllForTrain");
        trainsrep.setSegments(all);
        SegmentSerializer.serialize(trainsrep, Configuration.RESULTS_PATH);


    }

}
