
import javafx.application.Application;
import javafx.stage.Stage;
import view.scenemaker.WelcomeSceneMaker;

@SuppressWarnings("restriction")
public class App extends Application {

	@Override
	public void start(Stage stage) {
		stage.setTitle("EEG Processor");
		WelcomeSceneMaker sm = new WelcomeSceneMaker(stage);
		stage.setScene(sm.makeScene());
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) throws Exception {
//		LoggerUtil.configure();
		launch(args);
	}
}

