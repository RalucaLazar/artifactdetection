package classifier.feature_selection;

public class FeatureSelectionMain {

    public static void main(String[] args) {
        FeatureSelection featureSelection = new FeatureSelection();
        featureSelection.sortFeatureRanks();
        for (FeatureRank featureRank : featureSelection.getFeatureRanks()) {
            System.out.println(featureRank.getRank() + " " + featureSelection.getTypeIndex(featureRank.getType()) + " " + featureRank.getType().name());
        }
    }
}
