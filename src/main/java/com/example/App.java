package com.example;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        
        for (int i = 1; i <= 10; i++) {
            System.out.println("Line " + i);
        }
        
        int sum = 0;
        for (int i = 1; i <= 5; i++) {
            sum += i;
            System.out.println("Sum after adding " + i + " is: " + sum)
        }
        
        System.out.println("Final Sum: " + sum);
        
        if (sum > 10) {
            System.out.println("The sum is greater than 10");
        } else {
            System.out.println("The sum is 10 or less");
        }
    }
}
