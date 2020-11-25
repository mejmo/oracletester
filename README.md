# Oracle JDBC latency benchmark

Simple tool for getting latency benchmark using JDBC Oracle driver.

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
Oracle JDBC query tester: error: too few arguments
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
  -r REPEATNUM, --repeatnum REPEATNUM
                         How  many  times   command   should   be  repeated
                         (default: 100)
```

```
# java -jar oracletester-jar-with-dependencies.jar -v -U system -p oracle jdbc:oracle:thin:@192.168.5.115:49161:XE "SELECT * FROM TESTING" -r 10000

AVG   : 975.23 us
MED   : 899.15 us
STDEV : 1578.99 us
```
