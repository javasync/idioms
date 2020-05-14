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

import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class AsyncIoCallbacksTest {
    @Test public void testCallbacks1ForTwoFiles() {
        int count = AsyncIoCallbacks1.countLines(
                        Resources.METAMORPHOSIS,
                        Resources.DISCOURSE_ON_THE_METHOD);
        assertEquals(4745, count);
    }

    @Test public void testCallbacks2ForTwoFiles() throws InterruptedException, URISyntaxException {
        int count = AsyncIoCallbacks2.countLines(
                        Resources.METAMORPHOSIS,
                        Resources.DISCOURSE_ON_THE_METHOD);
        assertEquals(4745, count);
    }

    @Test public void testCallbacks2ForThreeFiles() throws InterruptedException, URISyntaxException {
        int count = AsyncIoCallbacks2.countLines(
                        Resources.METAMORPHOSIS,
                        Resources.DISCOURSE_ON_THE_METHOD,
                        Resources.DIVINE_COMEDY);
        assertEquals(10423, count);
    }
    @Test public void testCallbacks3ForTwoFiles() throws InterruptedException, URISyntaxException {
        AsyncIoCallbacks3.countLines(
                        (err, count) -> assertEquals(4745, count),
                        Resources.METAMORPHOSIS,
                        Resources.DISCOURSE_ON_THE_METHOD);
    }

    @Test public void testCallbacks3ForThreeFiles() throws InterruptedException, URISyntaxException {
        /**
         * This is a flaw test because the callback is not executed.
         * The test finishes before the countLines completion.
         */
        AsyncIoCallbacks3.countLines(
                        (err, count) -> assertEquals(10423, count),
                        Resources.METAMORPHOSIS,
                        Resources.DISCOURSE_ON_THE_METHOD,
                        Resources.DIVINE_COMEDY);
    }

}
