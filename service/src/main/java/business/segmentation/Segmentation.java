package business.segmentation;

import business.builders.StructureBuilder;
import entity.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Segmentation extends AbstractSegmentation {

    public Segmentation() {
        super(new StructureBuilder());
    }

    protected void parseFile(File file, int index) {
        int channel = getChannelFromFile(file.getName());
        /*if(channel<65){
            return;
		}*/
//		logger.info("Fisierul channel "+channel);
        List<Double> data = new ArrayList<>();
        List<Double> testData = new ArrayList<>();
        List<Double> evalData = new ArrayList<>();
        int count = 0;
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextDouble()) {
                count++;
                if (count <= Configuration.TRAIN_MAX_INDEX) {
                    data.add(scan.nextDouble());
                } else if (count <= Configuration.TEST_MAX_INDEX) {
                    testData.add(scan.nextDouble());
                } else if (count <= Configuration.MAX_INDEX) {
                    evalData.add(scan.nextDouble());
                } else {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//		logger.info("Nr valori train "+data.size());
//		logger.info("Nr valori test "+testData.size());
//		logger.info("Nr valori eval "+evalData.size());
        segment(data, index, channel, 1);
        segment(testData, index, channel, 2);
        segment(evalData, index, channel, 3);
    }
}