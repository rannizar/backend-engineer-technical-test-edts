package dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BookingRequest {

    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotNull(message = "Concert ID is required")
    private UUID concertId;

    @Positive(message = "Ticket quantity must be greater than zero")
    private Integer quantity;

    @NotNull(message = "Book Code is required")
    private String bookCode;

}