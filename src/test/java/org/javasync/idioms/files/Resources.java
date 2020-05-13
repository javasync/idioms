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

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static java.lang.ClassLoader.getSystemResource;

public class Resources {

    public static String METAMORPHOSIS = pathFrom("Metamorphosis-by-Franz-Kafka.txt");
    public static String DISCOURSE_ON_THE_METHOD = pathFrom("Discourse-on-the-Method-Descartes.txt");
    public static String DIVINE_COMEDY = pathFrom("The-Divine-Comedy-Hell-Dante.txt");

    private static String pathFrom(String file) {
        try {
            URL url = getSystemResource(file);
            return Paths.get(url.toURI()).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
