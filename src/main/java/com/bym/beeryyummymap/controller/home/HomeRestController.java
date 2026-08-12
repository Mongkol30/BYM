package com.bym.beeryyummymap.controller.home;

import com.bym.beeryyummymap.dto.HomePinLocationsDto;
import com.bym.beeryyummymap.services.HomeService.HomeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeRestController {

    private final HomeService service;

    public HomeRestController(HomeService service) {
        this.service = service;
    }

    @GetMapping("/pins")
    public List<HomePinLocationsDto> getPins(@RequestParam(required = false) String search) {
        return service.getAllPins(search);
    }
}
