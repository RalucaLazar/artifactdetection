package business.labels;

import com.google.common.collect.Range;
import entity.ArtifactType;
import entity.ArtifactsStructure;
import entity.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ArtifactsLabelsExtractor {

//	private Logger logger = LoggerUtil.logger(ArtifactsLabelsExtractor.class);


    public Map<ArtifactType, ArtifactsStructure> parseLabelsDirectory(String directoryName) {
        File folder = new File(directoryName);
        Map<ArtifactType, ArtifactsStructure> artifactsStructures = new HashMap<>();
        Map<Integer, List<Range<Integer>>> ocularMap = new HashMap<>();
        Map<Integer, List<Range<Integer>>> muscleMap = new HashMap<>();
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                String fileName =fileEntry.getName();
                List<Range<Integer>> ranges = parseFile(fileEntry);
                if (ArtifactType.OCULAR.equals(getArtifactTypeFromFile(fileName))) {
                    ocularMap.put(getChannelFromFile(fileName), ranges);
                } else {
                    //MUSCLE
                    muscleMap.put(getChannelFromFile(fileName), ranges);
                }
            }
        }
        artifactsStructures.put(ArtifactType.OCULAR, new ArtifactsStructure(ArtifactType.OCULAR, ocularMap));
        artifactsStructures.put(ArtifactType.MUSCLE, new ArtifactsStructure(ArtifactType.MUSCLE, muscleMap));
        return artifactsStructures;
    }

    private List<Range<Integer>> parseFile(File file) {
        List<Range<Integer>> ranges = new ArrayList<>();

        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextDouble()) {
                Integer low = computeIndexFromTime(scan);
                Integer high = computeIndexFromTime(scan);
                Range<Integer> range = Range.closed(low, high);
                ranges.add(range);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ranges;
    }

    private int computeIndexFromTime(Scanner scan) {
        int val = scan.nextInt();
//		logger.info(val);
        return (int) (val * Configuration.RATE);
    }


    // Labels file name: TYPE_CHANNEL.txt

    private ArtifactType getArtifactTypeFromFile(String file) {
        return ArtifactType.valueOf(file.split("_")[0]);
    }

    private int getChannelFromFile(String file) {
        String channel = file.split("_")[1];
        return Integer.parseInt(channel.substring(0, channel.length() - 4));
    }
}
