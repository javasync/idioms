/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.javasync.idioms;

import org.junit.Test;

import java.io.IOException;

import static org.javasync.idioms.Resources.DISCOURSE_ON_THE_METHOD;
import static org.javasync.idioms.Resources.DIVINE_COMEDY;
import static org.javasync.idioms.Resources.METAMORPHOSIS;
import static org.junit.Assert.assertEquals;

public class AsyncIoCfsTest {
    @Test public void testCf1ForTwoFiles() throws IOException {
        long count = AsyncIoCf1.countLines(
                        METAMORPHOSIS,
                        DISCOURSE_ON_THE_METHOD);
        assertEquals(4745, count);
    }

    @Test public void testCf2ForTwoFiles() throws IOException {
        AsyncIoCf2
            .countLines(METAMORPHOSIS,DISCOURSE_ON_THE_METHOD)
            .thenAccept(count -> assertEquals(4745, count.intValue()));
    }

    @Test public void testCf3ForThreeFiles() throws IOException {
        AsyncIoCf3
            .countLines(METAMORPHOSIS, DISCOURSE_ON_THE_METHOD, DIVINE_COMEDY)
            .thenAccept(count -> assertEquals(10423, count.intValue()));

    }
}