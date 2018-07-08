package view.util;

import entity.RegionChannel;
import view.scenemaker.AbstractSceneMaker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class FileUtil {

    private static int freq;
    public static Map<String, List<String>> regionsAndChannels = new TreeMap<>();
    private String folderPath;
    private Map<String, List<Integer>> regionsAndChannels1 = new HashMap<>();

    public FileUtil() {
        folderPath = AbstractSceneMaker.getInputFilesPath();
    }

    public void computeRegions() {
        int line = 0;

        File epdFile = getEpdFileFromPath(folderPath);
        String fileName = getFileName(epdFile);
        File txtFile = new File(folderPath + "/" + fileName + ".txt");

        try {
            copyFileUsingJava7Files(epdFile, txtFile);

            FileReader fr = new FileReader(txtFile);
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

        } catch (IOException e) {
            e.printStackTrace();
        }

        regionsAndChannels.forEach((k, v) -> regionsAndChannels1.put(k, convertValuesToInteger(v)));
        RegionChannel.setRegionsAndChannels(regionsAndChannels1);

        deleteFile(txtFile);
    }

    private String getFileName(File epdFile) {
        String fileName = "";
        if (epdFile != null) {
            System.out.println(epdFile.getName());
            String[] parts = epdFile.getName().split("\\.");
            fileName = parts[0];
        }
        return fileName;
    }

    private File getEpdFileFromPath(String folderPath) {
        File folder = new File(folderPath);
        for (File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.getName().contains(".epd"))
                return fileEntry;
        }
        return null;
    }

    private static void computeRegionMap(final String sCurrentLine) {
        final String region = sCurrentLine.substring(0, 1);
        final String channel = sCurrentLine.substring(sCurrentLine.lastIndexOf(" ") + 1);

        if (!region.equals("N")) {
            if (!regionsAndChannels.containsKey(region)) {
                regionsAndChannels.put(region, new ArrayList<>());
                regionsAndChannels.get(region).add(channel);
            } else {
                if (!regionsAndChannels.get(region).contains(channel)) {
                    regionsAndChannels.get(region).add(channel);
                }
            }
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

    private static List<Integer> convertValuesToInteger(final List<String> values) {
        return values.stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }
}
