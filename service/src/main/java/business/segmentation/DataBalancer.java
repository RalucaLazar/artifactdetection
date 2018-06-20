package business.segmentation;

import entity.AbstractSegment;
import entity.SegmentRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataBalancer {
    //add SegmentRepository muscular as parameter
    public List<AbstractSegment> applyUndesample(SegmentRepository clean, SegmentRepository occular, SegmentRepository muscular) {
        int szOccular = occular.getSegments().size();
        int szMuscular = muscular.getSegments().size();
        List<AbstractSegment> resultList = new ArrayList<>();
        if (szOccular < szMuscular) {
            resultList.addAll(occular.getSegments());
            resultList.addAll(pickRandomly(szOccular, muscular.getSegments()));
            resultList.addAll(pickRandomly(szOccular, clean.getSegments()));
        } else if (szOccular > szMuscular) {
            resultList.addAll(muscular.getSegments());
            resultList.addAll(pickRandomly(szMuscular, occular.getSegments()));
            resultList.addAll(pickRandomly(szMuscular, clean.getSegments()));
        } else {
            resultList.addAll(muscular.getSegments());
            resultList.addAll(occular.getSegments());
            resultList.addAll(pickRandomly(szOccular, clean.getSegments()));
        }
        Collections.shuffle(resultList);
        return resultList;
    }


    private List<AbstractSegment> pickRandomly(int value, List<AbstractSegment> allSegments) {
        Collections.shuffle(allSegments);
        return allSegments.subList(0, value);
    }

}
