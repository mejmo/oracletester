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

import static com.develmagic.oracletester.BenchmarkArgumentParser.JDBC_URL_ARG;

import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BenchmarkArgumentParserTest {

    @DisplayName("All arguments present, parsing successful")
    @Test
    public void testParseArguments_whenAllArgumentsPresent_expectValidNamespace() throws ArgumentParserException {
        Namespace namespace = BenchmarkArgumentParser.parseArguments(new String[]{
                "-v", "-U", "user", "-p", "password", "-d", "-x", "-r", "100", "url", "select"
        });
        Assertions.assertThat(namespace.getString(JDBC_URL_ARG)).isEqualTo("url");
    }

    @DisplayName("Required argument url is missing")
    @Test
    public void testParseArguments_whenUrlIsMissing_exception() {
        assertThrows(ArgumentParserException.class, () -> BenchmarkArgumentParser.parseArguments(new String[]{
                "-v", "-U", "user", "-p", "password", "-d", "-x", "-r", "100"
        }));
    }

}