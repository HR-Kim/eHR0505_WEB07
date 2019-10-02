ALTER TABLE CODE_TYPE
	DROP
		PRIMARY KEY
		CASCADE
		KEEP INDEX;

DROP INDEX PK_CODE_TYPE;

/* 공통코드유형 */
DROP TABLE CODE_TYPE 
	CASCADE CONSTRAINTS;

/* 공통코드유형 */
CREATE TABLE CODE_TYPE (
	CODE_TYPE_ID VARCHAR2(30 BYTE) NOT NULL, /* 코드유형ID */
	CODE_TYPE_NM VARCHAR2(100 CHAR) NOT NULL /* 코드유형명 */
);

COMMENT ON TABLE CODE_TYPE IS '공통코드유형';

COMMENT ON COLUMN CODE_TYPE.CODE_TYPE_ID IS '코드유형ID';

COMMENT ON COLUMN CODE_TYPE.CODE_TYPE_NM IS '코드유형명';

CREATE UNIQUE INDEX PK_CODE_TYPE
	ON CODE_TYPE (
		CODE_TYPE_ID ASC
	);

ALTER TABLE CODE_TYPE
	ADD
		CONSTRAINT PK_CODE_TYPE
		PRIMARY KEY (
			CODE_TYPE_ID
		);
        
        
        
ALTER TABLE CODE
	DROP
		CONSTRAINT FK_CODE_TYPE_TO_CODE
		CASCADE;

ALTER TABLE CODE
	DROP
		PRIMARY KEY
		CASCADE
		KEEP INDEX;

DROP INDEX PK_CODE;

/* 공통코드 */
DROP TABLE CODE 
	CASCADE CONSTRAINTS;

/* 공통코드 */
CREATE TABLE CODE (
	CODE_TYPE_ID VARCHAR2(30 BYTE) NOT NULL, /* 코드유형ID */
	CODE_ID VARCHAR2(30 BYTE) NOT NULL, /* 코드ID */
	CODE_NM VARCHAR2(100 CHAR) NOT NULL, /* 코드명 */
	USE_YN CHAR(1 BYTE) DEFAULT 0 NOT NULL, /* 사용여부 */
	NUM NUMBER(5), /* 순서 */
	REG_ID VARCHAR2(20), /* 등록자ID */
	REG_DT DATE DEFAULT SYSDATE, /* 등록일 */
	MOD_ID VARCHAR2(20), /* 수정자ID */
	MOD_DT DATE DEFAULT SYSDATE /* 수정일 */
);

COMMENT ON TABLE CODE IS '공통코드';

COMMENT ON COLUMN CODE.CODE_TYPE_ID IS '코드유형ID';

COMMENT ON COLUMN CODE.CODE_ID IS '코드ID';

COMMENT ON COLUMN CODE.CODE_NM IS '코드명';

COMMENT ON COLUMN CODE.USE_YN IS '사용여부';

COMMENT ON COLUMN CODE.NUM IS '순서';

COMMENT ON COLUMN CODE.REG_ID IS '등록자ID';

COMMENT ON COLUMN CODE.REG_DT IS '등록일';

COMMENT ON COLUMN CODE.MOD_ID IS '수정자ID';

COMMENT ON COLUMN CODE.MOD_DT IS '수정일';

CREATE UNIQUE INDEX PK_CODE
	ON CODE (
		CODE_TYPE_ID ASC,
		CODE_ID ASC
	);

ALTER TABLE CODE
	ADD
		CONSTRAINT PK_CODE
		PRIMARY KEY (
			CODE_TYPE_ID,
			CODE_ID
		);

ALTER TABLE CODE
	ADD
		CONSTRAINT FK_CODE_TYPE_TO_CODE
		FOREIGN KEY (
			CODE_TYPE_ID
		)
		REFERENCES CODE_TYPE (
			CODE_TYPE_ID
		);