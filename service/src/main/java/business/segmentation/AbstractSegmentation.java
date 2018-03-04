package business.segmentation;

import business.builders.AbstractStructureBuilder;
import entity.SegmentRepository;

import java.io.File;
import java.util.List;

/**
 * Created by Cristina on 3/4/2018.
 */
public abstract class AbstractSegmentation {

    //	protected Logger logger = LoggerUtil.logger(Segmentation.class);
    private FixedWindowSegmentation fws;

    public AbstractSegmentation(AbstractStructureBuilder sb) {
        fws = new FixedWindowSegmentation(sb);
    }

    public List<SegmentRepository> parseDataDirectory(File folder) {
        int i = 0;
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                //logger.info(fileEntry.getAbsolutePath());
                parseFile(fileEntry, i++);
            }
        }
        return fws.getStructureBuilder().getSerializableStructures();
    }

    protected abstract void parseFile(File file, int index);

    protected void segment(List<Double> data, int index, int channel, int type) {
        fws.segment(data.stream().mapToDouble(i -> i).toArray(), index, channel, type);
    }

    protected int getChannelFromFile(String file) {
        String delims = "[._]+";
        String[] tokens = file.split(delims);
        for (String token : tokens) {
            try {
                return Integer.parseInt(token);
            } catch (NumberFormatException e) {
                continue;
            }
        }
        return -1;
    }
}
