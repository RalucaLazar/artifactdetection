package business.segmentation;

import business.features.export.SegmentSerializer;
import com.google.common.collect.Lists;
import entity.AbstractSegment;
import entity.Configuration;
import entity.ResultType;
import entity.SegmentRepository;

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

    public SegmentRepository getCleanTest() {
        return cleanTest;
    }

    public SegmentRepository getOccularTest() {
        return occularTest;
    }

    public SegmentRepository getMuscleTest() {
        return muscleTest;
    }

    public SegmentRepository getCleanEval() {
        return cleanEval;
    }

    public SegmentRepository getOccularEval() {
        return occularEval;
    }

    public SegmentRepository getMuscleEval() {
        return muscleEval;
    }


    public ArffExporter(boolean nix) {

    }

    public List<SegmentRepository> getSegmentRepositories() {
        return Lists.newArrayList(this.occularRepo, this.occularEval, this.occularTest,
                this.muscleRepo, this.muscleEval, this.muscleTest,
                this.cleanRepo, this.cleanEval, this.cleanTest);
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
