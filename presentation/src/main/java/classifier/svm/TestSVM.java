package classifier.svm;

import entity.Configuration;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

public class TestSVM {

    public static void main(String[] args) {

        ConverterUtils.DataSource trainSource = null;
        ConverterUtils.DataSource testSource = null;

        SVMClassifier SVMImplementationClassifier = new SVMClassifier();
        try {
            trainSource = new ConverterUtils.DataSource("/home/raluca.sechel/Downloads/Test.arff");
            testSource = new ConverterUtils.DataSource("/home/raluca.sechel/Downloads/Test.arff");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Instances trainInstances = trainSource.getDataSet();
            trainInstances.setClassIndex(trainInstances.numAttributes() - 1);
            System.out.println("Dataset: \n");
            System.out.println(trainInstances);

            SVMImplementationClassifier.trainClassifier(trainInstances, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
