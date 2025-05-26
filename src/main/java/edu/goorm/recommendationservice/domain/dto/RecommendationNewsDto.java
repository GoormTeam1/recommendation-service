package edu.goorm.recommendationservice.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationNewsDto {
  /** 기사 ID */
  private Long id;

  /** 기사 제목 */
  private String title;

  /** 기사 이미지 URL */
  private String image;

  /** 기사 카테고리 */
  private String category;

  /** 기사 발행 일시 */
  private LocalDate publishedAt;

}
