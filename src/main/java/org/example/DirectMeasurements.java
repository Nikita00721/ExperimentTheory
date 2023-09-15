package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DirectMeasurements {
    private int N;
    private double K, A, reliability;
    private double[] arr;
    StudentCoefficient studentCoefficient = new StudentCoefficient();

    public DirectMeasurements(int n, double k, double a, double reliability, double[] arr) {
        N = n;
        K = k;
        A = a;
        this.reliability = reliability;
        this.arr = arr;
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private double instrumentalError(){
        return K*A/100;
    }
    private double middleOf(double[] array){
        double result = 0;
        for (int i = 0; i < array.length; i++)
            result += array[i];
        return round(result/array.length, 3);
    }
    private double standardDeviationOf(double[] array){
        double middle = middleOf(array);
        double result = 0;
        for (int i = 0; i < array.length; i++){
            result += Math.pow(array[i] - middle, 2);
        }
        result /= N-1;
        return round(Math.sqrt(result), 3);
    }
    private double[] searchForMissIn(double[] array){
        double[] maybeMiss = new double[array.length];
        double middle = middleOf(array);
        double standardDeviation = standardDeviationOf(array);
        double leftBorderOfConfidenceInterval = middle - standardDeviation;
        double rightBorderOfConfidenceInterval = middle + standardDeviation;
        for (int i = 0; i < array.length; i++)
            if(array[i] < leftBorderOfConfidenceInterval || array[i] > rightBorderOfConfidenceInterval)
                maybeMiss[i] = 1;
            else
                maybeMiss[i] = 0;
        return maybeMiss;
    }
    private double normalizedDeviation(double element, double middle, double standardDeviation){
        return Math.abs(element-middle)/standardDeviation;
    }
    private boolean isMissByShovine(double Z, double N){
        double[] tableZ = {1,1.02,1.04,1.06,1.08,1.1,1.12,1.14,1.16,1.18,1.2,1.22,1.24,1.26,1.28,1.3,1.32,1.34,1.36,1.38,1.4,1.42,1.44,1.46,1.48,1.5,1.52,1.54,1.56,1.58,1.6,1.62,1.64,1.66,1.68,1.7,1.72,1.74,1.76,1.78,1.8,1.82,1.84,1.86,1.88,1.9,1.92,1.94,1.96,1.98,2,2.02,2.04,2.06,2.08,2.1,2.12,2.14,2.16,2.18,2.2,2.22,2.24,2.26,2.28,2.3,2.32,2.34,2.36,2.38,2.4,2.42,2.44,2.46,2.48,2.5,2.52,2.54,2.56,2.58,2.6,2.62,2.64,2.66,2.68,2.7,2.72,2.74,2.76,2.78,2.8,2.82,2.84,2.86,2.88,2.9,2.92,2.94,2.96,2.98};
        double[] howManyMeasurements = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,3,4,4,4,4,4,4,5,5,5,5,5,6,6,6,6,7,7,7,8,8,8,9,9,10,10,10,11,12,12,13,13,14,15,16,16,17,18,19,20,21,22,23,25,26,27,29,30,32,34,36,38,40,43,45,48,51,54,57,60,64,68,72,77,81,87,92,98,104,111,118,126,134,143,152,163,173};
        for(int i = 0; i < tableZ.length; i++){
            if (Z == tableZ[i]){
                if (N <= howManyMeasurements[i])
                    return true;
                return false;
            } else if(Z > tableZ[i]){
                continue;
            } else {
                if (N <= howManyMeasurements[i])
                    return true;
                return false;
            }
        }
        return false;
    }
    private double[] getNewArrWithOutAllMisses(double [] array){
        double[] maybeMiss = searchForMissIn(array);
        double middle = middleOf(array);
        double standardDeviation = standardDeviationOf(array);
        int howManyMiss = 0;
        for (int i = 0; i < maybeMiss.length; i++)
            if(maybeMiss[i] == 1) {
                System.out.print("Нормализуем значение " + arr[i] + ": Z = ");
                double Z = round(normalizedDeviation(array[i], middle, standardDeviation), 3);
                System.out.print(Z + ". При N = " + N + " по критерию Шовене это ");
                if (isMissByShovine(Z, N)){
                    array[i] = -1;
                    howManyMiss++;
                    System.out.println("промах");
                } else
                    System.out.println("не промах");
            }

        N -= howManyMiss;
        double[] newArr = new double[N];
        int indexForNewArr = 0;
        for (int i = 0; i < array.length; i++){
            if(array[i] == -1)
                continue;
            newArr[indexForNewArr++] = array[i];
        }
        return newArr;
    }
    private double randomErrorComponent(double standardDeviation, double studentCoefficient){
        return round((standardDeviation/Math.sqrt(N))*studentCoefficient, 3);
    }
    private double absoluteError(double instrumentalError, double randomErrorComponent){
        return round(Math.sqrt(instrumentalError*instrumentalError + randomErrorComponent+randomErrorComponent), 3);
    }
    private double relativeError(double absoluteError, double middle){
        return round(absoluteError/middle*100, 3);
    }

    public void doCalculations(){
        System.out.print("Исходные данные: [" + arr[0]);
        for (int i = 1; i < arr.length; i++)
            System.out.print(", " + arr[i]);
        System.out.println("]");
        System.out.println("Всего элементов: N = " + N);
        double iError = instrumentalError();
        System.out.println("Инструментальная погрешность: " + iError);
        double middle = middleOf(arr);
        System.out.println("Среднее значение: " + middle);
        double standardDeviation = standardDeviationOf(arr);
        System.out.println("Среднее квадратическое отклонение: " + standardDeviation);
        double leftBorder = middle - standardDeviation,
                rightBorder = middle + standardDeviation;
        System.out.println("Доверительный интервал: [" + leftBorder + "; " + rightBorder + "]");
        double[] maybeMisses = searchForMissIn(arr);
        System.out.println("Значения подозрительные на промахи: ");
        for (int i = 0; i < maybeMisses.length; i++)
            if(maybeMisses[i] == 1)
                System.out.println(+ arr[i]);
        System.out.println("Проверка на промахи: ");
        double[] newArr = getNewArrWithOutAllMisses(arr);
        System.out.print("Новые данные: [" + newArr[0]);
        for (int i = 1; i < newArr.length; i++)
            System.out.print(", " + newArr[i]);
        System.out.println("]");
        double sCoefficient = studentCoefficient.calculateStudentFor(N, reliability);
        System.out.println(
                "Коэффициент Стьюдента для " +
                        "n = " + N + " и p = " + reliability +
                        ": " + sCoefficient);
        middle = middleOf(newArr);
        System.out.println("Среднее значение: " + middle);
        standardDeviation = standardDeviationOf(newArr);
        System.out.println("Среднее квадратическое отклонение: " + standardDeviation);
        double randomErrorComponent = randomErrorComponent(standardDeviation, sCoefficient);
        System.out.println("Случайная составляющая погрешности: " + randomErrorComponent);
        double aError = absoluteError(iError, randomErrorComponent);
        System.out.println("Абсолютная погрешность: " + aError);
        double rError = relativeError(aError, middle);
        System.out.println("Относительная погрешность: " + rError + "%");
        System.out.println("Результат измерений: " + middle + "+-" + aError + ",   " + rError + "%,   " + reliability + "%");
    }
}

