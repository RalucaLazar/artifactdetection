package business.segmentation;

import business.builders.StructureBuilder;
import entity.Configuration;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static entity.Configuration.MAX_INDEX;
import static entity.Configuration.TEST_MAX_INDEX;
import static entity.Configuration.TRAIN_MAX_INDEX;

public class Segmentation extends AbstractSegmentation {

    public Segmentation() {
        super(new StructureBuilder());
    }

    public static void parseFile(File file, int index) throws IOException {
        int channel = getChannelFromFile(file.getName());
        System.out.println(channel);
        List<Double> trainData = new ArrayList<>();
        List<Double> testData = new ArrayList<>();
        List<Double> evalData = new ArrayList<>();

        List<Double> values = new ArrayList<>();
        DataInputStream input = new DataInputStream(new FileInputStream(file));

        while (input.available() > 0) {
            try {
                values.add((double) Float.intBitsToFloat(Integer.reverseBytes(input.readInt())));
            } catch (EOFException e) {
                System.out.println("Error");
            }
        }

        System.out.println(values.size());

        for (int i = 0; i < values.size(); i++) {
            if (i <= TRAIN_MAX_INDEX) {
                trainData.add(values.get(i));
            } else if (i <= TEST_MAX_INDEX) {
                testData.add(values.get(i));
            } else if (i <= MAX_INDEX) {
                evalData.add(values.get(i));
            }
        }

        segment(trainData, index, channel, 1);
        segment(testData, index, channel, 2);
        segment(evalData, index, channel, 3);
    }
}