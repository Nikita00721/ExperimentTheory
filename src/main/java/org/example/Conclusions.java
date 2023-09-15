package org.example;

public class Conclusions {
    public void tusk3(){
        double absP = 405,
                Pot = 450,
                absErr = 121;
        double sigma = (absP - Pot) / absErr;
        //Задание 3
        if(absP == Pot)
            System.out.println("Предполагаемое теоретическое значение мощности: " + Pot + " Вт совпадает с вычисленным значением");
        else
            System.out.println("Предполагаемое теоретическое значение мощности: " + Pot +
                    " Вт лежит в интервале " +
                    (Math.abs(sigma) / sigma) * Math.ceil((Math.abs(sigma))) + " sigma");

    }
    public void tusk4and5(){
        double absExperimentP = 430,
                errExperimentP = 40,
                absP = 405,
                absErr = 121;
        System.out.println("Среднее экспериментальное значение отличается на " + Math.abs(absExperimentP - absP) + " Вт");
        if((absExperimentP + errExperimentP) - (absP - absErr) >= 0){
            System.out.println("Два Гаусовских распределния перекрываются на интервале [" +
                    (absP - absErr) + ";" + (absExperimentP + errExperimentP) + "]");
        } else if((absP - absErr) - (absExperimentP + errExperimentP) >= 0){
            System.out.println("Два Гаусовских распределния перекрываются на интервале [" +
                    (absExperimentP + errExperimentP) + ";" + (absP - absErr) + "]");
        } else {
            System.out.println("Два Гаусовских распределния не перекрываются");
        }

        if(absP + absErr - absExperimentP >= 0){
            System.out.println("Правое крыло экспериментального измерения P, перекрывает среднее значение косвенного измерения на " +
                    (absP + absErr - absExperimentP) + " Вт");
        } else if(absExperimentP - (absP - absErr) >= 0){
            System.out.println("Левое крыло экспериментального измерения P, перекрывает среднее значение косвенного измерения на " +
                    (absExperimentP - (absP - absErr)) + " Вт");
        }
    }

}
