package postprocessing;

import entity.Segment;
import helpers.OutputFileWriter;

import java.util.LinkedHashMap;
import java.util.List;

public class ClasesFileGenerator extends AbstractFileGenerator {

	@Override
	public String outputStatistics(List<Segment> segments) {
		String segmentsTypeFileContent = "";
		OutputRaportParameters reportParameters = new OutputRaportParameters(
				segments);
		LinkedHashMap<Integer, Integer> overlappingSegmentsType = reportParameters
				.getOverlappingSegmentsType();

		segmentsTypeFileContent = "Channel " + segments.get(0).getChannelNr()
				+ " has " + reportParameters.getNoOfMuscularArtifacts()
				+ " muscular artifacts and "
				+ reportParameters.getNoOfOcularArtifacts()
				+ " ocular artifacts" + " from "
				+ overlappingSegmentsType.size() + " windows.\n";

		segmentsTypeFileContent += "0 = brain signal, 1 = muscle artifact, 2 = ocular artifact \n";

		for (int i : overlappingSegmentsType.keySet()) {
			segmentsTypeFileContent += i + ", "
					+ overlappingSegmentsType.get(i) + "\n";
		}

		String filePath = OUTPUT_FILENAME + "statisticsClasses"
				+ segments.get(0).getChannelNr() + ".dat";

		OutputFileWriter.getInstance().writeToFile(segmentsTypeFileContent,
				filePath);

		return filePath;
	}
}
