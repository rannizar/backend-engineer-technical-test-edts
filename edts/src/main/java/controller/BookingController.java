package controller;

import dto.BookingRequest;
import dto.BookingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.BookingService;

@RestController

@RequestMapping("/booking")

@RequiredArgsConstructor

@Tag(name = "Booking API", description = "Book concert tickets")
public class BookingController {

    private final BookingService service;

    @Operation(
            summary = "Book Ticket",
            description = "Book ticket for selected concert."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking Success"),
            @ApiResponse(responseCode = "400", description = "Validation Error"),
            @ApiResponse(responseCode = "404", description = "Concert Not Found"),
            @ApiResponse(responseCode = "409", description = "Ticket Sold Out/Duplicate Booking")
    })
    @PostMapping

    public BookingResponse booking( @Valid
            @RequestBody BookingRequest request){

        return service.book(request);

    }

}