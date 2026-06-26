package service;

import constant.BookingStatusEnum;
import dto.BookingRequest;
import dto.BookingResponse;
import entity.BookingEntity;
import entity.ConcertEntity;
import exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.BookingRepository;
import repository.ConcertRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {

    private static final int MAX_RETRY = 3;

    private final ConcertRepository concertRepository;

    private final BookingRepository bookingRepository;

    @Retryable(
            retryFor = ObjectOptimisticLockingFailureException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 100)
    )
    @Transactional
    public BookingResponse book(BookingRequest request) {

        ConcertEntity concert = concertRepository.findById(request.getConcertId())
                .orElseThrow(ConcertNotFoundException::new);

        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(concert.getBookingEnd())) {

            throw new BookingClosedException();
        }

        if (bookingRepository.existsByConcertIdAndBookingCode(
                request.getConcertId(),
                request.getBookCode())) {

            throw new DuplicateBookingException();
        }

        if (concert.getAvailableTicket() < request.getQuantity()) {

            throw new SoldOutException();
        }

        concert.setAvailableTicket(
                concert.getAvailableTicket() - request.getQuantity());

        BookingEntity booking = new BookingEntity();

        booking.setConcert(concert);
        booking.setUserId(request.getUserId());
        booking.setQuantity(request.getQuantity());
        booking.setBookingTime(now);
        booking.setStatus(BookingStatusEnum.SUCCESS);

        bookingRepository.save(booking);

        concertRepository.save(concert);

        return new BookingResponse("Booking Success");

    }

    @Recover
    public BookingResponse recover(ObjectOptimisticLockingFailureException ex,
                                   BookingRequest request) {

        throw new TooManyTransactionException();
    }
}