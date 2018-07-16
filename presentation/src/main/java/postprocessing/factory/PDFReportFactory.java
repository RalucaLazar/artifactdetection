package postprocessing.factory;


import postprocessing.model.PDFReport;
import postprocessing.model.Report;

public class PDFReportFactory extends ReportFactory {

    public PDFReportFactory(String name){
        this.name = name;
    }

    public Report generateReport(){
        PDFReport report = new PDFReport(name);
        return report;
    }

}
