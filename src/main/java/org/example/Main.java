package org.example;

public class Main {
    public static void main(String[] args) {
//        Данные примера
//        int n = 10;
//        double k = 2.5;
//        double a = 200;
//        double reliability = 0.98;
//
//        double[] measurementValues = {145, 140, 145, 105, 130, 150, 150, 155, 175, 160};

        System.out.println("Вычисление силы тока:");
        double[] measurementValuesI = {14.1, 14.4, 15.7, 14.7, 15.1, 16.5, 14.2, 15.0, 16.3, 16.1};
        DirectMeasurements directMeasurementsForI = new DirectMeasurements(10, 0.5, 20, 0.90, measurementValuesI);
        directMeasurementsForI.doCalculations();

        System.out.println("-------------------------------------------");

        System.out.println("Вычисление сопротивления:");
        double[] measurementValuesR = {1.55, 1.65, 2.05, 1.90, 1.80, 2.55, 2.10, 2.05, 2.00, 1.90};
        DirectMeasurements directMeasurementsForR = new DirectMeasurements(10, 1, 5, 0.90, measurementValuesR);
        directMeasurementsForR.doCalculations();

        System.out.println("-------------------------------------------");

        IndirectMeasurements indirectMeasurements = new IndirectMeasurements();
//        indirectMeasurements.doExample();
        indirectMeasurements.doCalculations();

        System.out.println("-------------------------------------------");

        Conclusions conclusions = new Conclusions();
        conclusions.tusk3();
        System.out.println("-------------------------------------------");
        conclusions.tusk4and5();
    }
}
