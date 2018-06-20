package business.builders;

import business.features.FeatureExtractor;
import business.labels.ArtifactsLabelsExtractor;
import com.google.common.collect.Range;
import entity.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Cristina on 3/4/2018.
 */
public abstract class AbstractStructureBuilder {

    //	protected Logger logger = LoggerUtil.logger(StructureBuilder.class);
    private Map<ArtifactType, ArtifactsStructure> artifactsStructures;

    AbstractStructureBuilder() {
        ArtifactsLabelsExtractor artifactsLabelsExtractor = new ArtifactsLabelsExtractor();
        artifactsStructures = artifactsLabelsExtractor.parseLabelsDirectory(Configuration.RESOURCES_PATH + "/labels");
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

    public ResultType computeCorrectType(int startIndex, int channel) {
        int endIndex = startIndex + Configuration.WINDOW_SIZE;
        Range<Integer> segmentRange = Range.closed(startIndex, endIndex);

        if (ArtifactType.NOISE.isSuitableForType(channel)) {
            ArtifactsStructure noiseStructure = artifactsStructures.get(ArtifactType.NOISE);
            Boolean noise = ArtifactType.NOISE.isArtifact(segmentRange, noiseStructure.getRanges().get(channel));
            if (noise == null) {
                return null;
            }
            if (noise) {
                return null;
            }
        }

        if (ArtifactType.MUSCLE.isSuitableForType(channel)) {
            ArtifactsStructure muscleStructure = artifactsStructures.get(ArtifactType.MUSCLE);
            Boolean muscleResult = ArtifactType.MUSCLE.isArtifact(segmentRange, muscleStructure.getRanges().get(channel));
            if (muscleResult == null) {
                return null;
            }
            if (muscleResult) {
                return ResultType.MUSCLE;
            }
        }

        if (ArtifactType.OCULAR.isSuitableForType(channel)) {
            ArtifactsStructure ocularStructure = artifactsStructures.get(ArtifactType.OCULAR);
            Boolean ocularResult = ArtifactType.OCULAR.isArtifact(segmentRange, ocularStructure.getRanges().get(channel));
            if (ocularResult == null) {
                return null;
            }
            if (ocularResult) {
                return ResultType.OCULAR;
            } else {
                return ResultType.BRAIN_SIGNAL;
            }
        }
        return null;
    }
}