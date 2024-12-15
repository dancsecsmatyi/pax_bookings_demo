package repository;

import entity.Booking;
import createbooking.repository.CreateBookingRepository;
import mapper.BookingMapper;

import java.io.*;

public class FileCreateBookingRepository implements CreateBookingRepository {
    private final File file;

    public FileCreateBookingRepository(String filePath) {
        this.file = new File(filePath);
    }

    @Override
    public void save(Booking booking) {
        try (FileWriter writer = new FileWriter(file, true);
                BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            bufferedWriter.write(BookingMapper.bookingToString(booking));
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
