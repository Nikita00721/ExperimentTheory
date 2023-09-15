package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class IndirectMeasurements {
    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public void doExample(){
        double  abs_m = 310,    // в граммах
                rel_m = 6,
                abs_R = 104,    // в милиметрах
                rel_R = 5,
                abs_v = 30,     // в метрах за секунду
                rel_v = 1;

        System.out.println("Вычисляем среднее значение силы");
        double abs_F = round((abs_m/1000)*abs_v*abs_v/(abs_R/1000),0);
        System.out.println("F: " + abs_F);

        System.out.println("Находим частные производные и вычисляем их значения при средних значениях аргументов");
        double difFm = round(abs_v*abs_v/abs_R,1);
        double difFR = round(-abs_m*abs_v*abs_v/(abs_R*abs_R),1);
        double difFv = round(2*abs_m*abs_v/abs_R,1);
        System.out.println("Производная F по m = V^2/R: " + difFm);
        System.out.println("Производная F по R = m*V^2/R^2: " + difFR);
        System.out.println("Производная F по v = 2*m*V/R: " + difFv);

        System.out.println("Вычисляем составляющие погрешности от каждого аргумента");
        double errFm = round(Math.abs(difFm)*rel_m,1);
        double errFR = round(Math.abs(difFR)*rel_R,1);
        double errFv = round(Math.abs(difFv)*rel_v,1);
        System.out.println("Погрешность F по m: " + errFm);
        System.out.println("Погрешность F по R: " + errFR);
        System.out.println("Погрешность F по v: " + errFv);

        System.out.println("Вычисляем полную погрешность");
        double absErr = round(Math.sqrt(errFm*errFm + errFR*errFR + errFv*errFv), 1);
        System.out.println("Абсолютную: " + absErr);
        double relErr = round(absErr/abs_F,2);
        relErr *= 100;
        System.out.println("Относительную: " + relErr + "%");

        System.out.println("Результат: F = " + abs_F + "+-" + absErr + ", " + relErr + "%");

    }
    public void doCalculations(){
        // Значения приямых измерений:
        double  abs_I = 15,
                rel_I = 1,
                abs_R = 1.8,
                rel_R = 0.484;

        System.out.println("Формула мощности: P = I^2/R");
        System.out.println("Вычисляем среднее значение мощности");
        double abs_P = round(abs_I*abs_I*abs_R,3);
        System.out.println("P: " + abs_P);

        System.out.println("Находим частные производные и вычисляем их значения при средних значениях аргументов");
        double difPI = round(2*abs_I*abs_R,3);
        double difPR = round(abs_I*abs_I,3);
        System.out.println("Производная P по I = 2*I*R: " + difPI);
        System.out.println("Производная P по F = I*I: " + difPR);

        System.out.println("Вычисляем составляющие погрешности от каждого аргумента");
        double errPI = round(Math.abs(difPI)*rel_I,3);
        double errPR = round(Math.abs(difPR)*rel_R,3);
        System.out.println("Погрешность P по I: " + errPI);
        System.out.println("Погрешность P по R: " + errPR);

        System.out.println("Вычисляем полную погрешность");
        double absErr = round(Math.sqrt(errPI*errPI + errPR*errPR), 3);
        System.out.println("Абсолютную: " + absErr);
        double relErr = round(absErr/abs_P,2);
        relErr *= 100;
        System.out.println("Относительную: " + relErr + "%");

        System.out.println("Результат: P = " + abs_P + "+-" + absErr + ", " + relErr + "%");
    }
}
