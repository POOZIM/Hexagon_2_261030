-- 상위 카테고리 등록 (시퀀스 seq_topcategory 사용)
INSERT INTO topcategory (topcategory_id, top_name) VALUES (seq_topcategory.NEXTVAL, '정치'); 
INSERT INTO topcategory (topcategory_id, top_name) VALUES (seq_topcategory.NEXTVAL, '경제'); 
INSERT INTO topcategory (topcategory_id, top_name) VALUES (seq_topcategory.NEXTVAL, '사회'); 
INSERT INTO topcategory (topcategory_id, top_name) VALUES (seq_topcategory.NEXTVAL, 'IT/과학');

-- [1. 정치 (topcategory_id = 1)] 하위 카테고리
INSERT INTO subcategory (subcategory_id, sub_name, topcategory_id) VALUES (seq_subcategory.NEXTVAL, '대통령실/국회', 1);
INSERT INTO subcategory (subcategory_id, sub_name, topcategory_id) VALUES (seq_subcategory.NEXTVAL, '정당', 1);
INSERT INTO subcategory (subcategory_id, sub_name, topcategory_id) VALUES (seq_subcategory.NEXTVAL, '선거', 1);
INSERT INTO subcategory (subcategory_id, sub_name, topcategory_id) VALUES (seq_subcategory.NEXTVAL, '행정', 1);
INSERT INTO subcategory (subcategory_id, sub_name, topcategory_id) VALUES (seq_subcategory.NEXTVAL, '외교/안보', 1);

-- [2. 경제 (topcategory_id = 2)] 하위 카테고리
INSERT INTO subcategory (subcategory_id, sub_name, topcategory_id) VALUES (seq_subcategory.NEXTVAL, '금융', 2);
INSERT INTO subcategory (subcategory_id, sub_name, topcategory_id) VALUES (seq_subcategory.NEXTVAL, '증권', 2);
INSERT INTO subcategory (subcategory_id, sub_name, topcategory_id) VALUES (seq_subcategory.NEXTVAL, '부동산', 2);
INSERT INTO subcategory (subcategory_id, sub_name, topcategory_id) VALUES (seq_subcategory.NEXTVAL, '산업/기업', 2);
INSERT INTO subcategory (subcategory_id, sub_name, topcategory_id) VALUES (seq_subcategory.NEXTVAL, '글로벌경제', 2);

-- [3. 사회 (topcategory_id = 3)] 하위 카테고리
INSERT INTO subcategory (subcategory_id, sub_name, topcategory_id) VALUES (seq_subcategory.NEXTVAL, '사건/사고', 3);
INSERT INTO subcategory (subcategory_id, sub_name, topcategory_id) VALUES (seq_subcategory.NEXTVAL, '교육', 3);
INSERT INTO subcategory (subcategory_id, sub_name, topcategory_id) VALUES (seq_subcategory.NEXTVAL, '노동', 3);
INSERT INTO subcategory (subcategory_id, sub_name, topcategory_id) VALUES (seq_subcategory.NEXTVAL, '법원/검찰', 3);
INSERT INTO subcategory (subcategory_id, sub_name, topcategory_id) VALUES (seq_subcategory.NEXTVAL, '환경', 3);

-- [4. IT/과학 (topcategory_id = 4)] 하위 카테고리
INSERT INTO subcategory (subcategory_id, sub_name, topcategory_id) VALUES (seq_subcategory.NEXTVAL, '모바일', 4);
INSERT INTO subcategory (subcategory_id, sub_name, topcategory_id) VALUES (seq_subcategory.NEXTVAL, '인터넷/IT', 4);
INSERT INTO subcategory (subcategory_id, sub_name, topcategory_id) VALUES (seq_subcategory.NEXTVAL, '인공지능(AI)', 4);
INSERT INTO subcategory (subcategory_id, sub_name, topcategory_id) VALUES (seq_subcategory.NEXTVAL, '과학', 4);
INSERT INTO subcategory (subcategory_id, sub_name, topcategory_id) VALUES (seq_subcategory.NEXTVAL, '보안', 4);

-- 트랜잭션 반영
COMMIT;