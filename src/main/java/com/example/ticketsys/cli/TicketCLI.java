package com.example.ticketsys.cli;

/**
 * @TimeStamp 2024-12-10 18:54
 * @ProjectDetails ticket sys
 * @Author udarasan
 */
import java.util.Scanner;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class TicketCLI {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Ticket Management System!");

        while (true) {
            System.out.println("Enter a command (configure, start, book, status, exit): ");
            String command = scanner.nextLine();

            switch (command.toLowerCase()) {
                case "configure":
                    configureSystem(scanner);
                    break;
                case "start":
                    startOperations();
                    break;
                case "book":
                    bookTicket();
                    break;
                case "status":
                    getStatus();
                    break;
                case "exit":
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Unknown command!");
            }
        }
    }

    private static void configureSystem(Scanner scanner) {
        System.out.println("Enter total tickets to add: ");
        int tickets = Integer.parseInt(scanner.nextLine());
        makePostRequest("http://localhost:8080/api/tickets/add?count=" + tickets);
    }

    private static void startOperations() {
        System.out.println("Simulating ticket release and booking...");
        // Threads logic could be connected here
    }

    private static void bookTicket() {
        makePostRequest("http://localhost:8080/api/tickets/book");
    }

    private static void getStatus() {
        try {
            URL url = new URL("http://localhost:8080/api/tickets/status");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            Scanner responseScanner = new Scanner(connection.getInputStream());
            while (responseScanner.hasNextLine()) {
                System.out.println("Available tickets: " + responseScanner.nextLine());
            }
            responseScanner.close();
        } catch (IOException e) {
            System.out.println("Error while getting ticket status: " + e.getMessage());
        }
    }

    private static void makePostRequest(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                Scanner responseScanner = new Scanner(connection.getInputStream());
                while (responseScanner.hasNextLine()) {
                    System.out.println(responseScanner.nextLine());
                }
                responseScanner.close();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

