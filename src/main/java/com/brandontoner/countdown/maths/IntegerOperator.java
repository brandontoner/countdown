package com.brandontoner.countdown.maths;

import java.util.Optional;
import java.util.OptionalInt;

public enum IntegerOperator {
    ADD {
        @Override
        public OptionalInt apply(int a, int b) {
            return OptionalInt.of(Math.addExact(a, b));
        }

        @Override
        public String apply(String a, String b) {
            return "(" + a + " + " + b + ")";
        }

        @Override
        public boolean isCommutative() {
            return true;
        }
    },
    SUBTRACT {
        @Override
        public OptionalInt apply(int a, int b) {
            int value = Math.subtractExact(a, b);
            return OptionalInt.of(value);
        }

        @Override
        public String apply(String a, String b) {
            return "(" + a + " - " + b + ")";
        }

        @Override
        public boolean isCommutative() {
            return false;
        }
    },
    SUBTRACT_INVERSE {
        @Override
        public OptionalInt apply(int a, int b) {
            return SUBTRACT.apply(b, a);
        }

        @Override
        public String apply(String a, String b) {
            return SUBTRACT.apply(b, a);
        }

        @Override
        public boolean isCommutative() {
            return false;
        }
    },
    MULTIPLY {
        @Override
        public OptionalInt apply(int a, int b) {
            // b == 1 is valid, but pointless
            if (b == 1) {
                return OptionalInt.empty();
            }
            return OptionalInt.of(Math.multiplyExact(a, b));
        }

        @Override
        public String apply(String a, String b) {
            return "(" + a + " * " + b + ")";
        }

        @Override
        public boolean isCommutative() {
            return true;
        }
    },
    DIVIDE {
        @Override
        public OptionalInt apply(int a, int b) {
            // b == 1 is valid, but pointless
            if (b == 0 || b == 1) {
                return OptionalInt.empty();
            }
            int v = a / b;
            if (b * v == a) {
                return OptionalInt.of(v);
            }
            return OptionalInt.empty();
        }

        @Override
        public String apply(String a, String b) {
            return "(" + a + " / " + b + ")";
        }

        @Override
        public boolean isCommutative() {
            return false;
        }
    },
    DIVIDE_INVERSE {
        @Override
        public OptionalInt apply(int a, int b) {
            return DIVIDE.apply(b, a);
        }

        @Override
        public String apply(String a, String b) {
            return DIVIDE.apply(b, a);
        }

        @Override
        public boolean isCommutative() {
            return false;
        }
    };

    public abstract OptionalInt apply(int a, int b);
    public abstract String apply(String a, String b);
    public abstract boolean isCommutative();
    public Optional<IValue> applyValue(IValue valueI, IValue valueJ) {
        return IValue.operate(valueI, valueJ, this);
    }
}
