package entity;

import java.util.Arrays;
import java.util.List;

public enum RegionNew {

    F(Arrays.asList(2, 3, 4, 5, 9, 10, 123, 124, 12, 18, 19, 22, 23, 24, 26, 27)),
    C(Arrays.asList(7, 13, 20, 28, 29, 30, 34, 35, 36, 41, 103, 104, 105, 106, 110, 111, 112, 116, 117, 118)),
    P(Arrays.asList(31, 37, 42, 47, 51, 52, 53, 54, 61, 78, 79, 80, 86, 87, 92, 93, 97, 98)),
    O(Arrays.asList(58, 59, 60, 64, 65, 66, 67, 70, 71, 76, 77, 83, 84, 85, 90, 91, 95, 96)),
    T(Arrays.asList(39, 40, 44, 45, 46, 50, 57, 100, 101, 102, 108, 109, 114, 115));

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
            return RegionNew.F;
        }
        if (index < featuresNr * 2) {
            return RegionNew.C;
        }
        if (index < featuresNr * 3) {
            return RegionNew.P;
        }
        if (index < featuresNr * 4) {
            return RegionNew.O;
        }
        if (index < featuresNr * 5) {
            return RegionNew.T;
        }
        throw new IllegalArgumentException("Invalid index");
    }

}
