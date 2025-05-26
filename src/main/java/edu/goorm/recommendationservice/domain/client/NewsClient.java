package edu.goorm.recommendationservice.domain.client;

import edu.goorm.recommendationservice.domain.dto.RecommendationNewsDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "news-service", url = "${news-service.url}")
public interface NewsClient {

  @PostMapping("/api/news/internal/find-news-by-NewsId")
  List<RecommendationNewsDto> getRecommendationNews(@RequestBody List<Long> newsIds);
}
