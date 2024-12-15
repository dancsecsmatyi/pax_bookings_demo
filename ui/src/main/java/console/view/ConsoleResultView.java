package console.view;

import model.response.CreateBookingResponse;
import model.response.SearchBookingsResponse;

public class ConsoleResultView {

    public void display(CreateBookingResponse createBookingResponse) {
        System.out.println("Booking ID: " + createBookingResponse.id());
        System.out.println("Passenger Name: " + createBookingResponse.paxName());
        System.out.println("Departure: " + createBookingResponse.departure());
        System.out.println("Itinerary: " + String.join(", ", createBookingResponse.itinerary()));
    }

    public void display(SearchBookingsResponse searchBookingsResponse) {
        searchBookingsResponse.bookings().forEach(booking -> {
            System.out.println("Passenger Name: " + booking.paxName());
            System.out.println("Departure: " + booking.departure());
            System.out.println("Itinerary: " + String.join(", ", booking.itinerary()));
        });
    }
}