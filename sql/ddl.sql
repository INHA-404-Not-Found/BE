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