package business.features.export;

import entity.Configuration;
import entity.SegmentRepository;
import org.apache.log4j.Logger;

import java.util.List;

public class SegmentExporter {

	//private static Logger logger = LoggerUtil.logger(SegmentExporter.class);


	public static void exportAll(List<SegmentRepository> segmentRepositories){
		for (SegmentRepository segmentRepository : segmentRepositories) {
			SegmentSerializer.serialize(segmentRepository, Configuration.RESULTS_PATH);
			//logger.info("Serialized: "+segmentRepository);

		}
	}
}
