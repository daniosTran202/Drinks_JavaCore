create database bar_store
go
use bar_store
go
CREATE TABLE category (
  id varchar(11) PRIMARY KEY  NOT NULL,
  name nvarchar(100) NOT NULL,
  status bit NOT NULL DEFAULT 1,
  parentId varchar(11) NOT NULL DEFAULT '0'
)
go

CREATE TABLE drinks (
  id varchar(11) PRIMARY KEY NOT NULL,
  name nvarchar(150) unique NOT NULL,
  status bit NOT NULL DEFAULT 1,
  price float NOT NULL,
  description ntext NOT NULL,
  expiration date NULL ,
  category_id varchar(11) FOREIGN KEY REFERENCES  category(id)
)
go

insert into category values ('3',N'Pepsi',1,0)
insert into category values ('5',N'Bia Hà Nội',1,10)
insert into category values ('6',N'Bia Sài Gòn',0,10)
insert into category values ('7',N'Rượu Vodka',0,9)
insert into category values ('8',N'Rượu chivas',0,9)
insert into category values ('14',N'Rượu vang',1,9)
insert into category values ('10',N'Bia hơi',1,0)


UPDATE category set parentId = 13 where id = 3
UPDATE drinks set category_id = 14 where id = 'C011' 
UPDATE drinks set category_id = 14 where id = 'C011' 
delete from drinks where id = 'C980'


SELECT * FROM drinks where id =  'C980'
drop table drinks
go

SELECT * FROM category WHERE name LIKE '%coca%'
delete category where id = '3'

select *from drinks
select *from category

insert into drinks values ('C200',N'pepsi cola',0,12000,N'vị socola','2021-10-20',3)
insert into drinks values ('C201',N'Bia chai ',1,212000,N'hơi đắng','2021-11-22',5)
insert into drinks values ('C203',N'Bia lon ',1,11230,N'vị ngon','2021-11-29',6)
insert into drinks values ('C204',N'rượu chivas 18',0,112001,N'đắng ngắt','2023-1-22',8)
insert into drinks values ('C205',N'Vodka 500ml',1,13100,N'tăng lực','2024-12-29',7)
insert into drinks values ('C206',N'nước lọc aqua',0,11101,N'vị dâu','2021-02-22',2)
insert into drinks values ('C007',N'Vang italy',0,130020,N'vị chua chát','2045-01-21',14)

insert into drinks values ('C210',N'coca ko đường',0,12000,N'vị nhạt','2021-10-29',1)
insert into drinks values ('C211',N'Bia bom 10l',1,21200,N'mát mẻ','2021-11-22',10)
insert into drinks values ('C213',N'Fanta cam',1,11230,N'vị cam','2028-11-29',13)
insert into drinks values ('C214',N'Vang đà lạt',0,112001,N'chát nhẹ','2053-1-22',14)
insert into drinks values ('C215',N'Vodka Nga',1,13100,N'rượu mạnh','2042-12-10',7)
insert into drinks values ('C216',N'nutriboost ',0,11101,N'vị dâu','2021-12-22',13)
insert into drinks values ('C017',N'bia két 30c',0,130020,N'nặng 5kg','2026-11-21',5)

