package classifier.training_statistics;


import business.features.TimeFeatureExtractor;
import entity.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class StatisticsGenerator {

    private static void generateStatistics() {
        File folder = new File(Configuration.RESULTS_PATH + "/training");
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (!fileEntry.isDirectory()) {
                try {
                    List<TrainSet> sets = new ArrayList<>();
                    Scanner scanner = new Scanner(fileEntry);
                    while (scanner.hasNext()) {
                        float precision = scanner.nextFloat();
                        float recall = scanner.nextFloat();
                        sets.add(new TrainSet(precision, recall));
                    }
                    TimeFeatureExtractor extractor = new TimeFeatureExtractor();
                    System.out.println(fileEntry.getName());
                    System.out.println("    PRECISION");
                    System.out.println("        Mean: " + extractor.computeMean(getPrecision(sets)));
                    System.out.println("        Standard Deviation: " + extractor.computeStandardDeviation(getPrecision(sets)));

                    System.out.println("    RECALL");
                    System.out.println("        Mean: " + extractor.computeMean(getRecall(sets)));
                    System.out.println("        Standard Deviation: " + extractor.computeStandardDeviation(getRecall(sets)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static double[] getPrecision(List<TrainSet> sets) {
        double[] p = new double[sets.size()];
        int i = 0;
        for (TrainSet set : sets) {
            p[i] = set.getPrecision();
            i++;
        }
        return p;
    }

    private static double[] getRecall(List<TrainSet> sets) {
        double[] r = new double[sets.size()];
        int i = 0;
        for (TrainSet set : sets) {
            r[i] = set.getRecall();
            i++;
        }
        return r;
    }

    public static void main(String[] args) {
        generateStatistics();
    }
}
