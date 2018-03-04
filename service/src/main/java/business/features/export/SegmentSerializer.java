package business.features.export;



import entity.Segment;
import entity.SegmentRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class SegmentSerializer {

	//private static final Logger logger = LoggerUtil.logger(SegmentSerializer.class);

	public static void serialize(SegmentRepository segment, String path) {

		try {
			File dir = getOutputDir(path);

			File outputFile = new File(dir, segment.getName() + ".ser");
			FileOutputStream fileOut = new FileOutputStream(outputFile);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(segment);
			out.close();
			fileOut.close();
			//logger.info("Serialized data " + segment.getName() + " is saved");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	private static File getOutputDir(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	public static void serializeList(List<Segment> segments, File outputFile)
	{

		try
		{
			FileOutputStream fileOut = new FileOutputStream(outputFile);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(segments);
			out.close();
			fileOut.close();
		}
		catch (IOException i)
		{
			i.printStackTrace();
		}
	}
}
