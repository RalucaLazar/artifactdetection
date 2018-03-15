package entity;

import com.google.common.collect.Range;

import java.util.Arrays;
import java.util.List;

public enum ArtifactType {

    OCULAR_F(RegionNew.F),
    MUSCLE_F(RegionNew.F),
    MUSCLE_O(RegionNew.O),
    MUSCLE_T(RegionNew.T);

    private RegionNew regionNew;

    private ArtifactType(RegionNew regionNew) {
        this.regionNew = regionNew;
    }

    public List<Integer> getChannels() {
        return regionNew.getList();
    }

    public boolean isSuitableForType(int channel) {
        for (int channel1 : regionNew.getList()) {
            if (channel == channel1) return true;
        }
        return false;
    }

    public Boolean isArtifact(Range<Integer> segmentRange, List<Range<Integer>> artifactRanges) {
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
