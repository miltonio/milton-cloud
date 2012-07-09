/*
 * Copyright (C) 2012 McEvoy Software Ltd
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
package io.milton.cloud.server.scratch;

import io.milton.cloud.server.db.utils.SchemaExporter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author brad
 */
public class MCSchemaExporter {

    public static void main(String[] args) throws Exception {
        List<String> list = new ArrayList<>();
        list.add("io.milton.vfs.db");
        list.add("io.milton.cloud.server.db");

        File outputDir = new File("/tmp/schema");
        outputDir.mkdirs();
        SchemaExporter exporter = new SchemaExporter(list, outputDir);
        exporter.generate();
    }


}
