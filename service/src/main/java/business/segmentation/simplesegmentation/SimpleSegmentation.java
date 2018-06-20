package business.segmentation.simplesegmentation;

import business.builders.SimpleStructureBuilder;
import business.segmentation.AbstractSegmentation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static entity.Configuration.MAX_INDEX;
import static entity.Configuration.TEST_MAX_INDEX;
import static entity.Configuration.TRAIN_MAX_INDEX;

/**
 * Created by Cristina on 3/4/2018.
 */
public class SimpleSegmentation extends AbstractSegmentation {

    public SimpleSegmentation() {
        super(new SimpleStructureBuilder());
    }

    public static void parseFile(File file, int index) throws IOException {
        final DataInputStream input = new DataInputStream(new FileInputStream(file));
        int channel = getChannelFromFile(file.getName());
        List<Double> allData = new ArrayList<>();

        while (input.available() > 0) {
            allData.add(Double.longBitsToDouble(Integer.reverseBytes(input.readInt())));
        }
        segment(allData, index, channel, 1);
    }

}
