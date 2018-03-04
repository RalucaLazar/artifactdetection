package business.segmentation;

import business.builders.SimpleStructureBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Cristina on 3/4/2018.
 */
public class SimpleSegmentation extends AbstractSegmentation {

    public SimpleSegmentation() {
        super(new SimpleStructureBuilder());
    }

    protected void parseFile(File file, int index) {
        int channel = getChannelFromFile(file.getName());
//		logger.info("Fisierul channel "+channel);
        List<Double> data = new ArrayList<>();
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextDouble()) {
                data.add(scan.nextDouble());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//		logger.info("Nr valori "+data.size());
        segment(data, index, channel, 1);
    }

}
