package postprocessing.model;


import entity.Configuration;
import entity.Segment;
import postprocessing.OutputRaportParameters;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class CSVReport extends Report {

    private List<Segment> segments;

    public CSVReport(String name){
        this.name = name;
    }

    public CSVReport(String name, List<Segment> segments){
        this.name = name;
        this.segments = segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public void generateReport(){
        try {
            OutputRaportParameters reportParameters = new OutputRaportParameters(
                    segments);
            LinkedHashMap<Integer, Integer> overlappingSegmentsType = reportParameters
                    .getOverlappingSegmentsType();
            FileWriter writer = new FileWriter(Configuration.RESULTS_PATH+"/"+this.name+".csv");
            CSVUtils.writeLine(writer, Arrays.asList("Index","Type"));
            for (int i : overlappingSegmentsType.keySet()) {
                if (overlappingSegmentsType.get(i) == 1) {
                    CSVUtils.writeLine(writer, Arrays.asList(""+i, "Muscular artifact"));
                } else if (overlappingSegmentsType.get(i) == 2) {
                    CSVUtils.writeLine(writer, Arrays.asList(""+i, "Ocular artifact"));
                } else {
                    CSVUtils.writeLine(writer, Arrays.asList(""+i, "Brain"));
                }
            }
            writer.flush();
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
