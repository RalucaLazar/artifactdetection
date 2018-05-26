package entity;

public interface Configuration {
    // change this to 250
    int WINDOW_SIZE = 250; // 1s

    // change this to 250/4
    int STEP = 250 / 4; // 2500ms

    // change this to 250 / 1000
    double RATE = (double) 250 / 1000; // 512 sampling freq

    String PROJECT_PATH = "/home/raluca/work/artifactdetection";
    String RESOURCES_PATH = PROJECT_PATH + "/service/src/main/resources";
    String RESULTS_PATH = PROJECT_PATH + "/results";

    // change this to count (nr. of read values) GOOD VALUE: 369498
    int MAX_INDEX = 369498;
    String ARFF_TRAIN_NAME = RESULTS_PATH + "/WekaTrainInput.arff";
    int TEST_PROPORTION = 20; //20%
    int EVAL_PROPORTION = 20; //20%
    int TRAIN_MAX_INDEX = (100 - TEST_PROPORTION - EVAL_PROPORTION) * MAX_INDEX / 100;
    int TEST_MAX_INDEX = TRAIN_MAX_INDEX + TEST_PROPORTION * MAX_INDEX / 100;

    String DT_MODEL = RESOURCES_PATH + "/Classifier/wekaREP2.model";
    String MULTI_SEGMENTS_PATH = RESULTS_PATH + "/MultiSegments";
    String BINARY_INPUT_FILES = RESOURCES_PATH + "/bin_data";
    String TXT_INPUT_FILES = RESOURCES_PATH + "/txt_data";

    String ARFF_TRAIN1_NAME = RESULTS_PATH + "/Train1.arff";
    String ARFF_TRAIN2_NAME = RESULTS_PATH + "/Train2.arff";
    String ARFF_TRAIN3_NAME = RESULTS_PATH + "/Train3.arff";
    String ARFF_TRAIN4_NAME = RESULTS_PATH + "/Train4.arff";
    String ARFF_TRAIN5_NAME = RESULTS_PATH + "/Train5.arff";
    String ARFF_EVAL_NAME = RESULTS_PATH + "/Eval.arff";
    String ARFF_TEST_NAME = RESULTS_PATH + "/Test.arff";

    //allForTest
    String INPUT_SEGMENTS_FILENAME = "/home/raluca/work/artifactdetection/results/Test.ser";

}
