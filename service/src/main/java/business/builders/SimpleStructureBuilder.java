package business.builders;

import com.google.common.collect.Lists;
import entity.Segment;
import entity.SegmentRepository;

import java.util.List;

/**
 * Created by Cristina on 2/20/2018.
 */
public class SimpleStructureBuilder extends AbstractStructureBuilder {

    private SegmentRepository segmentRepository;

    public SimpleStructureBuilder() {
        super();
        this.segmentRepository = new SegmentRepository("All_Segments");
    }

    public void buildDataStructures(double[] data, int iter, int localIndex, int channel, int type) {
        int index = localIndex;
        Segment segment = createSegment(data, iter, index, channel);
        segmentRepository.addSegment(segment);
    }

    public List<SegmentRepository> getSerializableStructures() {
        return Lists.newArrayList(segmentRepository);
    }
}
