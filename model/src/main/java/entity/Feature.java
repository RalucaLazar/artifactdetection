package entity;

import java.io.Serializable;

public class Feature implements Serializable {

    private static final long serialVersionUID = 2L;
    private FeatureType feature;
    private double value;
    private RegionNew region;

    public Feature(FeatureType feature) {
        this.feature = feature;
    }

    public Feature(MultiChannelFeatureType multiChannelFeatureType) {
        this.feature = FeatureType.valueOf(multiChannelFeatureType.toString());
    }

    public Feature(MultiChannelFeatureType multiChannelFeatureType, RegionNew region) {
        this(multiChannelFeatureType);
        this.region = region;
    }

    public FeatureType getFeature() {
        return feature;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public RegionNew getRegion() {
        return region;
    }

    @Override
    public String toString() {
        return "Pair [featureName=" + feature + ", valueName=" + value + "]";
    }

}
