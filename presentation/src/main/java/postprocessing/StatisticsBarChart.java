package postprocessing;

import entity.Segment;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities;
import postprocessing.model.CSVUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class StatisticsBarChart extends ApplicationFrame {

   List<Segment> segments;
   
   public StatisticsBarChart(String applicationTitle , String chartTitle, List<Segment> segments ) {
      super( applicationTitle );
      this.segments = segments;

      JFreeChart barChart = ChartFactory.createBarChart(
         chartTitle,           
         "Category",            
         "Score",            
         createDataset(),          
         PlotOrientation.VERTICAL,           
         true, true, false);
         
      ChartPanel chartPanel = new ChartPanel( barChart );        
      chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );        
      setContentPane( chartPanel ); 
   }
   
   private CategoryDataset createDataset( ) {
      final String brain = "brain";
      final String ocular = "ocular";
      final String muscular = "muscular";
      final String count = "count";
      int brainSegments = 0;
      int ocularSegments = 0;
      int muscularSegments = 0;

      final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

      OutputRaportParameters reportParameters = new OutputRaportParameters(
              segments);
      LinkedHashMap<Integer, Integer> overlappingSegmentsType = reportParameters
              .getOverlappingSegmentsType();

      for (int i : overlappingSegmentsType.keySet()) {
         if (overlappingSegmentsType.get(i) == 1) {
            muscularSegments++;
         } else if (overlappingSegmentsType.get(i) == 2) {
            ocularSegments++;
         } else {
            brainSegments++;
         }
      }

      if(muscularSegments!=0){
         dataset.addValue(muscularSegments, muscular,count);
         dataset.addValue(ocularSegments, ocular,count);
         dataset.addValue(brainSegments, brain,count);
      }else{
         dataset.addValue(ocularSegments, ocular,count);
         dataset.addValue(brainSegments, brain,count);
      }

      return dataset;
   }

   public static void createChart(List<Segment> segments){
      StatisticsBarChart chart = new StatisticsBarChart("Artifact Detection",
              "Statistics chart", segments);
      chart.pack( );
      RefineryUtilities.centerFrameOnScreen( chart );
      chart.setVisible( true );
   }
}