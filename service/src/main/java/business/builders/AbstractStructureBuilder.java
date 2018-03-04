package business.builders;

import business.features.FeatureExtractor;
import business.labels.ArtifactsLabelsExtractor;
import com.google.common.collect.Range;
import entity.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Cristina on 3/4/2018.
 */
public abstract class AbstractStructureBuilder {

    //	protected Logger logger = LoggerUtil.logger(StructureBuilder.class);
    protected Map<ArtifactType, ArtifactsStructure> artifactsStructures;

    AbstractStructureBuilder() {
        ArtifactsLabelsExtractor artifactsLabelsExtractor = new ArtifactsLabelsExtractor();
        //artifactsStructures = artifactsLabelsExtractor.parseLabelsDirectory(Configuration.RESOURCES_PATH+ "/labels");
    }

    public abstract void buildDataStructures(double[] data, int iter, int localIndex, int channel, int type);

    public abstract List<SegmentRepository> getSerializableStructures();


    protected Segment createSegment(double[] data, int iter, int index, int channel, ResultType resultType) {
        Segment segment = new Segment(data);
        segment.setIterIdx(iter);
        segment.setChannelNr(channel);
        segment.setInitIdx(index);
        segment.setFeatures(computeFeaturesForSegment(data));
        segment.setCorrectType(resultType);
        return segment;
    }

    protected Segment createSegment(double[] data, int iter, int index, int channel) {
        Segment segment = new Segment(data);
        segment.setIterIdx(iter);
        segment.setChannelNr(channel);
        segment.setInitIdx(index);
        segment.setFeatures(computeFeaturesForSegment(data));
        //segment.setCorrectType(resultType);
        return segment;
    }

    private Feature[] computeFeaturesForSegment(double[] data) {
        Feature[] features = FeatureBuilder.createStandardFeaturesInstances();
        for (Feature feat : features) {
            if (FeatureExtractor.getFeatureValue(feat.getFeature(), data) == null) {
                return null;
            }
            feat.setValue(FeatureExtractor.getFeatureValue(feat.getFeature(), data));
        }
        return features;
    }

    public Feature[] computeMultiRegionFeaturesForSegment(double[] data) {
        Feature[] features = FeatureBuilder.createStandardMultiChannelFeaturesInstances();
        for (Feature feat : features) {
            if (FeatureExtractor.getFeatureValue(feat.getFeature(), data) == null) {
//				logger.error("Error with the feature extraction! in StructureBuilder[computeFeaturesForSegment]");
                return null;
            }
            feat.setValue(FeatureExtractor.getFeatureValue(feat.getFeature(), data));
        }
        return features;
    }

    public Feature[] computeMultiRegionFeaturesForMultiChannelSegment(List<Segment> segments) {
        Feature[] features = FeatureBuilder.createStandardMultiChannelFeaturesPerRegionInstances();
        Map<RegionNew, List<Segment>> segmentsGroupedByRegion = segments.stream()
                .collect(Collectors.groupingBy(s -> RegionNew.getRegionByChannel(s.getChannelNr())));
        for (Feature feature : features) {
            RegionNew region = feature.getRegion();
            FeatureType type = feature.getFeature();
            List<Segment> regionSegments = segmentsGroupedByRegion.get(region);
            double mean = 0;
            for (Segment segment : regionSegments) {
                mean += segment.getFeatureValueForFeature(type);
            }
            mean = mean / regionSegments.size();
            feature.setValue(mean);
        }
        return features;
    }

    public ResultType computeCorrectType(int startIndex, int channel) {
        int endIndex = startIndex + Configuration.WINDOW_SIZE;
        Range<Integer> segmentRange = Range.closed(startIndex, endIndex);
        if (ArtifactType.REJECT.isSuitableForType(channel)) {
            ArtifactsStructure structure = artifactsStructures.get(ArtifactType.REJECT);
            if (ArtifactType.REJECT.isArtifact(segmentRange, structure.getRanges()) == true) {
                return null;
            }
        }
        if (ArtifactType.MUSCLE_C.isSuitableForType(channel)) {
            ArtifactsStructure structure = artifactsStructures.get(ArtifactType.MUSCLE_C);
            Boolean result = ArtifactType.MUSCLE_C.isArtifact(segmentRange, structure.getRanges());
            if (result == null) {
                return null;
            }
            if (result) {
                return ResultType.MUSCLE;
            }
        }
        if (ArtifactType.OCCULAR.isSuitableForType(channel)) {
            ArtifactsStructure structure = artifactsStructures.get(ArtifactType.OCCULAR);
            Boolean result = ArtifactType.OCCULAR.isArtifact(segmentRange, structure.getRanges());
            if (result == null) {
                return null;
            }
            if (result) {
                return ResultType.OCCULAR;
            } else {
                return ResultType.BRAIN_SIGNAL;
            }
        }
        if (ArtifactType.MUSCLE_D.isSuitableForType(channel)) {
            ArtifactsStructure structure = artifactsStructures.get(ArtifactType.MUSCLE_D);
            Boolean result = ArtifactType.MUSCLE_D.isArtifact(segmentRange, structure.getRanges());
            if (result == null) {
                return null;
            }
            if (result) {
                return ResultType.MUSCLE;
            } else {
                return ResultType.BRAIN_SIGNAL;
            }
        }
        return null;
    }
}
