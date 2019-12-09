package equations.part2;

import java.util.Scanner;

public class EquationsPart2 {

    public static void resolveEquation(float coeffA, float coeffB, float coeffC){

        float discriminant = coeffB * coeffB - 4 * coeffA * coeffC;

        if (discriminant > 0) {
            discriminantMoreThanZero( coeffA, coeffB, discriminant);
        } else if (discriminant == 0) {
            discriminantIsZero(coeffA, coeffB);
        } else {
            discriminantIsLessThenZero();
        }
    }

    public static void discriminantMoreThanZero(float coeffA, float coeffB, float discriminant){
        double root1 =  (-coeffB - Math.sqrt(discriminant)) / (2 * coeffA);
        double root2 = (-coeffB + Math.sqrt(discriminant)) / (2 * coeffA);
        System.out.println(String
                .format("The equation has two real roots, because the discriminant is more than zero: \nroot1 = %f, root2 = %f",
                        root1, root2));
    }

    public static void discriminantIsZero(float coeffA, float coeffB){
        double root = -coeffB / (2 * coeffA);
        System.out.println(String
                .format("There is only one root of the equations, because the discriminant is zero: \nroot = %f",
                        root));

    }

    public static void discriminantIsLessThenZero(){
        System.out.println("The equation does not have real roots, because the discriminant is less than zero!");
    }

    public static void main(String[] args) {
        float coeffA, coeffB, coeffC;

        System.out.println("Print coeffA, coeffB and coeffC:");

        Scanner in = new Scanner(System.in);
        coeffA = in.nextFloat();
        coeffB = in.nextFloat();
        coeffC = in.nextFloat();

        resolveEquation(coeffA, coeffB, coeffC);
    }
}
