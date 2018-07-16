package postprocessing;

import entity.Configuration;
import entity.Segment;
import helpers.OutputFileWriter;

import java.util.LinkedHashMap;
import java.util.List;

public abstract class AbstractFileGenerator {

    protected static final String OUTPUT_FILENAME = Configuration.RESULTS_PATH
            + "/";

    /**
     * This method writes a binary file containing the eeg signal hold in
     * overlapping segments.
     *
     * @param segments A list of overlapping segments containing the eeg signal.
     * @return Path to the file generated in current system.
     */
    public String generateFileFromSegment(List<Segment> segments) {
        String cleanFileContent = "";
        double values[];
        int index = 0;
        OutputRaportParameters reportParameters = new OutputRaportParameters(
                segments);
        LinkedHashMap<Integer, Integer> segmentsType = reportParameters
                .getSegmentsType();
        LinkedHashMap<Integer, Segment> orderedSegments = reportParameters
                .getOrderedSegments();

        for (int i : segmentsType.keySet()) {
            index++;
            values = orderedSegments.get(i).getValues();
            if (segmentsType.get(i) == 0) { // clean signal
                for (int j = 0; j < values.length; j++) {
                    cleanFileContent += values[j] + " ";
                }
            } else {
                for (int j = 0; j < values.length; j++) {
                    cleanFileContent += 0 + " ";
                }
            }
        }

        String filePath = OUTPUT_FILENAME + "cleanEEG"
                + segments.get(0).getChannelNr() + ".dat";

        OutputFileWriter.getInstance().writeToFile(cleanFileContent, filePath);

        return filePath;
    }

    /**
     * Method for writing to a file the statistics about every segment
     *
     * @param segments A list of overlapping segments containing the eeg signal.
     * @return Path to the file generated in current system.
     */
    public abstract String outputStatistics(List<Segment> segments);

}
