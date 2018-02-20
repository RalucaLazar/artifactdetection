package business.segmentation;

import entity.Configuration;
import entity.SegmentRepository;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Segmentation {
	
//	private Logger logger = LoggerUtil.logger(Segmentation.class);
	private FixedWindowSegmentation fws;
	
	public Segmentation(){
		fws=new FixedWindowSegmentation();
	}
	
	public List<SegmentRepository> parseDataDirectory(File folder){
		int i=0;
		for (final File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
//	        	logger.info(fileEntry.getAbsolutePath());
	            parseFile(fileEntry,i++);
	        }
	    }
		return fws.getStructureBuilder().getSerializableStructures();
	}

	private void parseFile(File file, int index){
		int channel = getChannelFromFile(file.getName());
		if(channel<65){
			return;
		}
//		logger.info("Fisierul channel "+channel);
		List<Double> data = new ArrayList<>();
		List<Double> testData = new ArrayList<>();
		List<Double> evalData = new ArrayList<>();
		int count = 0;
		try(Scanner scan = new Scanner(file)){
			while (scan.hasNextDouble()) {
				count++;
				if(count <= Configuration.TRAIN_MAX_INDEX){
					data.add(scan.nextDouble());
				}
				else if(count <= Configuration.TEST_MAX_INDEX){
					testData.add(scan.nextDouble());
				}
				else if(count <= Configuration.MAX_INDEX){
					evalData.add(scan.nextDouble());
				}
				else {
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
//		logger.info("Nr valori train "+data.size());
//		logger.info("Nr valori test "+testData.size());
//		logger.info("Nr valori eval "+evalData.size());
		segment(data, index,channel,1);
		segment(testData, index,channel,2);
		segment(evalData, index,channel,3);
	}
	
	private void segment(List<Double> data, int index, int channel, int type){
		fws.segment(data.stream().mapToDouble(i -> i).toArray(), index, channel,type);
	}
	
	private int getChannelFromFile(String file){
		String delims = "[._]+";
		String[] tokens = file.split(delims);
		for(String token:tokens){
			try{
				return Integer.parseInt(token);
			}catch(NumberFormatException e){
				continue;
			}
		}
		return -1;
	}
}