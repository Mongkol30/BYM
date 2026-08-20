package com.bym.beeryyummymap.controller;

import com.bym.beeryyummymap.dto.AiMenuResponseDto;
import com.bym.beeryyummymap.services.AiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/extract-menu")
    public ResponseEntity<?> extractMenu(@RequestBody java.util.Map<String, String> payload) {
        try {
            String base64Image = payload.get("base64Image");
            if (base64Image == null || base64Image.isEmpty()) {
                return ResponseEntity.badRequest().body("No image provided");
            }
            if (base64Image.contains(",")) {
                base64Image = base64Image.split(",")[1];
            }
            List<AiMenuResponseDto> extractedMenus = aiService.extractMenuFromImage(base64Image);
            return ResponseEntity.ok(extractedMenus);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error extracting menu: " + e.getMessage());
        }
    }

}
