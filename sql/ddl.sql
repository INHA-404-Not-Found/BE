# 컬러명 변경
ALTER TABLE post
CHANGE original_filename original_file_name VARCHAR(255);
ALTER TABLE post
CHANGE stored_filename stored_file_name VARCHAR(255);

# not null 제약 조건 해제
ALTER TABLE post
MODIFY location_id BIGINT;
ALTER TABLE post
MODIFY stored_location VARCHAR(255);

# 세부 발견 장소 추가
ALTER TABLE post
ADD COLUMN location_detail VARCHAR(255) AFTER location_id;

# item 테이블 -> post_category 로 변경
DROP TABLE item;
CREATE TABLE post_category
(
    post_category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post(post_id),
    FOREIGN KEY (category_id) REFERENCES category(category_id)
);