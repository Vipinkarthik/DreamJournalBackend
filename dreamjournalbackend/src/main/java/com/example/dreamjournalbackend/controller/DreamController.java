package com.example.dreamjournalbackend.controller;
import com.example.dreamjournalbackend.dto.DreamRequest;
import com.example.dreamjournalbackend.dto.DreamResponse;
import com.example.dreamjournalbackend.service.DreamService;
import com.example.dreamjournalbackend.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dreams")
@CrossOrigin
public class DreamController {

    @Autowired
    private DreamService dreamService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<String> createDream(@RequestBody DreamRequest request, @RequestHeader(value = "Authorization", required = false) String authHeader) {
        String userEmail = request.getUserEmail();

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                String emailFromToken = jwtUtil.extractUsername(token);
                if (emailFromToken != null && !emailFromToken.isEmpty()) {
                    userEmail = emailFromToken;

                    request.setUserEmail(userEmail);
                }
            } catch (Exception e) {
            }
        }

        if (userEmail == null || userEmail.isEmpty()) {
            return ResponseEntity.badRequest().body("User email is missing.");
        }
        String result = dreamService.createDream(request);
        if ("Dream saved".equals(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    @GetMapping("/{userEmail}")
    public ResponseEntity<List<DreamResponse>> getDreamsByUser(@PathVariable String userEmail, @RequestHeader(value = "Authorization", required = false) String authHeader) {

        String actualUserEmail = userEmail;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                String emailFromToken = jwtUtil.extractUsername(token);
                if (emailFromToken != null && !emailFromToken.isEmpty()) {
                    actualUserEmail = emailFromToken;
                }
            } catch (Exception e){
            }
        }

        List<DreamResponse> dreams = dreamService.getDreamsByUserEmail(actualUserEmail);
        return ResponseEntity.ok(dreams);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDream(@PathVariable Long id) {
        dreamService.deleteDream(id);
        return ResponseEntity.ok("Dream deleted successfully");
    }

    @GetMapping("/stats/{userEmail}")
    public ResponseEntity<Map<String, Object>> getUserDreamStats(@PathVariable String userEmail, @RequestHeader(value = "Authorization", required = false) String authHeader) {

        String actualUserEmail = userEmail;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                String emailFromToken = jwtUtil.extractUsername(token);
                if (emailFromToken != null && !emailFromToken.isEmpty()) {
                    actualUserEmail = emailFromToken;
                }
            } catch (Exception e) {
            }
        }

        Map<String, Object> stats = dreamService.getUserDreamStats(actualUserEmail);
        return ResponseEntity.ok(stats);
    }
}
