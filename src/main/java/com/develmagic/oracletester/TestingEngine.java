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

import com.develmagic.oracletester.dao.Database;
import com.develmagic.oracletester.dao.DatabaseFactory;
import com.develmagic.oracletester.domain.BenchmarkRequest;
import com.develmagic.oracletester.domain.BenchmarkResultList;
import com.develmagic.oracletester.domain.DatabaseQueryResult;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestingEngine {

    private BenchmarkRequest benchmarkRequest;
    private Database database;

    public TestingEngine(BenchmarkRequest benchmarkRequest) {
        this.benchmarkRequest = benchmarkRequest;
    }

    @SneakyThrows
    public BenchmarkResultList start(SummaryWriter writer, boolean waitForNewLine) {
        initializeDatabase();
        final BenchmarkResultList resultList = new BenchmarkResultList();

        for (int i = 1; i < benchmarkRequest.getRepeatCount() + 1; i++) {
            final DatabaseQueryResult result = database.executeQuery();
            if (log.isDebugEnabled()) {
                log.debug("#{} run: Query result: Duration: {} us, isFromCache: {}", i, result.queryDurationNanoTime / 1000,
                        result.isFromResultCache());
            }
            resultList.addResult(result);
        }
        writer.print(resultList);
        log.info("Waiting for user confirmation: ");
        if (waitForNewLine) {
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        }
        return resultList;
    }

    private void initializeDatabase() {
        database = DatabaseFactory.create(benchmarkRequest);
        database.connect();
    }

}
