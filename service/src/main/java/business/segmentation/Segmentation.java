package business.segmentation;

import business.builders.StructureBuilder;
import entity.Configuration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Segmentation extends AbstractSegmentation {

    public Segmentation() {
        super(new StructureBuilder());
    }

    public static void parseFile(File file, int index) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream input = new DataInputStream(fileInputStream);
        int channel = getChannelFromFile(file.getName());
        System.out.println(channel);
        List<Float> data = new ArrayList<>();
        List<Float> testData = new ArrayList<>();
        List<Float> evalData = new ArrayList<>();
        int count = 0;
        // & count < see nr. of values
        while (input.available() > 0) {
            try {
                count++;
                if (count <= Configuration.TRAIN_MAX_INDEX) {
                    data.add(Float.intBitsToFloat(Integer.reverseBytes(input.readInt())));
                } else if (count <= Configuration.TEST_MAX_INDEX) {
                    testData.add(Float.intBitsToFloat(Integer.reverseBytes(input.readInt())));
                } else if (count <= Configuration.MAX_INDEX) {
                    evalData.add(Float.intBitsToFloat(Integer.reverseBytes(input.readInt())));
                } else {
                    break;
                }
            } catch (EOFException e) {
                break;
            }
        }
        segment(data, index, channel, 1);
        segment(testData, index, channel, 2);
        segment(evalData, index, channel, 3);
    }
}