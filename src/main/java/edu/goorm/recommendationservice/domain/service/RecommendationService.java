package edu.goorm.recommendationservice.domain.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.goorm.recommendationservice.domain.client.UserClient;
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

  public List<Map<String, Object>> getRecommendationsByEmail(String email) {
    Long userId = userClient.getUserIdByEmail(email); 
    String key = "recommendation:" + userId;
    String json = redisTemplate.opsForValue().get(key);

    if (json == null) return List.of();

    try {
      return objectMapper.readValue(json, new TypeReference<>() {});
    } catch (Exception e) {
      throw new RuntimeException("JSON 파싱 실패", e);
    }
  }
}
