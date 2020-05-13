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

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;

public class AsyncIoRxsTest {
    @Test public void testRxForThreeFiles() throws IOException {
        CompletableFuture finished = new CompletableFuture();
        AsyncIoRx
            .countLines(Resources.METAMORPHOSIS, Resources.DISCOURSE_ON_THE_METHOD, Resources.DIVINE_COMEDY)
            .thenAccept(count -> assertEquals(10423, count.intValue()))
            .thenAccept(finished::complete)
            .exceptionally(err -> { finished.completeExceptionally(err); return null; });
        finished.join();
    }
}
