package main.java.utilities.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class arrays {

    public static boolean allBytesEquals(byte[][][] data, int channel, byte value) {
        for (byte[][] subData : data) {
            if (allBytesEquals(subData, channel, value) == false) {
                return false;
            }
        }
        return true;
    }


    public static boolean allBytesEquals(byte[][] data, int channel, byte value) {
        for (byte[] subData : data) {
            if (subData[channel] != value) {
                return false;
            }
        }
        return true;
    }


    // <editor-fold desc="Addition">
    public static double[] addArrays(double[] first, double[] second) {
        double[] retArray = new double[(first.length > second.length) ? first.length : second.length];
        double a, b;
        for (int index = 0; index < retArray.length; index++) {
            a = (index < first.length) ? first[index] : 0;
            b = (index < second.length) ? second[index] : 0;
            retArray[index] = a + b;
        }
        return retArray;
    }


    public static int[] addArrays(int[] first, int[] second) {
        int[] retArray = new int[(first.length > second.length) ? first.length : second.length];
        int a, b;
        for (int index = 0; index < retArray.length; index++) {
            a = (index < first.length) ? first[index] : 0;
            b = (index < second.length) ? second[index] : 0;
            retArray[index] = a + b;
        }
        return retArray;
    }


    public static double[] addArrays(double[] first, int[] second) {
        return addArrays(first, castToDoubleArray(second));
    }


    // Because addition is communative
    public static double[] addArrays(int[] first, double[] second) {
        return addArrays(second, first);
    }

    // </editor-fold>

    // <editor-fold desc="Subtraction">
    public static double[] subtractArrays(double[] first, double[] second) {
        return addArrays(first, scale(second, -1.0));
    }


    public static int[] subtractArrays(int[] first, int[] second) {
        return addArrays(first, scale(second, -1));
    }


    public static double[] subtractArrays(double[] first, int[] second) {
        return subtractArrays(first, castToDoubleArray(second));
    }


    public static double[] subtractArrays(int[] first, double[] second) {
        return subtractArrays(castToDoubleArray(first), second);
    }

    //</editor-fold>

    // <editor-fold desc="Casting">
    public static int[] castToIntArray(double[] array) {
        int[] retArray = new int[array.length];
        for (int index = 0; index < array.length; index++) {
            retArray[index] = (int) array[index];
        }
        return retArray;
    }


    public static double[] castToDoubleArray(int[] array) {
        double[] retArray = new double[array.length];
        for (int index = 0; index < array.length; index++) {
            retArray[index] = (double) array[index];
        }
        return retArray;
    }

    // </editor-fold>

    // <editor-fold desc="Vector Operations">
    public static double findMagnitude(double[] array) {
        double sum = 0.0;
        for (double d : array) {
            sum += d * d;
        }
        return Math.sqrt(sum);
    }


    public static double findMagnitude(int[] array) {
        double sum = 0.0;
        for (int d : array) {
            sum += d * d;
        }
        return Math.sqrt(sum);
    }


    public static double[] scale(double[] array, double scale) {
        double[] retArray = new double[array.length];
        for (int index = 0; index < array.length; index++) {
            retArray[index] = scale * array[index];
        }
        return retArray;
    }


    public static int[] scale(int[] array, int scale) {
        int[] retArray = new int[array.length];
        for (int index = 0; index < array.length; index++) {
            retArray[index] = scale * array[index];
        }
        return retArray;
    }
    // </editor-fold>

    // <editor-fold desc="Set Operations">

    public static <T> T[] union(T[] first, T[] second) {
        ArrayList<T> list = new ArrayList();
        for (T element : first) {
            list.add(element);
        }
        for (T element : second) {
            if (list.contains(element) == false) {
                list.add(element);
            }
        }
        return list.toArray(first);
    }


    public static <T> T[] intersection(T[] first, T[] second) {
        List<T> firstList = Arrays.asList(first),
                secondList = Arrays.asList(second);
        ArrayList<T> intersectionList = new ArrayList();
        for(T element : firstList){
            if(secondList.contains(element)){
                intersectionList.add(element);
            }
        }
        T[] retArray = Arrays.copyOf(first, intersectionList.size());
        retArray = intersectionList.toArray(retArray);
        return retArray;
    }

    // </editor-fold>

    private arrays() {

    }


}
