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

import static org.junit.jupiter.api.Assertions.*;

import com.develmagic.oracletester.dao.Database;
import com.develmagic.oracletester.dao.DatabaseFactory;
import com.develmagic.oracletester.domain.BenchmarkRequest;
import com.develmagic.oracletester.domain.BenchmarkResultList;
import com.develmagic.oracletester.domain.DatabaseQueryResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestingEngineTest {

    @Test
    public void testStartBenchmark() {
        // Given
        SummaryWriter summaryWriterMock = Mockito.mock(SummaryWriter.class);
        Database databaseMock = Mockito.mock(Database.class);
        DatabaseQueryResult result1 = new DatabaseQueryResult(10L, false);
        DatabaseQueryResult result2 = new DatabaseQueryResult(20L, false);
        Mockito.when(databaseMock.executeQuery())
                .thenReturn(result1)
                .thenReturn(result2);

        BenchmarkRequest benchmarkRequest = new BenchmarkRequest(
                "url", "username", "password", "sqlQuery", 2, true, true
        );

        try (MockedStatic<DatabaseFactory> factoryMock = Mockito.mockStatic(DatabaseFactory.class)) {
            factoryMock.when(() -> DatabaseFactory.create(benchmarkRequest)).thenReturn(databaseMock);

            // When
            new TestingEngine(benchmarkRequest).start(summaryWriterMock, false);

            // Then
            ArgumentCaptor<BenchmarkResultList> argumentCaptor = ArgumentCaptor.forClass(BenchmarkResultList.class);
            Mockito.verify(summaryWriterMock).print(argumentCaptor.capture());
            Assertions.assertThat(argumentCaptor.getValue().getList()).isNotEmpty();
            Assertions.assertThat(argumentCaptor.getValue().getList()).hasSize(2);
            Assertions.assertThat(argumentCaptor.getValue().getList()).contains(result1, result2);
        }

    }

}