package view.scenemaker;

import classifier.Classifier;
import entity.Configuration;
import entity.Segment;
import helpers.LoggerUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import view.provider.SimpleChannelSegmentProvider;

import java.util.List;

public class ListOfChannelsMulticlassClassificationSceneMaker extends
        ListOfChannelsSceneMaker {

    Classifier clasiffier;
    private boolean train;


    public ListOfChannelsMulticlassClassificationSceneMaker(Stage stage,
                                                            Classifier clasiffier,
                                                            boolean train) {
        super(stage);
        this.clasiffier = clasiffier;
        this.train = train;
        visualizeButton.setText("Classify!");
    }

    public Classifier getClasiffier() {
        return clasiffier;
    }

    public void setClasiffier(Classifier clasiffier) {
        this.clasiffier = clasiffier;
    }


    @Override
    @SuppressWarnings("restriction")
    protected void addActionHandlerForButtonVizualize(Button btn) {
        btn.setOnAction(event -> {
            if (regionComboBox.getValue() != null) {
                System.out.println("Region: " + regionComboBox.getValue().toString());

                if (channelComboBox.getValue() != null) {
                    int nrChannel = Integer.parseInt(channelComboBox.getValue().toString());

                    System.out.println("Channel: " + nrChannel);

                    SimpleChannelSegmentProvider provider = new SimpleChannelSegmentProvider();
                    List<Segment> testSegm = provider.provideSegments(nrChannel);

                    //if we select Train & Test, we must create the model first
                    if(train){
                        clasiffier.createModel(Configuration.ARFF_TRAIN1_NAME);
                    }

                    if (testSegm == null) {
                        errorLabel.setText("Channel not available!");
                    } else {
                        List<Segment> classifiedSegm = clasiffier.classifySegments(testSegm);
                        SimpleSegmentLabeldViewSceneMaker sm = new SimpleSegmentLabeldViewSceneMaker(
                                stage, clasiffier, classifiedSegm, 0, 1, train);
                        stage.setScene(sm.makeScene());
                    }
                } else {
                    errorLabel.setText("Choose the channel!");
                }
            } else {
                errorLabel.setText("Choose the region and the channel!");
            }
        });
    }

}
