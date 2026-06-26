package service;

import constant.BookingStatusEnum;
import dto.BookingRequest;
import dto.BookingResponse;
import entity.BookingEntity;
import entity.ConcertEntity;
import exception.BookingClosedException;
import exception.ConcertNotFoundException;
import exception.DuplicateBookingException;
import exception.SoldOutException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.BookingRepository;
import repository.ConcertRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private ConcertRepository concertRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    private BookingRequest request;
    private ConcertEntity concert;

    @BeforeEach
    void setUp() {

        request = new BookingRequest();
        request.setConcertId(UUID.randomUUID());
        request.setUserId(UUID.randomUUID());
        request.setQuantity(2);
        request.setBookCode("BOOK-001");

        concert = new ConcertEntity();
        concert.setId(UUID.randomUUID());
        concert.setTotalTicket(100);
        concert.setAvailableTicket(100);
        concert.setBookingStart(LocalDateTime.now().minusMinutes(5));
        concert.setBookingEnd(LocalDateTime.now().plusMinutes(10));

    }

    @Test
    void shouldBookSuccessfully() {

        when(concertRepository.findById(UUID.randomUUID()))
                .thenReturn(Optional.of(concert));

        when(bookingRepository.existsByConcertIdAndBookingCode(UUID.randomUUID(), anyString()))
                .thenReturn(false);

        BookingResponse response = bookingService.book(request);

        assertNotNull(response);
        assertEquals("Booking Success", response.getMessage());
        assertEquals(98, concert.getAvailableTicket());

        verify(concertRepository).save(concert);

        ArgumentCaptor<BookingEntity> captor =
                ArgumentCaptor.forClass(BookingEntity.class);

        verify(bookingRepository).save(captor.capture());

        BookingEntity booking = captor.getValue();

        assertEquals(100L, booking.getUserId());
        assertEquals(2, booking.getQuantity());
        assertEquals(BookingStatusEnum.SUCCESS, booking.getStatus());

    }

    @Test
    void shouldThrowConcertNotFound() {

        when(concertRepository.findById(UUID.randomUUID()))
                .thenReturn(Optional.empty());

        assertThrows(
                ConcertNotFoundException.class,
                () -> bookingService.book(request));

        verify(bookingRepository, never()).save(any());

    }

    @Test
    void shouldThrowBookingClosed() {

        concert.setBookingEnd(LocalDateTime.now().minusMinutes(1));

        when(concertRepository.findById(UUID.randomUUID()))
                .thenReturn(Optional.of(concert));

        assertThrows(
                BookingClosedException.class,
                () -> bookingService.book(request));

    }

    @Test
    void shouldThrowDuplicateBooking() {

        when(concertRepository.findById(UUID.randomUUID()))
                .thenReturn(Optional.of(concert));

        when(bookingRepository.existsByConcertIdAndBookingCode(UUID.randomUUID(), anyString()))
                .thenReturn(true);

        assertThrows(
                DuplicateBookingException.class,
                () -> bookingService.book(request));

    }

    @Test
    void shouldThrowSoldOut() {

        concert.setAvailableTicket(1);

        when(concertRepository.findById(UUID.randomUUID()))
                .thenReturn(Optional.of(concert));

        when(bookingRepository.existsByConcertIdAndBookingCode(UUID.randomUUID(), anyString()))
                .thenReturn(false);

        assertThrows(
                SoldOutException.class,
                () -> bookingService.book(request));

    }

}