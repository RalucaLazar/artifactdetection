package business.features.export;

import entity.Feature;
import entity.FeatureType;
import entity.Segment;
import org.jdom2.Document;

import java.util.HashMap;
import java.util.Map;


public class DataGeneratorForDecisionTree {

    private DecisionTreeXMLWriter xmlWriter;
    private Document document;

    public DataGeneratorForDecisionTree() {
        generateFile();
    }


    public void writeSegment(Segment segment) {
        Map<String, String> pair = new HashMap<>();
        for (Feature feat : segment.getFeatures()) {
            pair.put(feat.getFeature().toString(), Double.toString(feat.getValue()));
        }
        pair.put("Label", segment.getCorrectType().toString());
        xmlWriter.addExample(document, "training", pair);
        System.out.println("Added a segment " + segment.getChannelNr() + " " + segment.getIterIdx() + " " + segment.getInitIdx());
    }

    private void generateFile() {
        xmlWriter = new DecisionTreeXMLWriter();
        document = xmlWriter.createXML();
        for (FeatureType feature : FeatureType.values()) {
            xmlWriter.addParameter(document, "input", feature.toString());
        }
        xmlWriter.addParameter(document, "output", "Label");
    }

}
