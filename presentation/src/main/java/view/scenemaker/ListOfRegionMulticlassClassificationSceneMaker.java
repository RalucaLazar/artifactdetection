package view.scenemaker;

import entity.MultiChannelSegmentSelector;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.provider.MultipleSegmentProvider;

public class ListOfRegionMulticlassClassificationSceneMaker extends ListOfRegionsSceneMaker{

	public ListOfRegionMulticlassClassificationSceneMaker(Stage stage) {
		super(stage);
	}
	
	protected void addActionHandlerForButtonVizualize(Button btn, int nrRegion) {
		btn.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				MultipleSegmentProvider provider = new MultipleSegmentProvider(MultiChannelSegmentSelector.A);
				MultipleSegmentLabeledViewSceneMaker sm = new MultipleSegmentLabeledViewSceneMaker(stage, provider.provideSegments(), 0);
				stage.setScene(sm.makeScene());

			}
		});
	}

}
