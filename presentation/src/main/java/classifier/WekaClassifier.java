package classifier;

import entity.Configuration;
import entity.FeatureType;
import entity.ResultType;
import entity.Segment;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.*;
import weka.core.converters.ConverterUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class WekaClassifier implements Classifier {


    @Override
    public List<Segment> classifySegments(List<Segment> segments) {
        List<Segment> predictedSegments = new ArrayList<>();
        AbstractClassifier classifier = createClassifier();
        ArrayList<Attribute> fvWekaAttributes =  createAttributesList();

        Instances instances = new Instances("Rel", fvWekaAttributes, segments.size());
        instances.setClassIndex(fvWekaAttributes.size()-1);

        for (Segment segment : segments) {
            Double result = classifyInstance(classifier, fvWekaAttributes, instances, segment);
            Segment predicted = createPredictedInstance(segment, result);
            predictedSegments.add(predicted);
        }
        return predictedSegments;
    }

    @Override
    public Segment classifySegment(Segment segment) {
        List<Segment> list = new ArrayList<>();
        list.add(segment);
        return classifySegments(list).get(0);
    }

    @Override
    public void createModel(String trainSetPath){
        ConverterUtils.DataSource trainSource = null;

        try {
            trainSource = new ConverterUtils.DataSource(trainSetPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Instances trainInstances = trainSource.getDataSet();
            trainInstances.setClassIndex(trainInstances.numAttributes() - 1);
            System.out.println("Dataset: \n");
            System.out.println(trainInstances);

            trainClassifier(trainInstances,true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract AbstractClassifier createClassifier();

    public abstract void trainClassifier(Instances trainingData, boolean crossValidate);

    public abstract AbstractClassifier getClassifier();



    private Double classifyInstance(AbstractClassifier classifier, ArrayList<Attribute> fvWekaAttributes,
                                    Instances instances, Segment segment) {
        Instance instance = new DenseInstance(FeatureType.values().length+1);
        for (int i=0; i<FeatureType.values().length; i++){
            instance.setValue(fvWekaAttributes.get(i), segment.getFeatureValueForFeature(FeatureType.values()[i]));
        }
        instance.setValue(fvWekaAttributes.get(FeatureType.values().length), segment.getCorrectType().toString());
        instances.add(instance);
        Double result = null;
        try {
            result = classifier.classifyInstance(instances.lastInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



    private ArrayList<Attribute> createAttributesList() {
        ArrayList<Attribute> fvWekaAttributes = new ArrayList<>();
        for (FeatureType featureType : FeatureType.values()) {
            Attribute attribute = new Attribute(featureType.name());
            fvWekaAttributes.add(attribute);
        }

        List<String> fvClassVal = new ArrayList<>();
        fvClassVal.add(ResultType.BRAIN_SIGNAL.name());
        fvClassVal.add(ResultType.OCULAR.name());
        fvClassVal.add(ResultType.MUSCLE.name());
        Attribute classAttribute = new Attribute("class", fvClassVal);
        fvWekaAttributes.add(classAttribute);
        return fvWekaAttributes;
    }

    private Segment createPredictedInstance(Segment segm, Double result) {
        Segment predicted = new Segment(segm.getValues());
        predicted.setCorrectType(ResultType.values()[result.intValue()]);
        predicted.setFeatures(segm.getFeatures());
        predicted.setInitIdx(segm.getInitIdx());
        predicted.setIterIdx(segm.getIterIdx());
        return predicted;
    }


    /**
     * Performs cross validation on the newly built model to validate it's accuracy and acceptance.
     *
     * @param data data set on which cross validation will be performed.
     */
    protected void crossValidate(Instances data) {
        System.out.println("Cross validating the model...");
        Evaluation eval;
        try {
            eval = new Evaluation(data);
            eval.crossValidateModel(getClassifier(), data, 10, new Random(1));
            System.out.println(eval.toSummaryString(true));
        } catch (Exception e) {
            System.out.println("Cross validation failed." + e);
        }
    }

    /**
     * Save the created model file to a file to be used later
     *
     * @param pathToSaveModel the path where the new model will be saved.
     */
    protected void saveModel(String pathToSaveModel) {
        System.out.println("Saving model file...");
        if (getClassifier() != null) {
            ObjectOutputStream oos;
            try {
                oos = new ObjectOutputStream(new FileOutputStream(pathToSaveModel));
                oos.writeObject(getClassifier());
                oos.flush();
                oos.close();
                System.out.println("Model file saved");
            } catch (IOException e) {
                System.out.println("Saving model file failed." + e);
            }
        } else {
            System.out.println("Model is not initiated, saving aborted");
        }
    }

}
