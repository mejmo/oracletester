# Oracle JDBC latency benchmark

[![Build Status](https://travis-ci.org/mejmo/oracletester.svg?branch=master)](https://travis-ci.org/mejmo/oracletester)
[![codecov](https://codecov.io/gh/mejmo/oracletester/branch/master/graph/badge.svg?token=PKBA91KNPR)](https://codecov.io/gh/mejmo/oracletester)
[![Code Grade](https://www.code-inspector.com/project/16701/score/svg)](https://www.code-inspector.com)
[![Code Grade](https://www.code-inspector.com/project/16701/status/svg)](https://www.code-inspector.com)

Request from customer, to develop simple tool for making latency benchmark using JDBC Oracle driver. Option to disable/enable local JDBC result cache.

No unit tests, project tightly timeboxed.

## Installation
- `./mvnw package`

## Usage

```
java -jar oracletester-jar-with-dependencies.jar -h
```
```
usage: Oracle JDBC query tester
       [-h] [-v] -U USER -p PASSWORD [-d] [-r REPEATNUM] jdbcurl
       sqlquery

Make summary of Oracle JDBC cache latencies

positional arguments:
  jdbcurl                JDBC URL  to  use  for  connection.  Format: jdbc:
                         oracle:thin:@<DB_HOSTNAME>:1521/<SERVICE_NAME>  or
                         jdbc:oracle:thin:@<DB_HOSTNAME>:1521:SID
  sqlquery               SQL to be executed.

named arguments:
  -h, --help             show this help message and exit
  -v, --verbose          Verbose output (default: false)
  -U USER, --user USER   Database schema/user
  -p PASSWORD, --password PASSWORD
                         Database password
  -d, --cachedisabled    Disable JDBC Result cache (default: false)
  -x, --resultFetching   For every request  loop  all  rows  in  result set
                         (default: false)
  -r REPEATNUM, --repeatnum REPEATNUM
                         How  many  times   command   should   be  repeated
                         (default: 100)

```

```
# java -jar oracletester-jar-with-dependencies.jar -v -U system -p oracle -r 10000 jdbc:oracle:thin:@192.168.5.115:49161:XE "SELECT * FROM TESTING" 

AVG   : 975.23 us
MED   : 899.15 us
STDEV : 1578.99 us
```

## How to use custom JDBC driver
