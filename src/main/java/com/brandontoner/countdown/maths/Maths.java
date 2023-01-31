package com.brandontoner.countdown.maths;

import java.util.*;

public class Maths {
    private static final int TARGET = 930;
    private static final int[] INPUTS = new int[] { 50, 75, 10, 5, 1, 7 };

    public static void main(String[] args) {
        var run = new ArrayList<>(runList(Arrays.stream(INPUTS).sorted().mapToObj(IValue::constantValue).toList()));
        run.sort(Comparator.comparingInt(v -> v.complexity()));
        for (IValue s : run) {
            System.err.println(s);
        }
    }

    public static Collection<IValue> runList(List<? extends IValue> values) {
        Set<IValue> output = new HashSet<>();
        for (int i = 0; i < values.size(); i++) {
            IValue valueI = values.get(i);
            for (int j = 0; j < i; j++) {
                IValue valueJ = values.get(j);
                for (IntegerOperator operator : IntegerOperator.values()) {
                    Optional<IValue> newValue = operator.applyValue(valueI, valueJ);
                    if (newValue.isEmpty()) {
                        continue;
                    }
                    IValue e = newValue.get();
                    if (e.value() == TARGET) {
                        output.add(e);
                    } else {
                        List<IValue> newValues = new ArrayList<>(values);
                        newValues.remove(i);
                        newValues.remove(j);
                        newValues.add(e);
                        output.addAll(runList(newValues));
                    }
                }
            }
        }
        return output;
    }
}
