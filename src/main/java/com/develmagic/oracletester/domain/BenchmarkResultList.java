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

package com.develmagic.oracletester.domain;

import java.util.LinkedList;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.util.FastMath;

public class BenchmarkResultList {

    private LinkedList<DatabaseQueryResult> list = new LinkedList<>();

    public void addResult(DatabaseQueryResult result) {
        list.add(result);
    }

    public double getAverageLatencyNano() {
        return list.stream()
                .mapToDouble(DatabaseQueryResult::getQueryDurationNanoTime)
                .average()
                .orElse(0);
    }

    public double getMedianLatencyNano() {
        return StatUtils.percentile(list.stream()
                .mapToDouble(DatabaseQueryResult::getQueryDurationNanoTime)
                .toArray(), 50);
    }

    public double getStDevLatencyNano() {
        return FastMath.sqrt(StatUtils.variance(list.stream()
                .mapToDouble(DatabaseQueryResult::getQueryDurationNanoTime).toArray()));
    }

    public LinkedList<DatabaseQueryResult> getList() {
        return list;
    }
}
