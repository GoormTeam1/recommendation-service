package edu.goorm.recommendationservice.domain.controller;

import edu.goorm.recommendationservice.domain.dto.RecommendationNewsDto;
import edu.goorm.recommendationservice.domain.service.RecommendationService;
import edu.goorm.recommendationservice.global.response.ApiResponse;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendation")
public class RecommendationController {

  private final RecommendationService recommendationService;

  @GetMapping("/search")
  public ResponseEntity<?> getUserRecommendations(@RequestHeader("X-User-Email") String email ) {
    List<RecommendationNewsDto> recommendations = recommendationService.fetchRecommendationsForUser(email);
    return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK,"추천 기사 검색 성공",recommendations));
  }
}
