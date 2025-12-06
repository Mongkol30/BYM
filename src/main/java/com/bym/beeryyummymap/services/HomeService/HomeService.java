package com.bym.beeryyummymap.services.HomeService;

import com.bym.beeryyummymap.dto.HomePinLocationsDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    private final JdbcTemplate jdbc;

    public HomeService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<HomePinLocationsDto> getAllPins() {

        String sql = """
                SELECT       l.id as locationId
                       		,r.id as restaurantId
                       		,r."name" as restaurantName
                       		,l.latitude as latitude
                       		,l.longitude as longitude
                FROM locations l
                JOIN restaurants r ON r.location_id = l.id
                WHERE r.status = 'ACTIVE'
                """;

        return jdbc.query(sql, (rs, rowNum) ->
                new HomePinLocationsDto(
                        rs.getObject("locationId", java.util.UUID.class),
                        rs.getObject("restaurantId", java.util.UUID.class),
                        rs.getString("restaurantName"),
                        rs.getBigDecimal("latitude"),
                        rs.getBigDecimal("longitude")
                )
        );
    }
}
