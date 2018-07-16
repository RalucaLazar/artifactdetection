package postprocessing.model;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Configuration;
import entity.Segment;
import postprocessing.OutputRaportParameters;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.List;


public class PDFReport extends Report {

    private List<Segment> segments;

    public PDFReport(String name) {
        this.name = name;
    }

    public PDFReport(String name, List<Segment> segments) {
        this.name = name;
        this.segments = segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public void generateReport() {
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(Configuration.RESULTS_PATH + "/" + this.name + ".pdf"));
            document.open();

            String segmentsTypeFileContent = "";
            OutputRaportParameters reportParameters = new OutputRaportParameters(
                    segments);
            LinkedHashMap<Integer, Integer> overlappingSegmentsType = reportParameters
                    .getOverlappingSegmentsType();

            segmentsTypeFileContent = "Selected channel has " + reportParameters.getNoOfMuscularArtifacts()
                    + " muscular artifacts and "
                    + reportParameters.getNoOfOcularArtifacts()
                    + " ocular artifacts" + " from "
                    + overlappingSegmentsType.size() + " windows.\n";

            for (int i : overlappingSegmentsType.keySet()) {
                if (overlappingSegmentsType.get(i) == 1) {
                    segmentsTypeFileContent += i + ", Muscular artifact" + "\n";
                } else if (overlappingSegmentsType.get(i) == 2) {
                    segmentsTypeFileContent += i + ", Ocular artifact" + "\n";
                } else {
                    segmentsTypeFileContent += i + ", Brain" + "\n";
                }
            }
            document.add(new Paragraph(segmentsTypeFileContent));

            document.close();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
