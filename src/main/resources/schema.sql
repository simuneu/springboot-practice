create database codingon default character set utf8 default collate utf8_general_ci;
use codingon;
drop table if exists codingon;

-- users 테이블 생성
drop table if exists users;
create table users(
    id bigint auto_increment primary key,
    username varchar(50) not null,
    email varchar(100) not null,
    create_at timestamp default current_timestamp
);


-- users 테이블에 더미 데이터 삽입
insert into users(username, email) values
    ('park', 'park@example.com'),
    ('lee', 'lee@example.com'),
    ('kim', 'kim@example.com');

select *from users;