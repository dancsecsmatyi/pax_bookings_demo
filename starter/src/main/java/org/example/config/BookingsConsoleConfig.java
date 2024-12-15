package org.example.config;

import console.controller.BookingController;
import console.view.ConsoleResultView;
import createbooking.boundary.CreateBookingInputBoundary;
import createbooking.interactor.CreateBookingInteractor;
import createbooking.repository.CreateBookingRepository;
import mapper.BookingRequestResponseObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import console.presenter.CreateBookingPresenter;
import console.presenter.SearchBookingsPresenter;
import org.springframework.context.annotation.Configuration;
import repository.FileCreateBookingRepository;
import repository.FileSearchBookingsRepository;
import search.boundary.SearchBookingsInputBoundary;
import search.interactor.SearchBookingsInteractor;
import search.repository.SearchBookingRepository;
import console.view.ConsoleMenuView;

public class BookingsConsoleConfig {

    @Value("${repository.file.path}")
    private String filePath;

    @Bean
    public CreateBookingInputBoundary createBookingInputBoundary(
            CreateBookingPresenter createBookingPresenter,
            CreateBookingRepository createBookingRepository,
            BookingRequestResponseObjectMapper bookingRequestResponseObjectMapper) {
        return new CreateBookingInteractor(
                createBookingPresenter, createBookingRepository, bookingRequestResponseObjectMapper);
    }

    @Bean
    public SearchBookingsInputBoundary searchBookingsInputBoundary(
            SearchBookingsPresenter searchBookingsPresenter,
            SearchBookingRepository searchBookingRepository,
            BookingRequestResponseObjectMapper bookingRequestResponseObjectMapper) {
        return new SearchBookingsInteractor(
                searchBookingsPresenter, searchBookingRepository, bookingRequestResponseObjectMapper);
    }

    @Bean
    public BookingController bookingController(
            CreateBookingInputBoundary inputBoundary,
            SearchBookingsInputBoundary searchBookingsInputBoundary,
            BookingRequestResponseObjectMapper bookingRequestResponseObjectMapper) {
        return new BookingController(
                inputBoundary,
                searchBookingsInputBoundary,
                bookingRequestResponseObjectMapper);
    }

    @Bean
    public ConsoleMenuView consoleMenuView(BookingController bookingController) {
        return new ConsoleMenuView(bookingController);
    }

    @Bean
    public CreateBookingPresenter createBookingPresenter(BookingRequestResponseObjectMapper bookingRequestResponseObjectMapper,
                                                         ConsoleResultView consoleResultView) {
        return new CreateBookingPresenter(bookingRequestResponseObjectMapper, consoleResultView);
    }

    @Bean
    public FileCreateBookingRepository createBookingRepository() {
        return new FileCreateBookingRepository(filePath);
    }

    @Bean
    public SearchBookingsPresenter searchBookingPresenter(
            BookingRequestResponseObjectMapper bookingRequestResponseObjectMapper,
            ConsoleResultView consoleResultView) {
        return new SearchBookingsPresenter(bookingRequestResponseObjectMapper, consoleResultView);
    }

    @Bean
    public FileSearchBookingsRepository searchBookingsRepository() {
        return new FileSearchBookingsRepository(filePath);
    }

    @Bean
    public BookingRequestResponseObjectMapper bookingsObjectMapper() {
        return new BookingRequestResponseObjectMapper();
    }

    @Bean
    public ConsoleResultView consoleResultView() {
        return new ConsoleResultView();
    }

    @Bean
    CommandLineRunner run(ConsoleMenuView consoleMenuView) {
        return args -> consoleMenuView.consoleApplication();
    }
}
