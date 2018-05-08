package classifier.training_statistics;

public class TrainSet {
    private float precision;
    private float recall;

    TrainSet(float precision, float recall) {
        this.precision = precision;
        this.recall = recall;
    }

    public float getPrecision() {
        return precision;
    }

    public float getRecall() {
        return recall;
    }

}
