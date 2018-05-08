package classifier.feature_selection;

import entity.FeatureType;

public class FeatureRank {

    private float rank;
    private FeatureType type;
    private int ranksNumber;

    FeatureRank(FeatureType type) {
        this.type = type;
        this.rank = 0;
        this.ranksNumber = 0;
    }

    public float getRank() {
        return rank;
    }

    public void computeRank(float rank) {
        float newRank = this.rank * ranksNumber + rank;
        ranksNumber++;
        this.rank = newRank / ranksNumber;
    }

    public FeatureType getType() {
        return type;
    }

}
