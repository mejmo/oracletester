# Oracle JDBC latency benchmark

Simple tool for getting latency benchmark using JDBC Oracle driver.

## Usage

```
usage: Oracle JDBC query tester
       [-h] [-v VERBOSE] -U USER -p PASSWORD [-c {true,false}]
       [-r REPEATNUM] jdbcurl sqlquery

Make summary of Oracle JDBC cache latencies

positional arguments:
  jdbcurl                JDBC URL  to  use  for  connection.  Format: jdbc:
                         oracle:thin:@<DB_HOSTNAME>:1521/<SERVICE_NAME>  or
                         jdbc:oracle:thin:@<DB_HOSTNAME>:1521:SID
  sqlquery               SQL to be  tested.  Can  contain dynamic variables
                         (## for random number or %% for random string)

named arguments:
  -h, --help             show this help message and exit
  -v VERBOSE, --verbose VERBOSE
                         Verbose output
  -U USER, --user USER   Database schema/user
  -p PASSWORD, --password PASSWORD
                         Database password
  -c {true,false}, --cacheenabled {true,false}
                         Enable JDBC Result cache (default: true)
  -r REPEATNUM, --repeatnum REPEATNUM
                         How  many  times   command   should   be  repeated
                         (default: 100)
```

## Output
```
AVG   : 975.23 us
MED   : 899.15 us
STDEV : 1578.99 us
```
