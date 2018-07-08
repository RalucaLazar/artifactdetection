package view.scenemaker;

import classifier.Classifier;
import classifier.decisiontree.DecisionTreeClassifier;
import classifier.knn.KNNClassifier;
import classifier.svm.SVMClassifier;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;

import static view.scenemaker.ListOfChannelsSceneMaker.channelComboBox;
import static view.scenemaker.ListOfChannelsSceneMaker.regionComboBox;
import static view.util.FileUtil.regionsAndChannels;

@SuppressWarnings("restriction")
public abstract class AbstractSceneMaker {

    protected final static int LENGTH_STAGE = 1000;
    protected final static int HIGH_STAGE = 700;
    protected Stage stage;
    private Menu menu1;
    private Menu menu2;
    private Menu menu3;
    private Menu menu4;

    private static String inputFilesPath = "";

    public AbstractSceneMaker(Stage stage) {
        this.stage = stage;
    }

    public abstract Scene makeScene();

    public static int getLengthStage() {
        return LENGTH_STAGE;
    }

    public static int getHighStage() {
        return HIGH_STAGE;
    }

    public static String getInputFilesPath() {
        return inputFilesPath;
    }

    public static void setInputFilesPath(String path) {
        inputFilesPath = path;
    }

    protected MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        menu1 = new Menu("Choose Folder");
        menu2 = new Menu("Visualize EEG");
        menu3 = new Menu("Detect Artifacts");
        menu4 = new Menu("Train & Test");

        if (inputFilesPath.equals("")) {
            disableButtons();
        }

        MenuItem menuItem11 = new MenuItem("Choose Folder");
        menuItem11.setOnAction(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFolder = fileChooser.getSelectedFile();
                String path = selectedFolder.getAbsolutePath();
                setInputFilesPath(path);
                enableButtons();
                regionsAndChannels.clear();
                if (regionComboBox != null) {
                    regionComboBox.getItems().clear();
                }
                if (channelComboBox != null) {
                    channelComboBox.getItems().clear();
                }
                System.out.println("Selected path: " + path);

            }
        });

        menu1.getItems().add(menuItem11);

        MenuItem menuItem21 = new MenuItem("Single channel");
        menuItem21.setOnAction(e -> {
            System.out.println("Single channel visualization");
            ListOfChannelsSceneMaker sm = new ListOfChannelsSceneMaker(stage);
            stage.setScene(sm.makeScene());
        });

        MenuItem menuItem22 = new MenuItem("Multiple channels");
        menuItem22.setOnAction(e -> {
            System.out.println("Multiple channels visualization");
            ListOfRegionsSceneMaker sm = new ListOfRegionsSceneMaker(stage);
            stage.setScene(sm.makeScene());
        });
        menu2.getItems().addAll(menuItem21, menuItem22);

        MenuItem menuItem31 = new MenuItem("KNN");
        menuItem31.setOnAction(e -> {
            System.out.println("KNN classifier");
            Classifier knn = new KNNClassifier();
            ListOfChannelsMulticlassClassificationSceneMaker sm = new ListOfChannelsMulticlassClassificationSceneMaker(
                    stage, knn, false);
            stage.setScene(sm.makeScene());
        });
        MenuItem menuItem32 = new MenuItem("Decision tree");
        menuItem32.setOnAction(e -> {
            System.out.println("DT classifier");
            Classifier dt = new DecisionTreeClassifier();
            ListOfChannelsMulticlassClassificationSceneMaker sm = new ListOfChannelsMulticlassClassificationSceneMaker(
                    stage, dt, false);
            stage.setScene(sm.makeScene());
        });
        MenuItem menuItem33 = new MenuItem("SVM");
        menuItem33.setOnAction(e -> {
            System.out.println("SVM classifier");
            Classifier svm = new SVMClassifier();
            ListOfChannelsMulticlassClassificationSceneMaker sm = new ListOfChannelsMulticlassClassificationSceneMaker(
                    stage, svm, false);
            stage.setScene(sm.makeScene());
        });

        menu3.getItems().addAll(menuItem31, menuItem32, menuItem33);

        MenuItem menuItem41 = new MenuItem("KNN");
        menuItem41.setOnAction(e -> {
            System.out.println("KNN classifier");
            Classifier knn = new KNNClassifier();
            ListOfChannelsMulticlassClassificationSceneMaker sm = new ListOfChannelsMulticlassClassificationSceneMaker(
                    stage, knn, true);
            stage.setScene(sm.makeScene());
        });
        MenuItem menuItem42 = new MenuItem("Decision tree");
        menuItem42.setOnAction(e -> {
            System.out.println("DT classifier");
            Classifier dt = new DecisionTreeClassifier();
            ListOfChannelsMulticlassClassificationSceneMaker sm = new ListOfChannelsMulticlassClassificationSceneMaker(
                    stage, dt, true);
            stage.setScene(sm.makeScene());
        });
        MenuItem menuItem43 = new MenuItem("SVM");
        menuItem43.setOnAction(e -> {
            System.out.println("SVM classifier");
            Classifier svm = new SVMClassifier();
            ListOfChannelsMulticlassClassificationSceneMaker sm = new ListOfChannelsMulticlassClassificationSceneMaker(
                    stage, svm, true);
            stage.setScene(sm.makeScene());
        });

        menu4.getItems().addAll(menuItem41, menuItem42, menuItem43);

        menuBar.getMenus().addAll(menu1, menu2, menu3, menu4);
        menuBar.prefWidthProperty().bind(stage.widthProperty());
        menuBar.prefHeight(HIGH_STAGE / 20);
        menuBar.setMaxHeight(HIGH_STAGE / 10);
        return menuBar;
    }

    private void disableButtons() {
        menu2.setDisable(true);
        menu3.setDisable(true);
        menu4.setDisable(true);
    }

    private void enableButtons() {
        menu2.setDisable(false);
        menu3.setDisable(false);
        menu4.setDisable(false);
    }
}
