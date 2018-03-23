package entity;

import com.google.common.collect.Range;

import java.util.List;
import java.util.Map;

public class ArtifactsStructure {

    private ArtifactType type;
    private Map<Integer, List<Range<Integer>>> ranges;

    public ArtifactsStructure(ArtifactType type, Map<Integer, List<Range<Integer>>> ranges) {
        this.type = type;
        this.ranges = ranges;
    }

    public ArtifactType getType() {
        return type;
    }

    public Map<Integer, List<Range<Integer>>> getRanges() {
        return ranges;
    }
}
