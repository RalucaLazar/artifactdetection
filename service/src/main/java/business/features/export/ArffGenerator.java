package business.features.export;

import entity.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ArffGenerator {

    private final BufferedWriter writer;

    public ArffGenerator(String name) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(name, true));
        generateArff(name);
    }

    private void generateArff(String name) throws IOException {

        String data = writeAttributes();

        this.writer.write(data);
        this.writer.flush();
    }

    private String writeAttributes() {
        String data = "@relation eegArtifacts\n";
        for (FeatureType feature : FeatureType.values()) {
            data += writeAttribute(feature);
        }
        data += "@attribute 'class' {" + ResultType.BRAIN_SIGNAL + ", " + ResultType.MUSCLE + ", " + ResultType.OCULAR + "}\n";
        data += "@data\n";
        return data;
    }

    private String writeAttribute(FeatureType featureType) {
        return "@attribute '" + featureType.toString() + "' real\n";
    }
    
}
