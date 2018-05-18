package view.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class FileUtil {

    public static int freq;
    public static Map<String, List<String>> regionsAndChannels = new TreeMap<>();

    public FileUtil() {
        computeRegions();
    }

    public void computeRegions() {
        int line = 0;
        File file = new File("/home/raluca/Documents/artefacte/EEG_depletie_part_1_2/2_06_11_2016/2_06_11_2016.txt");

        try {
            copyFileUsingJava7Files(new File("/home/raluca/Documents/artefacte/EEG_depletie_part_1_2/2_06_11_2016/2_06_11_2016.epd"),
                    file);

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                if (line == 8) {
                    double freqDouble = Double.parseDouble(sCurrentLine);
                    freq = (int) freqDouble;
                }
                if (line >= 153 && line < 281 && !sCurrentLine.equals("NON")) {
                    computeRegionMap(sCurrentLine);
                }
                line++;
            }

            System.out.println("Frecventa este: " + freq);
            System.out.println();

            for (Map.Entry<String, List<String>> entry : regionsAndChannels.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        deleteFile(file);
    }

    private static void computeRegionMap(final String sCurrentLine) {
        final String region = sCurrentLine.substring(0, 1);
        final String channel = sCurrentLine.substring(1);

        if (!regionsAndChannels.containsKey(region)) {
            regionsAndChannels.put(region, new ArrayList<>());
            regionsAndChannels.get(region).add(channel);
        } else {
            regionsAndChannels.get(region).add(channel);
        }
    }

    private static String changeFileExtension(final String file) {
        if (file.contains(".")) {
            int index = file.indexOf(".");
            String filename = file.substring(0, index);
            return filename + ".txt";
        } else {
            return file;
        }
    }

    private static void copyFileUsingJava7Files(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }

    private static void deleteFile(File file) {
        try {
            file.delete();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
