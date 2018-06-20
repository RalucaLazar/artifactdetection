package classifier.svm;

import classifier.WekaClassifier;
import entity.Configuration;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.functions.SMO;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SVMClassifier extends WekaClassifier {

    private SMO smoClassifier;

    public SVMClassifier() {
        smoClassifier = new SMO();
    }

    /**
     * Method used to create SMO classifier and load model for classifier.
     */
    @Override
    public SMO createClassifier() {
        System.out.println("Loading SMO WEKA model...");
        try {
            smoClassifier = (SMO) SerializationHelper.read(new FileInputStream(Configuration.SVM_MODEL));
            System.out.println(" SMO Weka model loaded");
        } catch (ClassNotFoundException e) {
            System.out.println("Invalid classifier object loaded, Loading ignored." + e);
        } catch (FileNotFoundException e) {
            System.out.println("Model file not found at " + Configuration.SVM_MODEL + e);
        } catch (IOException e) {
            System.out.println("Model file read failed." + e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return smoClassifier;
    }

    /**
     * Method used to train the classifier.
     *
     * @param trainingData  training data set.
     * @param crossValidate true if you want crossValidate.
     */

    @Override
    public void trainClassifier(Instances trainingData, boolean crossValidate) {
        System.out.println("Training the classifier with " + trainingData.numInstances() + " instances");
        try {
            String[] options = Utils.splitOptions("-C 10 -L 0.001 -P 1.0E-12 -N 0 -V -1 -W 1 -K \"weka.classifiers" +
                    ".functions.supportVector.PolyKernel -C 250007 -E 1.0\"");

            smoClassifier.setOptions(options);
            smoClassifier.buildClassifier(trainingData);

            if (crossValidate) {
                crossValidate(trainingData);
            }

            saveModel(new File(Configuration.CLASSIFIER_MODELS, "svm.model").getAbsolutePath());
            System.out.println("Model built and saved");
        } catch (Exception e) {
            System.out.println("Training classifier failed." + e);
        }
    }

    @Override
    public AbstractClassifier getClassifier() {
        return smoClassifier;
    }

}
