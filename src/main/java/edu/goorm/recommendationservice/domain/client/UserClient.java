package edu.goorm.recommendationservice.domain.client;


import edu.goorm.recommendationservice.domain.dto.RecommendationNewsDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "http://172.16.24.156:8081")
public interface UserClient {

  @GetMapping("/api/user/internal/find-id-by-email")
  Long getUserIdByEmail(@RequestHeader("X-User-Email") String email);

}
