package business.segmentation;

import business.builders.SimpleStructureBuilder;

import java.io.*;
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

    protected void parseFile(File file, int index) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        DataInputStream input = new DataInputStream(fileInputStream);
        int channel = getChannelFromFile(file.getName());
//		logger.info("Fisierul channel "+channel);
        List<Float> data = new ArrayList<>();
        while (input.available() > 0) {
            try {
                data.add(Float.intBitsToFloat(Integer.reverseBytes(input.readInt())));
            } catch (EOFException e) {
                e.printStackTrace();
            }
        }
//		logger.info("Nr valori "+data.size());
        segment(data, index, channel, 1);
    }

}
