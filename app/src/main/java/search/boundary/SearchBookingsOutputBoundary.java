package search.boundary;


import com.fasterxml.jackson.core.JsonProcessingException;

public interface SearchBookingsOutputBoundary {
    void present(String response) throws JsonProcessingException;
}