package view.scenemaker;

import classifier.Classifier;
import classifier.decisiontree.DecisionTreeClassifier;
import classifier.knn.KnnClassifier;
import classifier.svm.SvmBinaryClassifier;
import entity.Segment;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import postprocessing.ResultsValidator;
import view.provider.SimpleChannelSegmentProvider;

import java.util.List;

public class ListOfChannelsBinaryClassificationSceneMaker extends
        ListOfChannelsSceneMaker {

    private String clasificator;
//	Logger logger = LoggerUtil.logger(getClass());


    public ListOfChannelsBinaryClassificationSceneMaker(Stage stage,
                                                        String clasificator) {
        super(stage);
        this.clasificator = clasificator;
    }

    @Override
    @SuppressWarnings("restriction")
    protected void addActionHandlerForButtonVizualize(Button btn) {
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
//                logger.info("vizualize classify with " + clasificator);

                int regionIdx = getRegionComboBoxValue();
                int channelIdx = Integer.parseInt(channelComboBox.getValue()
                        .toString());
                int nrChannel = channelIdx + regionIdx * 32;
//                logger.info(channelIdx + " " + regionIdx + " Channel "
//                        + nrChannel);
                if (nrChannel >= 72) {
                    SimpleChannelSegmentProvider provider = new SimpleChannelSegmentProvider(
                            nrChannel);

                    List<Segment> testSegm = provider
                            .provideSegments(nrChannel);
                    if (testSegm == null) {
//                        logger.error("list of segments null");
                        errorLabel.setText("Channel not available!");
                    } else {
                        List<Segment> classifiedSegments = getCorrespondinClasifiedSegments(testSegm);
                        if (clasificator == null) {
//                            logger.error("classifier null");
                            errorLabel.setText("Choose a classifier!");
                        } else {
                            SimpleSegmentLabeldViewSceneMaker sm = new SimpleSegmentLabeledBinaryViewSceneMaker(
                                    stage, classifiedSegments, 0, clasificator);
                            stage.setScene(sm.makeScene());
                        }
                    }
                } else
                    errorLabel.setText("Channel not available!");
            }
        });
    }

    private List<Segment> getCorrespondinClasifiedSegments(
            List<Segment> testSegm) {
        Classifier svm = new SvmBinaryClassifier();
        List<Segment> classifiedSegments;
        List<Segment> svmSegments = svm.classifySegments(testSegm);
        if (clasificator.equalsIgnoreCase("all")) {
            Classifier knn = new KnnClassifier();
            List<Segment> knnSegments = knn.classifySegments(testSegm);
            Classifier dt = new DecisionTreeClassifier();
            List<Segment> dtSegments = dt.classifySegments(testSegm);
            ResultsValidator validator = new ResultsValidator();
            classifiedSegments = validator.validateClassificationResults(
                    svmSegments, knnSegments, dtSegments);
        } else
            classifiedSegments = svmSegments;
        return classifiedSegments;
    }

}
