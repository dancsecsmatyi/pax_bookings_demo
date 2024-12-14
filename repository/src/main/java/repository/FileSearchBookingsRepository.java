// src/main/java/repository/FileSearchBookingsRepository.java
package repository;

import entity.Booking;
import search.repository.SearchBookingRepository;
import mapper.BookingMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileSearchBookingsRepository implements SearchBookingRepository {
    private final String filePath;

    public FileSearchBookingsRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Booking> findByDepartureBefore(LocalDateTime departureTime) {
        List<Booking> bookings = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Booking booking = BookingMapper.stringToBooking(line);
                if (booking.departure().equals(departureTime) || booking.departure().isBefore(departureTime)) {
                    bookings.add(booking);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    @Override
    public List<Booking> findByItineraryContainingAirports(String airportFrom, String airportTo) {
        return readBookingsFromFile().stream()
                .filter(booking -> {
                    List<String> itinerary = booking.itinerary();
                    for (int i = 0; i < itinerary.size() - 1; i++) {
                        if (itinerary.get(i).equals(airportFrom)) {
                            for(i = i + 1; i < itinerary.size(); i++) {
                                if (itinerary.get(i).equals(airportTo)) {
                                    return true;
                                }
                            }
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    private List<Booking> readBookingsFromFile() {
        List<Booking> bookings = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                bookings.add(BookingMapper.stringToBooking(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bookings;
    }
}