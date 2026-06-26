package service;

import dto.ConcertResponse;
import entity.ConcertEntity;
import exception.ConcertNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.ConcertRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {

    @Mock
    private ConcertRepository concertRepository;

    @InjectMocks
    private ConcertService concertService;

    @Test
    void shouldReturnConcertList() {

        UUID id = UUID.randomUUID();

        ConcertEntity concert = new ConcertEntity();
        concert.setId(id);
        concert.setName("Coldplay");
        concert.setTotalTicket(10000);
        concert.setAvailableTicket(9000);
        concert.setBookingStart(LocalDateTime.now().minusHours(1));
        concert.setBookingEnd(LocalDateTime.now().plusHours(1));

        when(concertRepository.findByBookingStartBeforeAndBookingEndAfter(
                any(LocalDateTime.class),
                any(LocalDateTime.class)))
                .thenReturn(List.of(concert));

        List<ConcertResponse> response = concertService.search();

        assertNotNull(response);
        assertEquals(1, response.size());

        ConcertResponse result = response.get(0);

        assertEquals("Coldplay", result.getName());

        verify(concertRepository, times(1))
                .findByBookingStartBeforeAndBookingEndAfter(any(), any());
    }

    @Test
    void shouldReturnEmptyConcertList() {

        when(concertRepository.findByBookingStartBeforeAndBookingEndAfter(
                any(LocalDateTime.class),
                any(LocalDateTime.class)))
                .thenReturn(List.of());

        List<ConcertResponse> response = concertService.search();

        assertNotNull(response);
        assertTrue(response.isEmpty());

        verify(concertRepository).findByBookingStartBeforeAndBookingEndAfter(any(), any());
    }

    @Test
    void shouldReturnConcertDetail() {

        UUID id = UUID.randomUUID();

        ConcertEntity concert = new ConcertEntity();
        concert.setId(id);
        concert.setName("Bruno Mars");
        concert.setTotalTicket(5000);
        concert.setAvailableTicket(4500);

        when(concertRepository.findById(id))
                .thenReturn(Optional.of(concert));

        ConcertResponse response = concertService.detail(id);

        assertNotNull(response);
        assertEquals("Bruno Mars", response.getName());

        verify(concertRepository).findById(id);
    }

    @Test
    void shouldThrowConcertNotFound() {

        UUID id = UUID.randomUUID();

        when(concertRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                ConcertNotFoundException.class,
                () -> concertService.detail(id));

        verify(concertRepository).findById(id);
    }

}