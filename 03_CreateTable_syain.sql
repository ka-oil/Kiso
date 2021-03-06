/* Ðõ */
CREATE TABLE webapp.SK_SYAIN(
	 SYAIN_ID                       CHAR(7 CHAR)        /* ÐõID */
	,SYAIN_NAME                     VARCHAR2(64 CHAR)   /* Ðõ_¼O */
	,SYAIN_AGE                  	NUMBER(2,0)  		/* Nî */
	,SYAIN_SEIBETU                  CHAR(1 CHAR)        /* «Ê */
	,SYAIN_ZYUSYO                   VARCHAR2(64 CHAR)   /* Z */
	,BUSYO_ID                 		CHAR(3 CHAR)        /* ID */	
)
;
ALTER TABLE webapp.SK_SYAIN ADD CONSTRAINT PK_TR_URIAGE PRIMARY KEY(SYAIN_ID)
USING INDEX
;


/* ã¾× */
CREATE TABLE webapp.SK_BUSYO(
	 BUSYO_ID                		CHAR(3 CHAR)       /* ID */
	,BUSYO_NAME        				VARCHAR2(64 CHAR)        /* ¼ */
)
;
ALTER TABLE webapp.SK_BUSYO ADD CONSTRAINT PK_TR_URIAGE_MEISAI PRIMARY KEY(BUSYO_ID)
USING INDEX
;


COMMENT	ON	TABLE	webapp.SK_SYAIN			IS	'Ðõe[u';
COMMENT	ON	COLUMN	webapp.SYAIN_ID 		IS	'ÐõID';
COMMENT	ON	COLUMN	webapp.SYAIN_NAME		IS	'Ðõ_¼O';
COMMENT	ON	COLUMN	webapp.SYAIN_AGE		IS	'Nî';
COMMENT	ON	COLUMN	webapp.SYAIN_SEIBETU	IS	'«Ê';
COMMENT	ON	COLUMN	webapp.SYAIN_ZYUSYO		IS	'Z';
COMMENT	ON	COLUMN	webapp.BUSYO_ID			IS	'ID';
COMMENT	ON	TABLE	webapp.SK_BUSYO			IS	'e[u';
COMMENT	ON	COLUMN	webapp.BUSYO_ID			IS	'ID';
COMMENT	ON	COLUMN	webapp.BUSYO_NAME		IS	'¼';
