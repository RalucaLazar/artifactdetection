package classifier.decisiontree;

import classifier.WekaClassifier;
import entity.Configuration;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.trees.REPTree;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class DecisionTreeClassifier extends WekaClassifier {

    private REPTree REPTreeClassifier;

    public DecisionTreeClassifier() {
        REPTreeClassifier = new REPTree();
    }

    @Override
    public AbstractClassifier createClassifier() {
        System.out.println("Loading REPTree WEKA model...");
        try {
            REPTreeClassifier = (REPTree) SerializationHelper.read(new FileInputStream(Configuration.DT_MODEL));
            System.out.println("REPTree Weka model loaded");
        } catch (ClassNotFoundException e) {
            System.out.println("Invalid classifier object loaded, Loading ignored." + e);
        } catch (FileNotFoundException e) {
            System.out.println("Model file not found at " + Configuration.DT_MODEL + e);
        } catch (IOException e) {
            System.out.println("Model file read failed." + e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return REPTreeClassifier;
    }

    @Override
    public void trainClassifier(Instances trainingData, boolean crossValidate) {
        System.out.println("Training the classifier with " + trainingData.numInstances() + " instances");
        try {

            String[] options = Utils.splitOptions("weka.classifiers.trees.REPTree -M 2 -V 0.001 -N 3 -S 1 -L -1 -I 0.0");

            REPTreeClassifier.setOptions(options);
            REPTreeClassifier.buildClassifier(trainingData);

            if (crossValidate) {
                crossValidate(trainingData);
            }

            saveModel(new File(Configuration.CLASSIFIER_MODELS, "dt.model").getAbsolutePath());
            System.out.println("Model built and saved");
        } catch (Exception e) {
            System.out.println("Training classifier failed." + e);
        }
    }

    @Override
    public AbstractClassifier getClassifier() {
        return REPTreeClassifier;
    }
}
