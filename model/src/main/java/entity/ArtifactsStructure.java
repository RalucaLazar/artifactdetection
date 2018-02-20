package entity;

import com.google.common.collect.Range;

import java.util.List;

public class ArtifactsStructure {

	private ArtifactType type; 
	private List<Range<Integer>> ranges;
	
	public ArtifactsStructure(ArtifactType type,List<Range<Integer>> ranges) {
		this.type = type; 
		this.ranges =ranges;
	}

	public ArtifactType getType() {
		return type;
	}

	public List<Range<Integer>> getRanges() {
		return ranges;
	}
}
