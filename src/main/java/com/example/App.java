package com.example;


import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        
        System.out.println("Hello, " + name + "!");
        
        int sum = 0;
        for (int i = 1; i <= 5; i++) {
            sum += i;
            System.out.println("Adding " + i + " to sum. Current sum: " + sum);
        }
        
        System.out.println("Final Sum: " + sum);
        
        if (sum % 2 == 0) {
            System.out.println("The sum is even.");
        } else {
            System.out.println("The sum is odd.");
        }
        
        scanner.close();
    }
}
