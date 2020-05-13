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

import static org.junit.Assert.assertEquals;

public class ThreadsTest {
    @Test public void testThreads1ForTwoFiles() throws InterruptedException {
        long count = Threads1.countLines(
                        Resources.METAMORPHOSIS,
                        Resources.DISCOURSE_ON_THE_METHOD);
        assertEquals(4745, count);
    }

    @Test public void testThreads2ForThreeFiles() throws InterruptedException {
        long count = Threads2.countLines(
                        Resources.METAMORPHOSIS,
                        Resources.DISCOURSE_ON_THE_METHOD,
                        Resources.DIVINE_COMEDY);
        assertEquals(10423, count);
    }
}
