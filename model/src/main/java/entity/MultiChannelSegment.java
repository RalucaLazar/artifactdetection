package entity;

import java.util.List;

public class MultiChannelSegment extends AbstractSegment implements Comparable {

    private static final long serialVersionUID = 1L;
    private List<Segment> segments;

    public MultiChannelSegment(List<Segment> segments) {
        this.segments = segments;
        this.correctType = setCorrectType();
    }

    public List<Segment> getSegments() {
        return this.segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    private ResultType setCorrectType() {
        for (Segment segment : this.segments) {
            if (ResultType.OCULAR.equals(segment.getCorrectType())) {
                return ResultType.OCULAR;
            }
            if (ResultType.MUSCLE.equals(segment.getCorrectType())) {
                return ResultType.MUSCLE;
            }
        }
        return ResultType.BRAIN_SIGNAL;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
