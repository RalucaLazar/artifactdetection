package business.builders;

import entity.Feature;
import entity.FeatureType;
import entity.MultiChannelFeatureType;

public class FeatureBuilder {

    public static Feature[] createStandardFeaturesInstances() {
        Feature[] features = new Feature[FeatureType.values().length];
        int i = 0;
        for (FeatureType feat : FeatureType.values()) {
            features[i++] = new Feature(feat);
        }
        return features;
    }

    public static Feature[] createStandardMultiChannelFeaturesInstances() {
        Feature[] features = new Feature[MultiChannelFeatureType.values().length];
        int i = 0;
        for (MultiChannelFeatureType feat : MultiChannelFeatureType.values()) {
            features[i++] = new Feature(feat);
        }
        return features;
    }

}
