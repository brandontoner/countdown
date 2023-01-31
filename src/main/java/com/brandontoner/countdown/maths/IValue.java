package com.brandontoner.countdown.maths;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

public abstract class IValue {
    public static Optional<IValue> operate(IValue a, IValue b, IntegerOperator operator) {
        OptionalInt v = operator.apply(a.value(), b.value());
        if (v.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new OperatorValue(operator, a, b, v.getAsInt()));
    }

    public static IValue constantValue(int v) {
        return new ConstantValue(v);
    }

    public abstract int value();

    public abstract String valueString();

    public abstract int complexity();

    @Override
    public String toString() {
        return valueString();
    }

    private static class OperatorValue extends IValue {
        private final IntegerOperator operator;
        private final IValue a;
        private final IValue b;
        private final int value;

        public OperatorValue(IntegerOperator operator, IValue a, IValue b, int value) {
            this.operator = operator;
            this.a = a;
            this.b = b;
            this.value = value;
        }

        @Override
        public int value() {
            return value;
        }

        @Override
        public String valueString() {
            return operator.apply(a.valueString(), b.valueString());
        }

        @Override
        public int complexity() {
            int toAdd = value < 0 ? 5 : 1; // heavily prefer solutions that stay positive
            return a.complexity() + b.complexity() + toAdd;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            OperatorValue that = (OperatorValue) o;
            if (operator != that.operator) {
                return false;
            }
            if (a.equals(that.a) && b.equals(that.b)) {
                return true;
            }
            if (operator.isCommutative() && a.equals(that.b) && b.equals(that.a)) {
                return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            if (a.value() < b.value()) {
                return Objects.hash(operator, a, b);
            } else {
                return Objects.hash(operator, b, a);
            }
        }
    }

    private static class ConstantValue extends IValue {
        private final int v;

        public ConstantValue(int v) {
            this.v = v;
        }

        @Override
        public int value() {
            return v;
        }

        @Override
        public String valueString() {
            return String.valueOf(v);
        }

        @Override
        public int complexity() {
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ConstantValue that = (ConstantValue) o;
            return v == that.v;
        }

        @Override
        public int hashCode() {
            return Objects.hash(v);
        }
    }
}
