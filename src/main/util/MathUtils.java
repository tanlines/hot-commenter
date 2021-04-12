package main.util;

public class MathUtils {

    public static int max(int a, int b, int... c) {

        int max = Math.max(a, b);

        if (c != null)
        for (int value : c) {
            max = Math.max(value, max);
        }

        return max;
    }
    public static int min(int a, int b, int... c) {

        int min = Math.min(a, b);

        if (c != null)
            for (int value : c) {
                min = Math.min(value, min);
            }

        return min;
    }

    public static void main(String[] args) {
        assert MathUtils.max(1,2,3,4,1,5,2,6,3,7,2,7) == 7;

        assert MathUtils.max(1,7) == 7;
    }
}
