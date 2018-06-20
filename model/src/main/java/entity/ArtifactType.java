package entity;

import com.google.common.collect.Range;

import java.util.List;

public enum ArtifactType {

    OCULAR,
    MUSCLE,
    NOISE;

    public boolean isSuitableForType(int channel) {
        //the channel is not included in any of the five regions (F, T, P, O, C)
        //it is in the NON region
        return RegionChannel.getRegionByChannel(channel) != null;
    }

    public Boolean isArtifact(Range<Integer> segmentRange, List<Range<Integer>> artifactRanges) {
        if (artifactRanges == null || segmentRange == null || artifactRanges.isEmpty()) {
            // no artifacts (OCULAR or MUSCLE) for the segment's channel
            return false;
        }
        if (disjunctiveIntersectionCondition(segmentRange, artifactRanges)) {
            return false;
        }
        boolean reject = false;
        for (Range<Integer> range : artifactRanges) {
            try {
                Range<Integer> intersection = segmentRange.intersection(range);
                if (getRangeSize(intersection) >= getRangeSize(segmentRange) / 5) {
                    return true;
                }
                reject = true;
            } catch (IllegalArgumentException exception) {
            }
        }
        if (reject) {
            return null;
        }
        return false;
    }

    private boolean disjunctiveIntersectionCondition(Range<Integer> segmentRange, List<Range<Integer>> artifactRanges) {
        return segmentRange.upperEndpoint() < artifactRanges.get(0).lowerEndpoint() || segmentRange.lowerEndpoint() > artifactRanges.get(artifactRanges.size() - 1).upperEndpoint();
    }

    private Integer getRangeSize(Range<Integer> range) {
        return range.upperEndpoint() - range.lowerEndpoint() + 1;
    }
}
