package postprocessing.factory;


import postprocessing.model.Report;

public abstract class ReportFactory {
    public String name;
    public abstract Report generateReport();

}
