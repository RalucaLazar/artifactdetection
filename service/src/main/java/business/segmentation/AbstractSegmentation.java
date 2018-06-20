package business.segmentation;

import business.builders.AbstractStructureBuilder;
import business.segmentation.simplesegmentation.SimpleSegmentationThread;
import entity.SegmentRepository;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static business.segmentation.SegmentationType.SIMPLE;

/**
 * Created by Cristina on 3/4/2018.
 */
public abstract class AbstractSegmentation {

    private static FixedWindowSegmentation fws;

    public AbstractSegmentation(AbstractStructureBuilder sb) {
        fws = new FixedWindowSegmentation(sb);
    }

    public List<SegmentRepository> parseDataDirectory(File folder, SegmentationType segmentationType) {
        final AtomicInteger i = new AtomicInteger();
        final ExecutorService executor = Executors.newFixedThreadPool(8);
        Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .filter(fileEntry -> !fileEntry.isDirectory())
                .forEach(fileEntry -> {
                    if (SIMPLE.equals(segmentationType)) {
                        executor.submit(new SimpleSegmentationThread(fileEntry, i.getAndIncrement()));
                    } else {
                        executor.submit(new SegmentationThread(fileEntry, i.getAndIncrement()));
                    }
                });

        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return fws.getStructureBuilder().getSerializableStructures();

    }

    public static void segment(List<Double> data, int index, int channel, int type) {
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
