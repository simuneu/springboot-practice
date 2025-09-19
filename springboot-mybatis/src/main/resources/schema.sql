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

drop table if exists board;
create table board(
    id bigint auto_increment primary key not null,
    title varchar(20) not null,
    content varchar(100) not null,
    writer varchar(10) not null,
    registered timestamp default current_timestamp
);

insert into board(title, content, writer) values
    ('one', '첫 번째 글~~', 'papam'),
    ('two', '둘 글~', 'momong'),
    ('three', '셋 글~!', 'lily');