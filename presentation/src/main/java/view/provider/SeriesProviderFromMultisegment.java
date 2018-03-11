package view.provider;


import entity.MultiChannelSegment;
import entity.Segment;
import view.chart.SeriesHolder;

import java.util.ArrayList;
import java.util.List;

public class SeriesProviderFromMultisegment {
	
	public List<SeriesHolder> obtainSearise(MultiChannelSegment segment){
		List<Segment> segments=segment.getSegments();
		List<SeriesHolder> series=new ArrayList<SeriesHolder>();
		for (Segment s :segments){
			double[] values=s.getValues();
			SeriesHolder seriesHolder=new SeriesHolder();
			seriesHolder.setSeries(values);
			series.add(seriesHolder);
		}
		return series;
	}

}
