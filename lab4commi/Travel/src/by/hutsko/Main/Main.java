package by.hutsko.Main;

import by.hutsko.Algorithms.Algorithms;

public class Main {
    public static void main(String[] args) {
        Integer[][] matrix = {
                {Integer.MAX_VALUE, 3, 5, 10, Integer.MAX_VALUE, 8},
                {12, Integer.MAX_VALUE, 2, 4, 15, 10},
                {5, 2, Integer.MAX_VALUE, Integer.MAX_VALUE, 5, 6},
                {10, 4, Integer.MAX_VALUE,  Integer.MAX_VALUE, 12, 8},
                {5, 15, 5, 12, Integer.MAX_VALUE, 3},
                {8, 10, 6, 8, 3, Integer.MAX_VALUE}};

        matrix = Algorithms.floydAlgorithm(matrix);

        System.out.println("Little's algorithm");
        Algorithms.littleAlgorithm(matrix);
    }
}
