package model.response;

import model.dto.SearchBookingDto;

import java.util.List;

public record SearchBookingsResponse(Result result, List<SearchBookingDto> bookings) {}
