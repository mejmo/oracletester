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
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.pool.OracleDataSource;
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

    /**
     * Executes query in database. Checks if the query was fetched from result cache or not. Server must support
     * @return
     */
    public DatabaseQueryResult executeQuery() {
        final StopWatch stopWatch = new StopWatch();
        ResultSet resultSet = null;
        OraclePreparedStatement statement = null;
        try {

            statement = (OraclePreparedStatement) con.prepareStatement(benchmarkRequest.getSqlQuery());

            stopWatch.start();
            // Metering region start
            resultSet = statement.executeQuery(benchmarkRequest.getSqlQuery());
            if (benchmarkRequest.isResultFetching()) {
                while (resultSet.next()) {
                    log.debug("resultSet.next() from result cache: {}", ((OracleResultSet) resultSet).isFromResultSetCache());
                }
            }
            // Metering region end
            stopWatch.stop();

            return new DatabaseQueryResult(stopWatch.getNanoTime(), ((OracleResultSet)resultSet).isFromResultSetCache());

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
                    statement.close();
                } catch (SQLException throwables) {
                }
            }
        }
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
            OracleDataSource oracleDataSource = new OracleDataSource();
            oracleDataSource.setExplicitCachingEnabled(!benchmarkRequest.isDisableCache());
            oracleDataSource.setImplicitCachingEnabled(!benchmarkRequest.isDisableCache());
            oracleDataSource.setConnectionProperties(createProperties(benchmarkRequest));
            oracleDataSource.setURL(benchmarkRequest.getJdbcUrl());
            con = oracleDataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.exit(-1);
        }
    }

    private Properties createProperties(BenchmarkRequest benchmarkRequest) {
        final Properties props = new Properties();
        if (benchmarkRequest.isDisableCache()) {
            props.put(PROP_RESULT_CACHE, Boolean.FALSE.toString());
            props.put(PROP_RESULT_CACHE_12, Boolean.FALSE.toString());
        } else {
            props.put(PROP_RESULT_CACHE, Boolean.TRUE.toString());
            props.put(PROP_RESULT_CACHE_12, Boolean.TRUE.toString());
            props.put("oracle.jdbc.implicitStatementCacheSize", 500);
            props.put("oracle.jdbc.explicitStatementCacheSize", 500);
        }
        props.put("user", benchmarkRequest.getUsername());
        props.put("password", benchmarkRequest.getPassword());
        return props;
    }

    private void releaseResources() {
        if (con != null) {
            try {
                log.error("Closing database connection");
                con.close();
            } catch (SQLException e) {
                //empty
            }
        }
    }

}
