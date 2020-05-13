/*
 * Copyright (c) 2020, Fernando Miguel Gamboa Carvalho, mcarvalho@cc.isel.ipl.pt
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
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.javasync.idioms.files;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

public class TasksTest {
    @Test public void testTasks1ForTwoFiles() throws InterruptedException, ExecutionException {
        long count = Tasks1.countLines(
                        Resources.METAMORPHOSIS,
                        Resources.DISCOURSE_ON_THE_METHOD);
        assertEquals(4745, count);
    }

    @Test public void testTasks2ForThreeFiles() throws Exception {
        try(Tasks2 ts = new Tasks2(3)){
            long count = ts.countLines(
                            Resources.METAMORPHOSIS,
                            Resources.DISCOURSE_ON_THE_METHOD,
                            Resources.DIVINE_COMEDY);
            assertEquals(10423, count);
        }
    }
}
