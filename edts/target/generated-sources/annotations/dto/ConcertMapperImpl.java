package dto;

import entity.ConcertEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-26T22:58:43+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 13.0.2 (Oracle Corporation)"
)
public class ConcertMapperImpl implements ConcertMapper {

    @Override
    public ConcertResponse toResponse(ConcertEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ConcertResponse concertResponse = new ConcertResponse();

        return concertResponse;
    }

    @Override
    public List<ConcertResponse> toResponseList(List<ConcertEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ConcertResponse> list = new ArrayList<ConcertResponse>( entities.size() );
        for ( ConcertEntity concertEntity : entities ) {
            list.add( toResponse( concertEntity ) );
        }

        return list;
    }
}
