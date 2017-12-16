use test;

ALTER TABLE keywords CHANGE wordname wordname varchar(40) NOT NULL;
DROP TABLE IF EXISTS keywords;
DROP TABLE IF EXISTS chiwords;
DROP TABLE IF EXISTS pwords;
DROP TABLE IF EXISTS txtdata;

delete from keywords;
truncate table keywords;
truncate table chiwords;
truncate table pwords;
truncate table txtdata;

select count(*) from keywords;
select count(*) from chiwords;
select count(*) from pwords;
select count(*) from txtdata;

create table if not exists keywords(
id int NOT NULL AUTO_INCREMENT,
wordname varchar(40) not null unique,
num_t_c int not null default 0,
num_t_nc int not null default 0,
num_nt_c int not null default 0,
num_nt_nc int not null default 0,
primary key(id)
)
default charset=GBK;

create table if not exists pwords(
id int NOT NULL AUTO_INCREMENT,
wordname varchar(20) not null unique,
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

create table if not exists filterparam(
id int NOT NULL AUTO_INCREMENT,
df int not null default 0,
chi double not null default 0,
primary key(id)
)
default charset=GBK;


create table if not exists chiwords(
id int NOT NULL AUTO_INCREMENT,
wordname varchar(20) not null unique,
num_t_c int not null default 0,
num_t_nc int not null default 0,
num_nt_c int not null default 0,
num_nt_nc int not null default 0,
chiham double not null default 0,
chispam double not null default 0,
primary key(id)
)
default charset=GBK;
