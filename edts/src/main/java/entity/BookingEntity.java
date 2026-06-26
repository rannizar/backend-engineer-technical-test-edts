package entity;

import constant.BookingStatusEnum;
import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "booking",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_concert",
                        columnNames = {
                                "user_id",
                                "concert_id"
                        }
                )
        }
)
public class BookingEntity extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "concert_id",
            nullable = false
    )
    private ConcertEntity concert;

    @Column(nullable = false)
    private String bookingCode;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private LocalDateTime bookingTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatusEnum status;

}