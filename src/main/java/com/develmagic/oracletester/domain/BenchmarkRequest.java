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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;

/**
 * Domain object
 */
public class BenchmarkRequest {

    @Getter
    @NotBlank
    @Pattern(regexp = "^jdbc:oracle:thin:@([^:]+)(?::([0-9]+))?:([a-zA-Z][a-zA-Z0-9]*)$")
    private String jdbcUrl;

    @Getter
    @NotBlank
    private String username;

    @Getter
    @NotBlank
    private String password;

    @Getter
    @NotBlank
    private String sqlQuery;

    @Size(min = 1, max = 10000000)
    @Getter
    private Long repeatCount;

    @Getter
    private boolean disableCache;

    @Getter
    private boolean resultFetching;

    public BenchmarkRequest(String jdbcUrl, String username, String password, String sqlQuery, long repeatCount, boolean cacheDisabled, boolean resultFetching) {
        this.resultFetching = resultFetching;
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
        this.sqlQuery = sqlQuery;
        this.repeatCount = repeatCount;
        this.disableCache = cacheDisabled;
    }
}
