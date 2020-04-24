package com.example.test_utils;

import java.util.Objects;

public class Pair<T, U> {
    private T x;
    private U y;

    public Pair(T x, U y) {
        this.x = x;
        this.y = y;
    }

    public T getX() {
        return x;
    }

    public U getY() {
        return y;
    }

    @Override
    public String toString() {
        var strBuilder = new StringBuilder();
        strBuilder.append('(');
        strBuilder.append(x);
        strBuilder.append(", ");
        strBuilder.append(y);
        strBuilder.append(')');
        return strBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        var other = (Pair<?, ?>) obj;

        return x.equals(other.getX()) && y.equals(other.getY());
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
