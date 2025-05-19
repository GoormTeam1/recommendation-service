package edu.goorm.recommendationservice.domain.controller;

import edu.goorm.recommendationservice.domain.dto.RecommendationNewsDto;
import edu.goorm.recommendationservice.domain.service.RecommendationService;
import edu.goorm.recommendationservice.global.logger.CustomLogger;
import edu.goorm.recommendationservice.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendation")
public class RecommendationController {

  private final RecommendationService recommendationService;

  @GetMapping("/search")
  public ResponseEntity<?> getUserRecommendations(@RequestHeader(value = "X-User-Email", required = false) String email,
      HttpServletRequest request) {
    List<RecommendationNewsDto> recommendations;

    if (email == null) {
      recommendations = recommendationService.fetchDefaultRecommendations();

      CustomLogger.logRequest(
          "RECOMMEND_DEFAULT_FALLBACK",
          "/api/recommendation/search",
          "GET",
          null,
          "{\"reason\": \"no user email provided\"}",
          request
      );
    } else {
      recommendations = recommendationService.fetchRecommendationsForUser(email);

      CustomLogger.logRequest(
          "RECOMMEND_USER",
          "/api/recommendation/search",
          "GET",
          email,
          null,
          request
      );
    }

    return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "추천 기사 검색 성공", recommendations));
  }

  @GetMapping("/search/default")
  public ResponseEntity<?> getDefaultRecommendations(HttpServletRequest request) {
    List<RecommendationNewsDto> recommendations = recommendationService.fetchDefaultRecommendations();

    CustomLogger.logRequest(
        "RECOMMEND_DEFAULT",
        "/api/recommendation/search/default",
        "GET",
        null,
        null,
        request
    );

    return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "기본 추천 기사 검색 성공", recommendations));
  }
}
