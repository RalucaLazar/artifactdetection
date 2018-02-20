package entity;

import java.util.Arrays;
import java.util.List;

public enum RegionNew {

    FL(Arrays.asList(12, 18, 19, 22, 23, 24, 26, 27)),
    FR(Arrays.asList(2, 3, 4, 5, 9, 10, 123, 124)),
    CL(Arrays.asList(7, 13, 20, 28, 29, 30, 34, 35, 36, 41)),
    CR(Arrays.asList(103, 104, 105, 106, 110, 111, 112, 116, 117, 118)),
    PL(Arrays.asList(31, 37, 42, 47, 51, 52, 53, 54, 61)),
    PR(Arrays.asList(78, 79, 80, 86, 87, 92, 93, 97, 98)),
    OL(Arrays.asList(58, 59, 60, 64, 65, 66, 67, 70, 71)),
    OR(Arrays.asList(76, 77, 83, 84, 85, 90, 91, 95, 96)),
    TL(Arrays.asList(39, 40, 44, 45, 46, 50, 57)),
    TR(Arrays.asList(100, 101, 102, 108, 109, 114, 115));

    private List<Integer> list;

    RegionNew(List<Integer> integers) {
        this.list = integers;
    }

    public List<Integer> getList() {
        return list;
    }

    public static RegionNew getRegionByChannel(int channel) {
        for (RegionNew region : RegionNew.values()) {
            if (region.getList().contains(channel))
                return region;
        }
        return null;
    }

    public static RegionNew getRegionByIndex(int index) {
        int featuresNr = MultiChannelFeatureType.values().length;
        if (index < featuresNr) {
            return RegionNew.FL;
        }
        if (index < featuresNr * 2) {
            return RegionNew.FR;
        }
        if (index < featuresNr * 3) {
            return RegionNew.CL;
        }
        if (index < featuresNr * 4) {
            return RegionNew.CR;
        }
        if (index < featuresNr * 5) {
            return RegionNew.PL;
        }
        if (index < featuresNr * 6) {
            return RegionNew.PR;
        }
        if (index < featuresNr * 7) {
            return RegionNew.OL;
        }
        if (index < featuresNr * 8) {
            return RegionNew.OR;
        }
        if (index < featuresNr * 9) {
            return RegionNew.TL;
        }
        if (index < featuresNr * 10) {
            return RegionNew.TR;
        }
        throw new IllegalArgumentException("Invalid index");
    }
}
