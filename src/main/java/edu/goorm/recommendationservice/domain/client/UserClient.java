package edu.goorm.recommendationservice.domain.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "http://172.16.24.156:8080")
@Component
public interface UserClient {

  @GetMapping("/api/user/id")
  Long getUserIdByEmail(@RequestParam("email") String email);
}
