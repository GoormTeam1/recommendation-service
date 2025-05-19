package edu.goorm.recommendationservice.global.logger;



import edu.goorm.recommendationservice.domain.controller.RecommendationController;
import edu.goorm.recommendationservice.global.util.CustomIpUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomLogger {
    private static final Logger logger = LogManager.getLogger(RecommendationController.class);

    public static void logRequest(
        String logType,
        String url,
        String method,
        String userId,
        String payload,
        HttpServletRequest request
    ) {
        logger.info(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s",
            logType,                          // ex: USER_LOGIN, USER_UPDATE_LEVEL
            LocalDateTime.now(),             // 로그 시간
            url,                             // 요청 URL
            method,                          // GET, POST 등
            userId != null ? userId : "-",   // 로그인 전엔 null일 수 있음
            payload != null ? payload : "-", // 요청 핵심 내용
            CustomIpUtil.getClientIp(request),
            request.getHeader("User-Agent")
        ));
    }

}