/* 社員 */
CREATE TABLE webapp.SK_SYAIN(
	 SYAIN_ID                       CHAR(7 CHAR)        /* 社員ID */
	,SYAIN_NAME                     VARCHAR2(64 CHAR)   /* 社員_名前 */
	,SYAIN_AGE                  	NUMBER(2,0)  		/* 年齢 */
	,SYAIN_SEIBETU                  CHAR(1 CHAR)        /* 性別 */
	,SYAIN_ZYUSYO                   VARCHAR2(64 CHAR)   /* 住所 */
	,BUSYO_ID                 		CHAR(3 CHAR)        /* 部署ID */	
)
;
ALTER TABLE webapp.SK_SYAIN ADD CONSTRAINT PK_TR_URIAGE PRIMARY KEY(SYAIN_ID)
USING INDEX
;


/* 売上明細 */
CREATE TABLE webapp.SK_BUSYO(
	 BUSYO_ID                		CHAR(3 CHAR)       /* 部署ID */
	,BUSYO_NAME        				VARCHAR2(64 CHAR)        /* 部署名 */
)
;
ALTER TABLE webapp.SK_BUSYO ADD CONSTRAINT PK_TR_URIAGE_MEISAI PRIMARY KEY(BUSYO_ID)
USING INDEX
;


COMMENT	ON	TABLE	webapp.SK_SYAIN			IS	'社員テーブル';
COMMENT	ON	COLUMN	webapp.SYAIN_ID 		IS	'社員ID';
COMMENT	ON	COLUMN	webapp.SYAIN_NAME		IS	'社員_名前';
COMMENT	ON	COLUMN	webapp.SYAIN_AGE		IS	'年齢';
COMMENT	ON	COLUMN	webapp.SYAIN_SEIBETU	IS	'性別';
COMMENT	ON	COLUMN	webapp.SYAIN_ZYUSYO		IS	'住所';
COMMENT	ON	COLUMN	webapp.BUSYO_ID			IS	'部署ID';
COMMENT	ON	TABLE	webapp.SK_BUSYO			IS	'部署テーブル';
COMMENT	ON	COLUMN	webapp.BUSYO_ID			IS	'部署ID';
COMMENT	ON	COLUMN	webapp.BUSYO_NAME		IS	'部署名';
