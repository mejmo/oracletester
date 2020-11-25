/*
 * Oracle Cache Benchmark
 * Copyright (C) 2020  Martin Formanko
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.develmagic.oracletester;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

/**
 * usage: Oracle JDBC query tester
 *        [-h] [-v VERBOSE] -U USER -p PASSWORD [-c {true,false}]
 *        [-r REPEATNUM] jdbcurl sqlquery
 *
 * Make summary of Oracle JDBC cache latencies
 *
 * positional arguments:
 *   jdbcurl                JDBC URL  to  use  for  connection.  Format: jdbc:
 *                          oracle:thin:@<DB_HOSTNAME>:1521/<SERVICE_NAME>  or
 *                          jdbc:oracle:thin:@<DB_HOSTNAME>:1521:SID
 *   sqlquery               SQL to be executed.
 *
 * named arguments:
 *   -h, --help             show this help message and exit
 *   -v VERBOSE, --verbose VERBOSE
 *                          Verbose output (default: false)
 *   -U USER, --user USER   Database schema/user
 *   -p PASSWORD, --password PASSWORD
 *                          Database password
 *   -c {true,false}, --cacheenabled {true,false}
 *                          Enable JDBC Result cache (default: true)
 *   -r REPEATNUM, --repeatnum REPEATNUM
 *                          How  many  times   command   should   be  repeated
 *                          (default: 100)
 */
@Slf4j
class BenchmarkArgumentParser {

    public static final String JDBC_URL_ARG = "jdbcurl";
    public static final String JDBC_QUERY = "sqlquery";
    public static final String JDBC_USERNAME = "user";
    public static final String JDBC_PASSWORD = "password";
    public static final String VERBOSE = "verbose";
    public static final String REPEAT_COUNT = "repeatnum";
    public static final String CACHE_ENABLED = "cacheenabled";

    public static Namespace parseArguments(String[] args) {
        final ArgumentParser parser = ArgumentParsers.newFor("Oracle JDBC query tester").build()
                .defaultHelp(true)
                .description("Make summary of Oracle JDBC cache latencies");

        parser.addArgument("-v", "--" + VERBOSE)
                .setDefault(false)
                .help("Verbose output");

        parser.addArgument("-U", "--" + JDBC_USERNAME)
                .required(true)
                .help("Database schema/user");

        parser.addArgument("-p", "--" + JDBC_PASSWORD)
                .required(true)
                .help("Database password");

        parser.addArgument("-c", "--" + CACHE_ENABLED)
                .setDefault(true)
                .type(Boolean.class)
                .help("Enable JDBC Result cache");

        parser.addArgument("-r", "--" + REPEAT_COUNT)
                .required(false)
                .setDefault(100L)
                .type(Long.class)
                .help("How many times command should be repeated");

        parser.addArgument(JDBC_URL_ARG)
                .required(true)
                .help("JDBC URL to use for connection. Format: jdbc:oracle:thin:@<DB_HOSTNAME>:1521/<SERVICE_NAME> or jdbc:oracle:thin:@<DB_HOSTNAME>:1521:SID");

        parser.addArgument(JDBC_QUERY)
                .required(true)
                .help("SQL to be executed.");
        try {
            return parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.err.println(parser.formatHelp());
            System.exit(1);
            return null;
        }
    }

}
