package business.segmentation;

import business.features.export.SegmentExporter;
import business.features.export.SegmentSerializer;
import entity.AbstractSegment;
import entity.Configuration;
import entity.ResultType;
import entity.SegmentRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SegmenatationMain {

    public static void main(String[] args) throws IOException {

//        // use this part for segmentation
//        AbstractSegmentation segmentation = new Segmentation();
//        List<SegmentRepository> repositories = segmentation.parseDataDirectory(new File(Configuration.TXT_INPUT_FILES));
//        SegmentExporter.exportAll(repositories);

        // use this part to compute the last 2 features (pearson, max correlation)
//        ArffExporter arffExporter = new ArffExporter();
//        List<SegmentRepository> segmentRepositories = arffExporter.getSegmentRepositories();
//        MultiChannelSegmentation multiChannelSegmentation = new MultiChannelSegmentation(segmentRepositories);
//        multiChannelSegmentation.buildMultichannelSegments();
//
//        // use this part to divide train set and apply smote and undersampling
//        List<AbstractSegment> evalSegments = combineSers(arffExporter.getCleanEval(), arffExporter.getOccularEval());
//
//        SegmentRepository evalRepo = new SegmentRepository("Eval");
//        evalRepo.setSegments(evalSegments);
//        SegmentSerializer.serialize(evalRepo, Configuration.RESULTS_PATH);
//
        //List<AbstractSegment> testSegments = combineSers(arffExporter.getCleanTest(), arffExporter.getOccularTest());

//        SegmentRepository testRepo = new SegmentRepository("Test");
//        testRepo.setSegments(testSegments);
//        System.out.println("Test segments: " + testSegments.size());
//        SegmentSerializer.serialize(testRepo, Configuration.RESULTS_PATH);

//        List<AbstractSegment> cleanSegments = arffExporter.getCleanRepo().getSegments();
//
//        int size = cleanSegments.size() / 5;
//        System.out.println("Train clean size: " + cleanSegments.size());
//        System.out.println("One train clean size: " + size);
//
//        List<AbstractSegment> cleanList1 = cleanSegments.subList(0, size);
//        List<AbstractSegment> cleanList2 = cleanSegments.subList(size + 1, 2 * size);
//        List<AbstractSegment> cleanList3 = cleanSegments.subList(2 * size + 1, 3 * size);
//        List<AbstractSegment> cleanList4 = cleanSegments.subList(3 * size + 1, 4 * size);
//        List<AbstractSegment> cleanList5 = cleanSegments.subList(4 * size + 1, 5 * size);
//
//        List<AbstractSegment> ocularSegments = arffExporter.getOccularRepo().getSegments();
//
//        List<AbstractSegment> train1 = new ArrayList<>();
//        List<AbstractSegment> train2 = new ArrayList<>();
//        List<AbstractSegment> train3 = new ArrayList<>();
//        List<AbstractSegment> train4 = new ArrayList<>();
//        List<AbstractSegment> train5 = new ArrayList<>();
//
//        train1.addAll(cleanList1);
//        train1.addAll(ocularSegments);
//        SegmentRepository train1Repo = new SegmentRepository("Train1");
//        train1Repo.setSegments(train1);
//        SegmentSerializer.serialize(train1Repo, Configuration.RESULTS_PATH);
//        dataBalancer("/home/raluca/work/artifactdetection/results/Train1.ser");

//        train2.addAll(cleanList2);
//        train2.addAll(ocularSegments);
//        SegmentRepository train2Repo = new SegmentRepository("Train2");
//        train2Repo.setSegments(train2);
//        SegmentSerializer.serialize(train2Repo, Configuration.RESULTS_PATH);
//        dataBalancer("/home/raluca/work/artifactdetection/results/Train2.ser");
//
//        train3.addAll(cleanList3);
//        train3.addAll(ocularSegments);
//        SegmentRepository train3Repo = new SegmentRepository("Train3");
//        train3Repo.setSegments(train3);
//        SegmentSerializer.serialize(train3Repo, Configuration.RESULTS_PATH);
//        dataBalancer("/home/raluca/work/artifactdetection/results/Train3.ser");
//
//        train4.addAll(cleanList4);
//        train4.addAll(ocularSegments);
//        SegmentRepository train4Repo = new SegmentRepository("Train4");
//        train4Repo.setSegments(train4);
//        SegmentSerializer.serialize(train4Repo, Configuration.RESULTS_PATH);
//        dataBalancer("/home/raluca/work/artifactdetection/results/Train4.ser");
//
//        train5.addAll(cleanList5);
//        train5.addAll(ocularSegments);
//        SegmentRepository train5Repo = new SegmentRepository("Train5");
//        train5Repo.setSegments(train5);
//        SegmentSerializer.serialize(train5Repo, Configuration.RESULTS_PATH);
//        dataBalancer("/home/raluca/work/artifactdetection/results/Train5.ser");

        // use this part for generating arff
//        arffExporter.exportArff();
    }

    private static List<AbstractSegment> combineSers(SegmentRepository firstRepository, SegmentRepository secondRepository) {
        List<AbstractSegment> allSegments = new ArrayList<>();

        allSegments.addAll(firstRepository.getSegments());
        allSegments.addAll(secondRepository.getSegments());

        return allSegments;
    }

    private static void dataBalancer(String pathToFile) {
        SegmentRepository repository = SegmentDeserializer.deserializeSegmentsFromFile(pathToFile);
        List<AbstractSegment> segments = new ArrayList<>(repository.getSegments());

        System.out.println("Number of segments: " + segments.size());
        TrainDatasetHandler datasetHandler = new TrainDatasetHandler();
        List<AbstractSegment> oversampledSegments = datasetHandler.getSMOTEOversampling(segments, 2000);
        System.out.println("Number of segments after SMOTE: " + oversampledSegments.size());

        SegmentRepository cleanR = new SegmentRepository("cleanR");
        SegmentRepository ocularR = new SegmentRepository("ocularR");
        SegmentRepository muscleR = new SegmentRepository("muscleR");

        for (AbstractSegment oversampledSegment : oversampledSegments) {
            if (oversampledSegment.getCorrectType().equals(ResultType.BRAIN_SIGNAL)) {
                cleanR.addSegment(oversampledSegment);
            } else if (oversampledSegment.getCorrectType().equals(ResultType.OCULAR)) {
                ocularR.addSegment(oversampledSegment);
            } else {
                muscleR.addSegment(oversampledSegment);
            }
        }

        System.out.println("Number of clean segments after SMOTE: " + cleanR.getSegments().size());
        System.out.println("Number of ocular segments after SMOTE: " + ocularR.getSegments().size());

        DataBalancer dataBalancer = new DataBalancer();
        List<AbstractSegment> undersampledSegments = dataBalancer.undersample(cleanR, ocularR, muscleR);
        System.out.println("Number of segments after undersampling: " + undersampledSegments.size());

        SegmentRepository cleanR1 = new SegmentRepository("cleanR");
        SegmentRepository ocularR1 = new SegmentRepository("ocularR");

        for (AbstractSegment undersampledSegment : undersampledSegments) {
            if (undersampledSegment.getCorrectType().equals(ResultType.BRAIN_SIGNAL)) {
                cleanR1.addSegment(undersampledSegment);
            } else {
                ocularR1.addSegment(undersampledSegment);
            }
        }

        System.out.println("Number of clean segments after under: " + cleanR1.getSegments().size());
        System.out.println("Number of ocular segments after under: " + ocularR1.getSegments().size());

        String[] parts = pathToFile.split("/");
        String name = parts[parts.length - 1];
        String newName = name.substring(0, 6);

        SegmentRepository trainsrep = new SegmentRepository(newName + "Balanced");
        trainsrep.setSegments(undersampledSegments);
        SegmentSerializer.serialize(trainsrep, Configuration.RESULTS_PATH);
    }

}
