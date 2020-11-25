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

import com.develmagic.oracletester.domain.BenchmarkResultList;
import java.io.PrintStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SummaryWriter {

    private BenchmarkResultList benchmarkResultList;

    public SummaryWriter(BenchmarkResultList resultList) {
        this.benchmarkResultList = resultList;
    }

    public void print(PrintStream printStream) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        stringBuilder.append("Benchmark summary:\n\n");
        stringBuilder.append(String.format("AVG   : %.2f us\n", benchmarkResultList.getAverageLatencyNano() / 1000));
        stringBuilder.append(String.format("MED   : %.2f us\n", benchmarkResultList.getMedianLatencyNano() / 1000));
        stringBuilder.append(String.format("STDEV : %.2f us\n", benchmarkResultList.getStDevLatencyNano() / 1000));
        log.info(stringBuilder.toString());
        if (printStream != null) {
            printStream.println(stringBuilder.toString());
        }
    }

    public void print() {
        this.print(null);
    }

}
