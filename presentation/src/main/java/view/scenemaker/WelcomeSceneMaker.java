package view.scenemaker;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WelcomeSceneMaker extends AbstractSceneMaker {

	public WelcomeSceneMaker(Stage stage) {
		super(stage);
	}

	private Button btnLoad;

	@Override
	public Scene makeScene() {
		VBox hBox = new VBox();
		Image image = new Image("file:src/resources/welcome2.jpg");
		ImageView iv = new ImageView();
		iv.setFitWidth(super.LENGTH_STAGE);
		iv.setFitHeight(super.HIGH_STAGE);
		iv.setImage(image);		
		hBox.getChildren().addAll(createMenuBar(),iv);
		Scene scene = new Scene(hBox, LENGTH_STAGE, HIGH_STAGE);
		scene.getStylesheets().add("file:src/resources/stylesheet.css");
		return scene;
	}

	
	
}
