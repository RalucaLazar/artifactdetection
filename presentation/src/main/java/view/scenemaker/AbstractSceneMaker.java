package view.scenemaker;

import classifier.Classifier;
import classifier.decisiontree.DecisionTreeClassifier;
import classifier.knn.KnnClassifier;
import classifier.svm.SvmClassesClassifier;
import entity.Configuration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;

@SuppressWarnings("restriction")
public abstract class AbstractSceneMaker {

    protected final static int LENGTH_STAGE = 1000;
    protected final static int HIGH_STAGE = 700;
    protected Stage stage;
    private Menu menu1;
    private Menu menu2;
    private Menu menu3;
    //    Logger logger = LoggerUtil.logger(getClass());
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

        menu1 = new Menu("Load file");
        menu2 = new Menu("Visualize EEG");
        menu3 = new Menu("Extract artefact from EEG");

        if (inputFilesPath.equals("")) {
            disableButtons();
        }

        MenuItem menuItem11 = new MenuItem("Load from ...");
        menuItem11.setOnAction(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFolder = fileChooser.getSelectedFile();
                String path = selectedFolder.getAbsolutePath();
                setInputFilesPath(path);
                enableButtons();
                System.out.println("Selected path: " + path);
                ListOfChannelsSceneMaker sm = new ListOfChannelsSceneMaker(stage);
                stage.setScene(sm.makeScene());
            }
        });

        menu1.getItems().add(menuItem11);

        MenuItem menuItem21 = new MenuItem("Single channel processing");
        menuItem21.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                ListOfChannelsSceneMaker sm = new ListOfChannelsSceneMaker(
                        stage);
                stage.setScene(sm.makeScene());
            }
        });

        MenuItem menuItem22 = new MenuItem("Multiple channel processing");
        menuItem22.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
//                logger.info("Visualize Multiple channel processing");
                ListOfRegionsSceneMaker sm = new ListOfRegionsSceneMaker(stage);
                stage.setScene(sm.makeScene());
            }
        });
        menu2.getItems().add(menuItem21);
        menu2.getItems().add(menuItem22);

        Menu menu31 = new Menu("Single channel processing");
        Menu menuItem311 = new Menu("Binary classification");

        MenuItem menuItem3111 = new MenuItem("SVM");
        menuItem3111.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                ListOfChannelsBinaryClassificationSceneMaker sm = new ListOfChannelsBinaryClassificationSceneMaker(
                        stage, "svm");
                stage.setScene(sm.makeScene());
            }
        });
        MenuItem menuItem3112 = new MenuItem("Error correction clasificators");
        menuItem3112.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                ListOfChannelsBinaryClassificationSceneMaker sm = new ListOfChannelsBinaryClassificationSceneMaker(
                        stage, "all");
                stage.setScene(sm.makeScene());
            }
        });
        menuItem311.getItems().addAll(menuItem3111, menuItem3112);

        Menu menu312 = new Menu("Multiclass classification");
        MenuItem menuItem3121 = new MenuItem("KNN");
        menuItem3121.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                Classifier knn = new KnnClassifier();
                ListOfChannelsMulticlassClassificationSceneMaker sm = new ListOfChannelsMulticlassClassificationSceneMaker(
                        stage, knn);
                stage.setScene(sm.makeScene());
//                logger.info("Knn");
            }
        });
        MenuItem menuItem3122 = new MenuItem("Decision tree");
        menuItem3122.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                Classifier dt = new DecisionTreeClassifier();
                ListOfChannelsMulticlassClassificationSceneMaker sm = new ListOfChannelsMulticlassClassificationSceneMaker(
                        stage, dt);
                stage.setScene(sm.makeScene());
//                logger.info("DT");
            }
        });
        MenuItem menuItem3123 = new MenuItem("SVM");
        menuItem3123.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                Classifier svm = new SvmClassesClassifier();
                ListOfChannelsMulticlassClassificationSceneMaker sm = new ListOfChannelsMulticlassClassificationSceneMaker(
                        stage, svm);
                stage.setScene(sm.makeScene());
//                logger.info("SVM");
            }
        });
        menu312.getItems().addAll(menuItem3121, menuItem3122, menuItem3123);
        menu31.getItems().addAll(menuItem311, menu312);

        menu3.getItems().addAll(menu31);

        menuBar.getMenus().addAll(menu1, menu2, menu3);
        menuBar.prefWidthProperty().bind(stage.widthProperty());
        menuBar.prefHeight(HIGH_STAGE / 20);
        menuBar.setMaxHeight(HIGH_STAGE / 10);
        return menuBar;
    }

    private void disableButtons() {
        menu2.setDisable(true);
        menu3.setDisable(true);
    }

    private void enableButtons() {
        menu2.setDisable(false);
        menu3.setDisable(false);
    }
}
