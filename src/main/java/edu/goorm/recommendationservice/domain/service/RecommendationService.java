package edu.goorm.recommendationservice.domain.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.goorm.recommendationservice.domain.client.NewsClient;
import edu.goorm.recommendationservice.domain.client.UserClient;
import edu.goorm.recommendationservice.domain.dto.RecommendationNewsDto;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendationService {

  private final RedisTemplate<String, String> redisTemplate;
  private final ObjectMapper objectMapper;
  private final UserClient userClient;
  private final NewsClient newsClient;

  public List<RecommendationNewsDto> fetchRecommendationsForUser(String email) {
    Long userId = userClient.getUserIdByEmail(email);
    String key = "recommendation:" + userId;

    String json = redisTemplate.opsForValue().get(key);
    if (json == null || json.isBlank()) {
      return Collections.emptyList();
    }
    List<Long> newsIds = parseNewsIdsFromJson(json);
    if (newsIds.isEmpty()) {
      return Collections.emptyList();
    }
    return newsClient.getRecommendationNews(newsIds);
  }

  private List<Long> parseNewsIdsFromJson(String json) {
    try {
      List<Map<String, Object>> recommendations = objectMapper.readValue(json, new TypeReference<>() {
      });
      return recommendations.stream()
          .map(entry -> ((Number) entry.get("news_id")).longValue())
          .toList();
    } catch (Exception e) {
      throw new RuntimeException("Redis에 저장된 추천 목록 JSON 파싱 실패: " + e.getMessage(), e);
    }
  }

  public List<RecommendationNewsDto> fetchDefaultRecommendations() {
    String key = "recommendation:default";

    String json = redisTemplate.opsForValue().get(key);
    System.out.println(json);
    if (json == null || json.isBlank()) {
      return Collections.emptyList();
    }
    List<Long> newsIds = parseNewsIdsFromJson(json);
    if (newsIds.isEmpty()) {
      return Collections.emptyList();
    }
    return newsClient.getRecommendationNews(newsIds);
  }
}
