// ConsoleMenuView.java
package view;

import com.fasterxml.jackson.core.JsonProcessingException;
import controller.BookingController;
import model.dto.CreateBookingDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenuView {

    private final BookingController bookingController;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleMenuView(BookingController bookingController) {
        this.bookingController = bookingController;
    }

    public void consoleApplication() throws JsonProcessingException {

        while (true) {
            System.out.println("1. Create Booking");
            System.out.println("2. Search Bookings by Departure Time");
            System.out.println("3. Search Bookings by Airports");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    createBooking();
                    break;
                case 2:
                    searchBookingsByDepartureTime();
                    break;
                case 3:
                    searchBookingsByAirports();
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createBooking() throws JsonProcessingException {
        System.out.print("Enter Pax Name: ");
        String paxName = scanner.nextLine();

        System.out.print("Enter Departure Year: ");
        int year = scanner.nextInt();
        System.out.print("Enter Departure Month: ");
        int month = scanner.nextInt();
        System.out.print("Enter Departure Day: ");
        int day = scanner.nextInt();
        System.out.print("Enter Departure Hour: ");
        int hour = scanner.nextInt();
        System.out.print("Enter Departure Minute: ");
        int minute = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        LocalDateTime departure = LocalDateTime.of(year, month, day, hour, minute);

        System.out.print("Enter Itinerary (comma separated): ");
        List<String> itinerary = List.of(scanner.nextLine().split(","));

        CreateBookingDto request = new CreateBookingDto(paxName, departure, itinerary);
        bookingController.createBooking(request);
        System.out.println("Booking created successfully!");
    }

    private void searchBookingsByDepartureTime() throws JsonProcessingException {
        System.out.print("Enter Departure Year: ");
        int year = scanner.nextInt();
        System.out.print("Enter Departure Month: ");
        int month = scanner.nextInt();
        System.out.print("Enter Departure Day: ");
        int day = scanner.nextInt();
        System.out.print("Enter Departure Hour: ");
        int hour = scanner.nextInt();
        System.out.print("Enter Departure Minute: ");
        int minute = scanner.nextInt();
        System.out.print("Enter Departure Second: ");
        int second = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        LocalDateTime departure = LocalDateTime.of(year, month, day, hour, minute, second);
        bookingController.searchBookingsByDeparture(departure);
    }

    private void searchBookingsByAirports() throws JsonProcessingException {
        System.out.print("Enter First Airport Code: ");
        String airportFrom = scanner.nextLine();
        System.out.print("Enter Second Airport Code: ");
        String airportTo = scanner.nextLine();
        bookingController.searchBookingsByAirports(airportFrom, airportTo);
    }
}