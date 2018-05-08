package business.segmentation;

import entity.SegmentRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SegmentDeserializer {

    public static SegmentRepository deserializeSegmentsFromFile(String pathToFile) {
        SegmentRepository deserializedRepository = null;
        try {
            System.out.println("Now Deserializing Repository Class !!!");
            FileInputStream inputFileStream = new FileInputStream(pathToFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputFileStream);
            deserializedRepository = (SegmentRepository) objectInputStream.readObject();
            objectInputStream.close();
            inputFileStream.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }
        return deserializedRepository;
    }

}
