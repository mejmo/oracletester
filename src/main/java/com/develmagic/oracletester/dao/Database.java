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

package com.develmagic.oracletester.dao;

import com.develmagic.oracletester.domain.BenchmarkRequest;
import com.develmagic.oracletester.domain.DatabaseQueryResult;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

@Slf4j
@AllArgsConstructor
public class Database {

    private final String ORACLE_DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
    private final String PROP_RESULT_CACHE = "oracle.jdbc.enableQueryResultCache";
    private final String PROP_RESULT_CACHE_12 = "oracle.jdbc.enableResultSetCache";

    private Connection con;
    private BenchmarkRequest benchmarkRequest;

    public Database(BenchmarkRequest benchmarkRequest) {
        this.benchmarkRequest = benchmarkRequest;
    }

    public void connect() {
        try {
            log.debug("Loading driver");
            Class.forName(ORACLE_DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            log.error("Oracle driver not found");
            System.exit(-1);
        }

        try {
            log.debug("Connecting to database {}", benchmarkRequest.getJdbcUrl());
            con = DriverManager.getConnection(benchmarkRequest.getJdbcUrl(), createProperties(benchmarkRequest));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.exit(-1);
        }
    }

    public void releaseResources() {
        if (con != null) {
            try {
                log.error("Closing database connection");
                con.close();
            } catch (SQLException e) {
                //empty
            }
        }
    }

    public DatabaseQueryResult executeQuery() {
        final StopWatch stopWatch = new StopWatch();
        ResultSet resultSet = null;
        try {
            final Statement statement = con.createStatement();

            stopWatch.start();
            resultSet = statement.executeQuery(benchmarkRequest.getSqlQuery());
            stopWatch.stop();

            return new DatabaseQueryResult(stopWatch.getNanoTime());

        } catch (SQLException throwables) {
            log.error("Cannot execute query");
            throwables.printStackTrace();
            releaseResources();
            System.exit(-1);
            return null;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                }
            }
        }
    }

    private Properties createProperties(BenchmarkRequest benchmarkRequest) {
        final Properties props = new Properties();
        if (benchmarkRequest.isDisableCache()) {
            props.put(PROP_RESULT_CACHE, Boolean.FALSE.toString());
            props.put(PROP_RESULT_CACHE_12, Boolean.FALSE.toString());
        }
        props.put("user", benchmarkRequest.getUsername());
        props.put("password", benchmarkRequest.getPassword());
        return props;
    }

}
