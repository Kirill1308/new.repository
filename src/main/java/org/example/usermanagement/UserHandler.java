package org.example.usermanagement;

import java.util.Scanner;

public class UserHandler {
    private final Scanner scanner = new Scanner(System.in);
    public int width;
    public int height;

    public void readIslandDimensions() {
        width = readCoordinate("WIDTH (number of columns)");
        height = readCoordinate("HEIGHT (number of rows)");

    }

    private int readCoordinate(String dimensionName) {
        int coordinate;

        while (true) {
            System.out.print("Enter the " + dimensionName + " of your Island: ");
            if (scanner.hasNextInt()) {
                coordinate = scanner.nextInt();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            }
        }

        return coordinate;
    }
}

