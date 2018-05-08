package classifier.feature_selection;

import entity.Configuration;
import entity.FeatureType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class FeatureSelection {

    private FeatureRank[] featureRanks;

    FeatureSelection() {
        createFeatureRanks();
        readFeatureRanks();
    }

    public FeatureRank[] getFeatureRanks() {
        return featureRanks;
    }

    private void createFeatureRanks() {
        FeatureType[] featureTypes = FeatureType.values();
        featureRanks = new FeatureRank[featureTypes.length];
        for (int i = 0; i < featureTypes.length; i++) {
            featureRanks[i] = new FeatureRank(featureTypes[i]);
        }
    }

    private void readFeatureRanks() {
        File folder = new File(Configuration.RESULTS_PATH + "/ranks");
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (!fileEntry.isDirectory()) {
                try {
                    Scanner scanner = new Scanner(fileEntry);
                    while (scanner.hasNext()) {
                        float rank = scanner.nextFloat();
                        int index = scanner.nextInt();
                        scanner.next();
                        featureRanks[index - 1].computeRank(rank);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sortFeatureRanks() {
        int length = FeatureType.values().length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = i + 1; j < length; j++) {
                if (featureRanks[i].getRank() < featureRanks[j].getRank()) {
                    FeatureRank rank = featureRanks[i];
                    featureRanks[i] = featureRanks[j];
                    featureRanks[j] = rank;
                }
            }
        }
    }

    public int getTypeIndex(FeatureType type) {
        FeatureType[] featureTypes = FeatureType.values();
        for (int i = 0; i < featureTypes.length; i++) {
            if (featureTypes[i].equals(type))
                return i + 1;
        }
        return 0;
    }

}
