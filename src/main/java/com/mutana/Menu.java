package com.mutana;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Menu {
    private final ComputerInterface computerImpl;
    private final Scanner scanner;

    @Autowired
    public Menu(ComputerInterface computerImpl) {
        this.computerImpl = computerImpl;
        this.scanner = new Scanner(System.in);
    }


    public void start() {
        int choice;
        System.out.println("Note: Type exit instead of a computer name to terminate\n");

        while (true) {
            showMenu();
            choice = getChoice();

            switch (choice) {
                case 1:
                    computerImpl.recordDetails();
                    break;

                case 2:
                    computerImpl.viewRecords();
                    break;

                case 3:
                    exit();
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showMenu() {
        System.out.println("1. Record computer details");
        System.out.println("2. View records");
        System.out.println("3. Exit \n");
    }

    private int getChoice() {
        System.out.print("Enter your choice number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    private void exit() {
        System.out.println("Thank you for using the program!");
        scanner.close();
        System.exit(0);
    }
}

