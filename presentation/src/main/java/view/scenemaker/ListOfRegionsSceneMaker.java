package view.scenemaker;

import entity.MultiChannelSegment;
import entity.MultiChannelSegmentSelector;
import entity.Segment;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.provider.MultipleSegmentProvider;
import view.provider.SimpleChannelSegmentProvider;
import view.util.FileUtil;

import java.util.List;

import static view.util.FileUtil.regionsAndChannels;

public class ListOfRegionsSceneMaker extends AbstractSceneMaker {

    private static final int NR_CHANNELS = 128;

    protected Label errorLabel = new Label("");
    public static ComboBox regionComboBox;
    final Button visualizeButton = new Button("Visualize!");


    public ListOfRegionsSceneMaker(Stage stage) {
        super(stage);
    }

    @Override
    public Scene makeScene() {
        VBox hBox = new VBox();
        HBox listBox = new HBox();
        listBox.getChildren().addAll(getInitialPane());
        listBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.getChildren().addAll(createMenuBar(), getInitialPane());
        hBox.setMinWidth(stage.getWidth());
//		hBox.setMinHeight(stage.getHeight());
        hBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(hBox, LENGTH_STAGE, HIGH_STAGE);
        scene.getStylesheets().add("file:src/resources/stylesheet.css");
        return scene;
    }

    private ScrollPane getInitialPane() {

        ScrollPane pane1 = new ScrollPane();
        HBox root = new HBox();

        VBox rootB = new VBox();
        HBox hboxB = new HBox();
        Label labelB = new Label("Headset configuration");
        hboxB.setAlignment(Pos.BASELINE_CENTER);
        hboxB.getChildren().addAll(labelB);

        Image image = new Image("file:src/resources/system_10_20.png");
        ImageView iv = new ImageView();
        iv.setFitWidth(580);
        iv.setFitHeight(620);
        iv.setImage(image);

        rootB.getChildren().addAll(hboxB, iv);
        rootB.setSpacing(10);
        rootB.setPadding(new Insets(10));

        Label channelsLabel = new Label("Channel no ");

        // used to populate the region/channel map
        FileUtil fileUtil = new FileUtil();
        fileUtil.computeRegions();

        if (regionsAndChannels.keySet().size() == 0 || regionsAndChannels.keySet().size() == 1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(".edp Warning");
            alert.setContentText("Your .epd file is not proper formatted!");

            alert.showAndWait();
        } else {
            // regions
            regionComboBox = new ComboBox();
            regionComboBox.getItems().addAll(regionsAndChannels.keySet());

        }

        addActionHandlerForButtonVizualize(visualizeButton);

        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(10, 30, 20, 50));
        grid.add(new Label("Choose channel set"), 0, 0);
        grid.add(new Label(" "), 0, 1);
        grid.add(new Label("Region: "), 0, 2);
        grid.add(regionComboBox, 1, 2);
        grid.add(new Label("Channel no:"), 0, 3);
        grid.add(visualizeButton, 0, 4);
        grid.add(errorLabel, 0, 5);

        root.getChildren().addAll(grid, rootB);
        root.setAlignment(Pos.CENTER_RIGHT);
        pane1.setContent(root);
        pane1.setPannable(true); // it means that the user should be able to pan
        // the viewport by using the mouse.

        return pane1;
    }

    /**
     * Sets the next view scene by reading the i-th channel !!!!! number of
     * channels from 1 to 128
     *
     * @param btn
     */
    protected void addActionHandlerForButtonVizualize(Button btn) {
        btn.setOnAction(event -> {
            if (regionComboBox.getValue() != null) {
                System.out.println("Region: " + regionComboBox.getValue().toString());

                MultipleSegmentProvider provider = new MultipleSegmentProvider(regionComboBox.getValue().toString());
                List<MultiChannelSegment> testSegm = provider.provideSegments();
                if (testSegm == null) {
                    errorLabel.setText("Channels not available!");
                } else {
                    MultipleSegmentViewSceneMaker sm = new MultipleSegmentViewSceneMaker(stage, testSegm, 0);
                    stage.setScene(sm.makeScene());
                }
            } else {
                errorLabel.setText("Choose the region!");
            }
        });
    }


    int getRegionComboBoxValue() {
        int regionIdx;
        if (regionComboBox.getValue() == null) {
            regionIdx = 0;
        } else {
            switch (regionComboBox.getValue().toString()) {
                case "A":
                    regionIdx = 0;
                    break;
                case "B":
                    regionIdx = 1;
                    break;
                case "C":
                    regionIdx = 2;
                    break;
                case "D":
                    regionIdx = 3;
                    break;
                default:
                    regionIdx = 0;
                    break;
            }
        }
        return regionIdx;
    }
}