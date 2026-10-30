-- 뉴스 기사 최상위 카테고리
create table topcategory(
	topcategory_id number primary key
	, top_name varchar2(30) --	정치, 경제, 사회, IT/과학
); 

create table subcategory(
	subcategory_id number primary key 
	, sub_name varchar2(30)
	, topcategory_id number
	, constraint fk_subcategory_topcategory foreign key(topcategory_id) references topcategory(topcategory_id)
);

create  table  news(
	news_id number primary key
	, title varchar2(100)
	, writer varchar2(30)
	, content clob -- 
	, created_at date default sysdate
	, hit number default 0
	, subcategory_id number 
	, constraint fk_news_subcategory foreign key(subcategory_id) references subcategory(subcategory_id) 
);

create table comments(
	comments_id number primary key 
	, msg varchar2(200)
	, commenter varchar2(30)
	, created_at date default sysdate
	, news_id number
	, constraint fk_comments_news foreign key(news_id) references news(news_id)	
);

create sequence seq_topcategory
increment by 1
start with 1;

create sequence seq_subcategory
increment by 1
start with 1;

create sequence seq_news
increment by 1
start with 1;

create sequence seq_comments
increment by 1
start with 1;