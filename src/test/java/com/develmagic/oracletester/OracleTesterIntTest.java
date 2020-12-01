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


import com.develmagic.oracletester.domain.BenchmarkRequest;
import com.develmagic.oracletester.domain.BenchmarkResultList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Disabled
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OracleTesterIntTest {

    @Container
    private static final OracleContainer oracleContainer = new OracleContainer("wnameless/oracle-xe-11g-r2")
            .withInitScript("db/init.sql");

    @Test
    public void someTestMethod() {
        // Given
        BenchmarkRequest benchmarkRequest = new BenchmarkRequest(
                oracleContainer.getJdbcUrl(),
                oracleContainer.getUsername(),
                oracleContainer.getPassword(),
                "select * from testing",
                100,
                false,
                true
        );

        // When
        TestingEngine testingEngine = new TestingEngine(benchmarkRequest);
        BenchmarkResultList benchmarkResultList = testingEngine.start(new SummaryWriter(), false);

        // Then
        Assertions.assertThat(benchmarkResultList.getAverageLatencyNano() / 1000).isBetween(50D, 1000000D);
        Assertions.assertThat(benchmarkResultList.getMedianLatencyNano() / 1000).isBetween(50D, 1000000D);
        Assertions.assertThat(benchmarkResultList.getStDevLatencyNano() / 1000).isBetween(50D, 1000000D);

    }

}
