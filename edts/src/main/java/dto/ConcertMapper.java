package dto;

import entity.ConcertEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ConcertMapper {

    ConcertMapper INSTANCE = Mappers.getMapper(ConcertMapper.class);

    ConcertResponse toResponse(ConcertEntity entity);

    List<ConcertResponse> toResponseList(List<ConcertEntity> entities);
}
