package business.classification;

import entity.Configuration;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.REPTree;
import weka.core.Instances;
import weka.core.SerializationHelper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;

public class WekaModelEvaluator {

    private static final String DT = "DT";
    private static final String SVM = "SVM";
    private static final String KNN = "KNN";
    // arff path for test or eval
    private static final String arffPath = Configuration.RESULTS_PATH + "/Test_OldData.arff";
    // model generated from train set only in Weka
    private static final String DTmodelPath = Configuration.DT_MODEL;

    public static void main(String[] args) throws Exception {

        Evaluation eval = getEvalForClassifier(DT, DTmodelPath, arffPath);

        //print some statistics
        System.out.println("Accuracy: " + eval.pctCorrect());
        System.out.println("Precision: " + eval.weightedPrecision());
        System.out.println("Recall: " + eval.weightedRecall());
    }

    private static Evaluation getEvalForClassifier(String classifierName, String trainModelPath, String evalArffPath) throws Exception {
        AbstractClassifier classifier = null;
        switch (classifierName) {
            case DT:
                classifier = (REPTree) SerializationHelper.read(new FileInputStream(trainModelPath));
                break;
            case SVM:
                classifier = (SMO) SerializationHelper.read(new FileInputStream(trainModelPath));
                break;
            case KNN:
                classifier = (IBk) SerializationHelper.read(new FileInputStream(trainModelPath));
        }
        BufferedReader reader = new BufferedReader(
                new FileReader(evalArffPath));
        Instances data = new Instances(reader);
        int classIndex = data.numAttributes() - 1;
        reader.close();
        data.setClassIndex(classIndex);

        // evaluate classifier
        Evaluation eval = new Evaluation(data);
        eval.evaluateModel(classifier, data);

        return eval;
    }
}