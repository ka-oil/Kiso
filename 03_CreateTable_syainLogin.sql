CREATE TABLE SK_LOGIN (
	SYAIN_ID					VARCHAR2(10)	NOT NULL,
	LOGIN_PASS					VARCHAR2(30),
	TEMPORARY_PASS				VARCHAR2(1),
	SYAIN_ROLL					VARCHAR2(30),
	
	CONSTRAINT SK_LOGIN PRIMARY KEY(SYAIN_ID)
)
;

insert into SK_LOGIN(SYAIN_ID, LOGIN_PASS, TEMPORARY_PASS, SYAIN_ROLL)
values('0001', '0001pw','1','マネージャー');

