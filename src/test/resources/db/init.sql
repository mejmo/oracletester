CREATE TABLE "SYSTEM"."TESTING"
(
    "ID"       NUMBER(*, 0)      NOT NULL ENABLE,
    "COLUMN1"  VARCHAR2(20 BYTE) NOT NULL ENABLE,
    "COLUMN2"  VARCHAR2(20 BYTE) NOT NULL ENABLE,
    "COLUMN3"  VARCHAR2(20 BYTE) NOT NULL ENABLE,
    "TESTBLOB" BLOB,
    CONSTRAINT "TESTING_PK" PRIMARY KEY ("ID")
);

INSERT into testing (id, column1, column2, column3, TESTBLOB) values (1, '23', '456', '789', 'ddd');
INSERT into testing (id, column1, column2, column3, TESTBLOB) values (2, '23', '456', '789', 'ddd');
INSERT into testing (id, column1, column2, column3, TESTBLOB) values (3, '23', '456', '789', 'ddd');

