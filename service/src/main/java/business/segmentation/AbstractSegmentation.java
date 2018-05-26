package business.segmentation;

import business.builders.AbstractStructureBuilder;
import entity.SegmentRepository;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Cristina on 3/4/2018.
 */
public abstract class AbstractSegmentation {

    //	protected Logger logger = LoggerUtil.logger(Segmentation.class);
    private static FixedWindowSegmentation fws;

    public AbstractSegmentation(AbstractStructureBuilder sb) {
        fws = new FixedWindowSegmentation(sb);
    }

    public List<SegmentRepository> parseDataDirectory(File folder) {
        final AtomicInteger i = new AtomicInteger();
        final ExecutorService executor = Executors.newFixedThreadPool(8);
        Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .filter(fileEntry -> !fileEntry.isDirectory())
                .forEach(fileEntry ->
                {
                    executor.submit(new SegmentationThread(fileEntry, i.getAndIncrement()));
                });

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return fws.getStructureBuilder().getSerializableStructures();

    }

    public static void segment(List<Float> data, int index, int channel, int type) {
        fws.segment(data.stream().mapToDouble(i -> i).toArray(), index, channel, type);
    }

    public static int getChannelFromFile(String file) {
        String delims = "[._]+";
        String[] tokens = file.split(delims);
        for (String token : tokens) {
            try {
                return Integer.parseInt(token);
            } catch (NumberFormatException e) {
//                e.printStackTrace();
            }
        }
        return -1;
    }
}
