package br.ufu.facom.pcf.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Util {

    public static double calculateLabelPurity(final int[] samplesLabels) {

        final Map<Integer, Integer> hp = new HashMap<>();

        for (final int key : samplesLabels) {
            if (hp.containsKey(key)) {
                int freq = hp.get(key);
                freq++;
                hp.put(key, freq);
            } else {
                hp.put(key, 1);
            }
        }

        int max_count = 0;

        for(Map.Entry<Integer, Integer> val : hp.entrySet()) {
            if (max_count < val.getValue()) {
                max_count = val.getValue();
            }
        }

        return max_count / (double) samplesLabels.length;

    }

    public static double calculateCategoryPurity(final int[] samplesLabels, final Set<Integer> knownLabels) {

        int unseenLabels = 0;
        int seenLabels = 0;

        for (final int label : samplesLabels) {
            if (knownLabels.contains(label)) {
                ++seenLabels;
            } else {
                ++unseenLabels;
            }
        }

        return Math.max(unseenLabels, seenLabels) / (double) samplesLabels.length;

    }
}
