package business.labels;

import com.google.common.collect.Range;
import com.sun.xml.internal.ws.util.StringUtils;
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
        Map<Integer, List<Range<Integer>>> noiseMap = new HashMap<>();

        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (!fileEntry.isDirectory()) {
                String fileName = fileEntry.getName();
                List<Range<Integer>> ranges = parseFile(fileEntry);

                switch (getArtifactTypeFromFile(fileName)) {
                    case OCULAR:
                        ocularMap.put(getChannelFromFile(fileName), ranges);
                        break;
                    case MUSCLE:
                        muscleMap.put(getChannelFromFile(fileName), ranges);
                        break;
                    case NOISE:
                        noiseMap.put(getChannelFromFile(fileName), ranges);
                        break;
                }
            }
        }
        artifactsStructures.put(ArtifactType.OCULAR, new ArtifactsStructure(ArtifactType.OCULAR, ocularMap));
        artifactsStructures.put(ArtifactType.MUSCLE, new ArtifactsStructure(ArtifactType.MUSCLE, muscleMap));
        artifactsStructures.put(ArtifactType.NOISE, new ArtifactsStructure(ArtifactType.NOISE, noiseMap));
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
        return ArtifactType.valueOf(file.substring(0, file.indexOf("_")));
    }

    private int getChannelFromFile(String file) {
        String channel = file.split("_")[1];
        String ch = channel.substring(channel.indexOf("_") + 1, channel.lastIndexOf("."));
        return Integer.parseInt(ch);
    }
}
