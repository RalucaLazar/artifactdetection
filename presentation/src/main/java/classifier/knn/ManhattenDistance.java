package classifier.knn;

public class ManhattenDistance implements Metric {

    public double getDistance(Record s, Record e) {
        assert s.getAttributes().length == e.getAttributes().length : "s and e are different types of records!";
        int numOfAttributes = s.getAttributes().length;
        double sum = 0;

        for (int i = 0; i < numOfAttributes; i++) {
            sum += Math.abs(s.getAttributes()[i] - e.getAttributes()[i]);
        }

        return sum;
    }

}