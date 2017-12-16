create database test;

use test;

ALTER TABLE keywords CHANGE wordname wordname varchar(40) NOT NULL;
DROP TABLE IF EXISTS keywords;
delete from keywords;
truncate table keywords;

create table if not exists keywords(
id int NOT NULL AUTO_INCREMENT,
wordname varchar(40) not null unique,
wordpos varchar(10),
num_t_c int not null default 0,
num_t_nc int not null default 0,
num_nt_c int not null default 0,
num_nt_nc int not null default 0,
primary key(id)
)
default charset=GBK;

create table if not exists chiwords(
id int NOT NULL AUTO_INCREMENT,
wordname varchar(40) not null unique,
wordpos varchar(10),
num_t_c int not null default 0,
num_t_nc int not null default 0,
num_nt_c int not null default 0,
num_nt_nc int not null default 0,
chiham double not null default 0,
chispam double not null default 0,
primary key(id)
)
default charset=GBK;

create table if not exists pwords(
id int NOT NULL AUTO_INCREMENT,
wordname varchar(40) not null unique,
wpham double not null default 0,
wpspam double not null default 0,
primary key(id)
)
default charset=GBK;

create table if not exists txtdata(
id int NOT NULL AUTO_INCREMENT,
txtnum int not null default 0,
hamnum int not null default 0,
spamnum int not null default 0,
pwordsnum int not null default 0,
primary key(id)
)
default charset=GBK;

insert into keywords(id,wordname,wordpos,num_t_c,num_t_nc,num_nt_c,num_nt_nc)values(null,"朱正义","人名",10,20,30,40);

select * from keywords;


