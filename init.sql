-- Drop tables if they exist
DROP TABLE IF EXISTS lottery CASCADE;
DROP TABLE IF EXISTS user_ticket CASCADE;

CREATE TABLE lottery (
 lt_ticket_id varchar(100) NOT NULL,
 lt_price int8 NOT NULL,
 lt_amount int8 NOT NULL,
 lt_create_user varchar(100) NULL,
 lt_create_date timestamp NULL,
 lt_update_user varchar(100) NULL,
 lt_update_date timestamp NULL,
 CONSTRAINT lottery_pk PRIMARY KEY (lt_ticket_id)
);

CREATE TABLE user_ticket (
 ut_tran_id varchar(100) NOT NULL,
 ut_user_id varchar(10) NOT NULL,
 ut_ticket_id varchar(100) NOT NULL,
 ut_statud_id varchar(1) NOT NULL,
 ut_create_user varchar(100) NULL,
 ut_create_date timestamp NULL,
 ut_update_user varchar(100) NULL,
 ut_update_date timestamp NULL,
 CONSTRAINT user_ticket_pk PRIMARY KEY (ut_tran_id)
);

-- Initial data
INSERT INTO lottery (lt_ticket_id,lt_price,lt_amount,lt_create_user,lt_create_date,lt_update_user,lt_update_date) VALUES
  ('000001',80,3,'admin','2024-02-21 22:51:08.684','0100000001','2024-02-21 22:51:08.684'),
  ('000002',80,2,'admin','2024-02-21 22:51:08.684','0100000002','2024-02-21 22:51:08.684'),
  ('123456',80,1,'admin','2024-02-21 22:51:08.684','admin','2024-02-21 22:51:08.684');

INSERT INTO user_ticket (ut_tran_id,ut_user_id,ut_ticket_id,ut_statud_id,ut_create_user,ut_create_date,ut_update_user,ut_update_date) VALUES
  ('1','0100000001','000001','1','0100000001','2024-02-21 22:51:08.684','0100000001','2024-02-21 22:51:08.684'),
  ('2','0100000002','000002','1','0100000002','2024-02-21 22:51:08.684','0100000002','2024-02-21 22:51:08.684');