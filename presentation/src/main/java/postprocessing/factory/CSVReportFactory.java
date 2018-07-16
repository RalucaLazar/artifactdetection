package postprocessing.factory;

import postprocessing.model.CSVReport;
import postprocessing.model.Report;

public class CSVReportFactory extends ReportFactory {

    public CSVReportFactory(String name){
        this.name = name;
    }

    public Report generateReport(){
        CSVReport report = new CSVReport(name);
        return report;
    }

}
