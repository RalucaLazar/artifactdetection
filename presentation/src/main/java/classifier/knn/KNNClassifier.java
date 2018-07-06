package classifier.knn;

import classifier.WekaClassifier;
import entity.Configuration;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class KNNClassifier extends WekaClassifier {

    private IBk IBkClassifier;

    public KNNClassifier() {
        IBkClassifier = new IBk();
    }


    @Override
    public AbstractClassifier createClassifier() {
        System.out.println("Loading IBk WEKA model...");
        try {
            IBkClassifier = (IBk) SerializationHelper.read(new FileInputStream(Configuration.KNN_MODEL));
            System.out.println("IBk Weka model loaded");
        } catch (ClassNotFoundException e) {
            System.out.println("Invalid classifier object loaded, Loading ignored." + e);
        } catch (FileNotFoundException e) {
            System.out.println("Model file not found at " + Configuration.KNN_MODEL + e);
        } catch (IOException e) {
            System.out.println("Model file read failed." + e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return IBkClassifier;
    }

    @Override
    public void trainClassifier(Instances trainingData, boolean crossValidate) {
        System.out.println("Training the classifier with " + trainingData.numInstances() + " instances");
        try {

            String[] options = Utils.splitOptions("-K 15 -W 0 -A \"weka.core.neighboursearch.LinearNNSearch -A \\\"weka.core.ManhattanDistance -R first-last\\\"\"");

            IBkClassifier.setOptions(options);
            IBkClassifier.buildClassifier(trainingData);

            if (crossValidate) {
                crossValidate(trainingData);
            }

            saveModel(new File(Configuration.CLASSIFIER_MODELS, "knn.model").getAbsolutePath());
            System.out.println("Model built and saved");
        } catch (Exception e) {
            System.out.println("Training classifier failed." + e);
        }
    }

    @Override
    public AbstractClassifier getClassifier() {
        return IBkClassifier;
    }

}
