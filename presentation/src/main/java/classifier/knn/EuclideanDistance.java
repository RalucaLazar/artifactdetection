package classifier.knn;

/**
 * This class is used for implementic a mtric by euclidian metric formula.
 *
 * @author Tolas Ramona
 */
public class EuclideanDistance implements Metric {

    public double getDistance(Record s, Record e) {
        assert s.getAttributes().length == e.getAttributes().length : "s and e are different types of records!";
        int numOfAttributes = s.getAttributes().length;
        double sum = 0;

        for (int i = 0; i < numOfAttributes; i++) {
            sum += Math.pow(s.getAttributes()[i] - e.getAttributes()[i], 2);
        }

        return Math.sqrt(sum);
    }

}
