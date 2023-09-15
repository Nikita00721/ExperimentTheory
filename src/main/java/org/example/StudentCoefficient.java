package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StudentCoefficient {
    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private double StatCom(double q, int i, int j) {
        double zz = 1, z = zz;
        int k = i;
        while (k <= j){
            zz *= q*k/(k+1);
            z += zz;
            k += 2;
        }
        return z;
    }

    private double StudentT(double t, int n) {
        double  th = Math.atan(Math.abs(t)/Math.sqrt((double)n)),
                pi2 = Math.acos((double)-1)/2;
        if (n == 1)
            return ( 1 - th / pi2 );

        double sth = Math.sin(th), cth = Math.cos(th);

        if (n%2 == 1)
            return ( 1 - (th + sth * cth * StatCom(cth * cth, 2, n-3)) / pi2 );
        else
            return ( 1 - sth * StatCom(cth * cth, 1, n-3) );
    }

    private double AStudentT(int n, double alpha) {
        double v = 0.5, dv = 0.5, t = 0;
        while (dv > 1e-10) {
            t = 1/v-1; dv /= 2;
            if (StudentT(t,n) > alpha)
                v -= dv;
            else
                v += dv;
        }
        return round(t, 3);
    }
    public double calculateStudentFor(int n, double p){
        return AStudentT(n,1-p);
    }
}
