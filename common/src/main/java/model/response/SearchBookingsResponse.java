// src/main/java/search/model/SearchBookingsResponse.java
package model.response;

import model.dto.SearchBookingDto;

import java.util.List;

public record SearchBookingsResponse (    List<SearchBookingDto> bookings){

}