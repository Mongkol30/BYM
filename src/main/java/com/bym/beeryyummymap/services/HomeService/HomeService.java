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

    public List<HomePinLocationsDto> getAllPins(String search) {
        if (search != null && !search.trim().isEmpty()) {
            String searchPattern = "%" + search.trim() + "%";
            String sql = """
                    SELECT DISTINCT l.id as locationId
                           ,r.id as restaurantId
                           ,r."name" as restaurantName
                           ,r.image_url as imageUrl
                           ,l.latitude as latitude
                           ,l.longitude as longitude
                    FROM locations l
                    JOIN restaurants r ON r.location_id = l.id
                    LEFT JOIN menus m ON m.res_id = r.id
                    WHERE r.status = 'ACTIVE'
                      AND (LOWER(r."name") LIKE LOWER(?) OR LOWER(m."name") LIKE LOWER(?))
                    """;

            return jdbc.query(sql, (rs, rowNum) ->
                    new HomePinLocationsDto(
                            rs.getObject("locationId", java.util.UUID.class),
                            rs.getObject("restaurantId", java.util.UUID.class),
                            rs.getString("restaurantName"),
                            rs.getString("imageUrl"),
                            rs.getBigDecimal("latitude"),
                            rs.getBigDecimal("longitude")
                    ),
                    searchPattern, searchPattern
            );
        }

        String sql = """
                SELECT       l.id as locationId
                       		,r.id as restaurantId
                       		,r."name" as restaurantName
                       		,r.image_url as imageUrl
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
                        rs.getString("imageUrl"),
                        rs.getBigDecimal("latitude"),
                        rs.getBigDecimal("longitude")
                )
        );
    }
}
