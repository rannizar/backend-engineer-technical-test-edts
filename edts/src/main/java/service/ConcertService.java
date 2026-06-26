package service;

import dto.ConcertMapper;
import dto.ConcertResponse;
import entity.ConcertEntity;
import exception.ConcertNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.ConcertRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository repository;

    public List<ConcertResponse> search() {

        List<ConcertEntity> concertEntityList = repository.findByBookingStartBeforeAndBookingEndAfter(LocalDateTime.now(), LocalDateTime.now());

        return ConcertMapper.INSTANCE.toResponseList(concertEntityList);
    }

    public ConcertResponse detail(UUID id) {
        Optional<ConcertEntity> concertEntity = repository.findById(id);
        return ConcertMapper.INSTANCE.toResponse(concertEntity.orElseThrow(ConcertNotFoundException::new));
    }

}