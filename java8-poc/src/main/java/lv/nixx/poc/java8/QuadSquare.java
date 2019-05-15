package lv.nixx.poc.java8;

import java.util.Scanner;

public class QuadSquare {


    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("Enter A:");
        double a = s.nextDouble();

        System.out.println("Enter B:");
        double b = s.nextDouble();

        System.out.println("Enter C:");
        double c = s.nextDouble();

        double d = Math.pow(b, 2) - (4 * a * c);

        if (d < 0) {
            System.out.println("D = " + d + ", less than 0, not possible to resolve");
        } else {
            double x1 = (-b - Math.sqrt(d)) / (2 * a);
            double x2 = (-b + Math.sqrt(d)) / (2 * a);
            System.out.println("Result:");
            System.out.println("D = " + d);
            System.out.println("x1 = " + x1);
            System.out.println("x2 = " + x2);
        }

    }

}
