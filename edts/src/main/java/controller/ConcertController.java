package controller;

import dto.ConcertResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.ConcertService;

import java.util.List;
import java.util.UUID;

@RestController

@RequestMapping("/concerts")
@Tag(name = "Concert API", description = "Search available concerts")
@RequiredArgsConstructor

public class ConcertController {

    private final ConcertService service;

    @Operation(
            summary = "Search Concert",
            description = "Retrieve all available concerts whose booking period is still open."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping

    public List<ConcertResponse> search(){

        return service.search();

    }

    @Operation(
            summary = "Detail Concert",
            description = "Retrieve Detail Specific Concert."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Data Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/id")
    public ConcertResponse detail(@RequestParam String id){
        UUID uuid = UUID.fromString(id);
        return service.detail(uuid);

    }

}