package view.chart;

import entity.Segment;
import javafx.scene.chart.LineChart;

public class SimpleSegmentChart {

	public LineChart<Number, Number> generateChartFromSegment(Segment segment) {
		ChartHolder ch = new ChartHolder("Segment starting from " +segment.getInitIdx() + " ms", false);
		ch.addSeries(segment.getValues(), "Segm");
		LineChart<Number, Number> lineChart = ch.getLineChart();
		return lineChart;
	}

}
