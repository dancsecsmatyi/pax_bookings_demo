package org.example.config;

import createbooking.boundary.CreateBookingInputBoundary;
import createbooking.interactor.CreateBookingInteractor;
import createbooking.repository.CreateBookingRepository;
import mapper.BookingRequestResponseObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.FileCreateBookingRepository;
import repository.FileSearchBookingsRepository;
import rest.controller.BookingRestController;
import rest.presenter.CreateBookingRestPresenter;
import rest.presenter.SearchBookingsRestPresenter;
import search.boundary.SearchBookingsInputBoundary;
import search.interactor.SearchBookingsInteractor;
import search.repository.SearchBookingRepository;

@Configuration
public class BookingsRestConfig {

    @Value("${repository.file.path}")
    private String filePath;

    @Bean
    public CreateBookingInputBoundary createBookingInputBoundary(
            CreateBookingRestPresenter createBookingRestPresenter,
            CreateBookingRepository createBookingRepository,
            BookingRequestResponseObjectMapper bookingRequestResponseObjectMapper) {
        return new CreateBookingInteractor(
                createBookingRestPresenter, createBookingRepository, bookingRequestResponseObjectMapper);
    }

    @Bean
    public SearchBookingsInputBoundary searchBookingsInputBoundary(
            SearchBookingsRestPresenter searchBookingsRestPresenter,
            SearchBookingRepository searchBookingRepository,
            BookingRequestResponseObjectMapper bookingRequestResponseObjectMapper) {
        return new SearchBookingsInteractor(
                searchBookingsRestPresenter, searchBookingRepository, bookingRequestResponseObjectMapper);
    }

    @Bean
    public BookingRestController bookingController(
            CreateBookingInputBoundary createBookingInputBoundary,
            BookingRequestResponseObjectMapper bookingRequestResponseObjectMapper,
            SearchBookingsInputBoundary searchBookingsInputBoundary) {
        return new BookingRestController(
                createBookingInputBoundary,
                bookingRequestResponseObjectMapper,
                searchBookingsInputBoundary);
    }

    @Bean
    public FileCreateBookingRepository createBookingRepository() {
        return new FileCreateBookingRepository(filePath);
    }

    @Bean
    public FileSearchBookingsRepository searchBookingsRepository() {
        return new FileSearchBookingsRepository(filePath);
    }

    @Bean
    public CreateBookingRestPresenter createBookingRestPresenter() {
        return new CreateBookingRestPresenter();
    }

    @Bean
    public SearchBookingsRestPresenter searchBookingsRestPresenter(
            BookingRequestResponseObjectMapper bookingRequestResponseObjectMapper) {
        return new SearchBookingsRestPresenter(bookingRequestResponseObjectMapper);
    }

    @Bean
    public BookingRequestResponseObjectMapper bookingRequestResponseObjectMapper() {
        return new BookingRequestResponseObjectMapper();
    }
}