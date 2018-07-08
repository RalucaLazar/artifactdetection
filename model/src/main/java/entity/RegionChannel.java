package entity;

import java.util.List;
import java.util.Map;

public class RegionChannel {

    private static Map<String, List<Integer>> regionsAndChannels;

    public static void setRegionsAndChannels(Map<String, List<Integer>> regionsAndChannels) {
        RegionChannel.regionsAndChannels = regionsAndChannels;
    }

    public static Map<String, List<Integer>> getRegionsAndChannels() {
        return regionsAndChannels;
    }

    public static String getRegionByChannel(int channel) {
        for (String region : regionsAndChannels.keySet()) {
            if (regionsAndChannels.get(region).contains(channel))
                return region;
        }
        return null;
    }

    public static List<Integer> getChannelsForRegion(String region){
        return regionsAndChannels.get(region);
    }
}
