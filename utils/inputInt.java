package utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class inputInt {

    /**
     * The function nextInt takes a Scanner object as input and repeatedly prompts
     * the user for an integer until a valid input is provided.
     * Form of Error Checking for input Int
     * 
     * @param scanner The scanner parameter
     * @return The method is returning an integer value.
     */
    public static int nextInt(Scanner scanner) {
        int value;
        while (true) {
            try {
                value = scanner.nextInt();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                System.out.println("Input again:");
                scanner.nextLine();
            }
        }
    }
}
