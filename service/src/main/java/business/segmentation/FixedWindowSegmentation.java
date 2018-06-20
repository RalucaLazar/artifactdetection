package business.segmentation;

import business.builders.AbstractStructureBuilder;
import entity.Configuration;

import java.util.Arrays;

public class FixedWindowSegmentation {

    private AbstractStructureBuilder sb;

    public FixedWindowSegmentation(AbstractStructureBuilder sb) {
        this.sb = sb;
    }

    public void segment(double[] allValues, int index, int channel, int type) {
        for (int i = 0; i < Configuration.WINDOW_SIZE; i += Configuration.STEP) {
            iterativeSegm(allValues, i, channel, type);
        }
    }

    private void iterativeSegm(double[] allValues, int start, int channel, int type) {
        int i = 0;
        for (i = start; i < allValues.length; i += Configuration.WINDOW_SIZE) {
            double[] values = Arrays.copyOfRange(allValues, i, i + Configuration.WINDOW_SIZE - 1);
            sb.buildDataStructures(values, start, i, channel, type);
        }
        if (i < allValues.length) {
            double[] values = Arrays.copyOfRange(allValues, i, allValues.length - 1);
            sb.buildDataStructures(values, start, i, channel, type);
        }
    }

    public AbstractStructureBuilder getStructureBuilder() {
        return sb;
    }
}
