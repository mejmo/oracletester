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

import static com.develmagic.oracletester.BenchmarkArgumentParser.CACHE_DISABLED;
import static com.develmagic.oracletester.BenchmarkArgumentParser.JDBC_PASSWORD;
import static com.develmagic.oracletester.BenchmarkArgumentParser.JDBC_QUERY;
import static com.develmagic.oracletester.BenchmarkArgumentParser.RESULT_FETCHING;
import static com.develmagic.oracletester.BenchmarkArgumentParser.JDBC_URL_ARG;
import static com.develmagic.oracletester.BenchmarkArgumentParser.JDBC_USERNAME;
import static com.develmagic.oracletester.BenchmarkArgumentParser.REPEAT_COUNT;
import static com.develmagic.oracletester.BenchmarkArgumentParser.VERBOSE;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.develmagic.oracletester.domain.BenchmarkRequest;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.slf4j.LoggerFactory;

/**
 * Simple application outputs latencies for selected query
 */
@Slf4j
public class Application {

    public static void main(String[] args) {
        Namespace res = null;
        try {
            res = BenchmarkArgumentParser.parseArguments(args);
        } catch (ArgumentParserException e) {
            System.exit(-1);
        }
        setLoggingLevel(res.getBoolean(VERBOSE));
        new TestingEngine(new BenchmarkRequest(
                res.get(JDBC_URL_ARG),
                res.get(JDBC_USERNAME),
                res.get(JDBC_PASSWORD),
                res.get(JDBC_QUERY),
                res.get(REPEAT_COUNT),
                res.getBoolean(CACHE_DISABLED),
                res.getBoolean(RESULT_FETCHING)
        )).start(new SummaryWriter(), true);
    }

    private static void setLoggingLevel(Boolean verboseEnabled) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger root = loggerContext.getLogger("com.develmagic");
        if (verboseEnabled) {
            root.setLevel(Level.DEBUG);
        } else {
            root.setLevel(Level.INFO);
        }
    }

}
