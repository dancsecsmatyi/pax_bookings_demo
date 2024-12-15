package repository;

import entity.Booking;
import lombok.extern.slf4j.Slf4j;
import search.repository.SearchBookingRepository;
import mapper.BookingMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FileSearchBookingsRepository implements SearchBookingRepository {
    private final String filePath;

    public FileSearchBookingsRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Booking> findByDepartureBefore(LocalDateTime departureTime) {
        log.info("Searching bookings with departure time before " + departureTime);
        List<Booking> bookings = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Booking booking = BookingMapper.stringToBooking(line);
                if (booking.getDeparture().equals(departureTime) || booking.getDeparture().isBefore(departureTime)) {
                    bookings.add(booking);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Found " + bookings.size() + " bookings");

        return bookings;
    }

    @Override
    public List<Booking> findByItineraryContainingAirports(String airportFrom, String airportTo) {
        log.info("Searching bookings with itinerary containing airports sequentially " + airportFrom + " and " + airportTo);
        List<Booking> bookings = readBookingsFromFile().stream()
                .filter(booking -> hasItinerarySequentiallyAirports(airportFrom, airportTo, booking))
                .collect(Collectors.toList());
        log.info("Found " + bookings.size() + " bookings");
        return bookings;
    }

    private boolean hasItinerarySequentiallyAirports(String airportFrom, String airportTo, Booking booking) {
        List<String> itinerary = booking.getItinerary();
        for (int i = 0; i < itinerary.size() - 1; i++) {
            if (hasFoundAirport(airportFrom, itinerary, i)) {
                for(i = i + 1; i < itinerary.size(); i++) {
                    if (hasFoundAirport(airportTo, itinerary, i)) return true;
                }
            }
        }
        return false;
    }

    private boolean hasFoundAirport(String airportTo, List<String> itinerary, int i) {
        return itinerary.get(i).equals(airportTo);
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