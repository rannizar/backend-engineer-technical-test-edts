package dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ConcertResponse {

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-DD HH:mm:ss")
    private LocalDateTime concertDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-DD HH:mm:ss")
    private LocalDateTime bookingStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-DD HH:mm:ss")
    private LocalDateTime bookingEnd;

    @Schema(example = "5000")
    private Integer totalTicket;

    @Schema(example = "5000")
    private Integer availableTicket;

}