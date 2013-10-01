TEI Maven Plugin
================

Overview
--------

This TEI Maven plugin is designed to make the process of working with ODD
specifications easier for projects that are already using Maven as their build
system.

It uses and includes the [stylesheets provided by the TEI](http://www.tei-c.org/Guidelines/P5/get.xml)
for use with [Roma](http://www.tei-c.org/Guidelines/P5/get.xml#Roma) (currently revision `r10366`).

Usage
-----

You currently first need to add the [MITH Maven repository](https://github.com/umd-mith/maven-repository)
to your project's `pom.xml`:

    <pluginRepositories>
      <pluginRepository>
        <id>mith-plugin-snapshots</id>
        <url>http://umd-mith.github.com/maven-repository/snapshots</url>
      </pluginRepository>
    </pluginRepositories>

Next add the following to the plugins section:

    <plugin>
      <groupId>edu.umd.mith</groupId>
      <artifactId>tei-maven-plugin</artifactId>
      <version>0.1.0-SNAPSHOT</version>
      <executions>
        <execution>
          <goals>
            <goal>odd-schema</goal>
            <goal>validate</goal>
          </goals>
        </execution>
      </executions>
      <configuration>
        <oddSpecs>
          <oddSpec>
            <source>${basedir}/../data/odd/shelley-godwin-master.odd</source>
          </oddSpec>
          <oddSpec>
            <source>${basedir}/../data/odd/shelley-godwin-page.odd</source>
            <teiDirs>
              <teiDir>
                <dir>${basedir}/../data/tei/ox/</dir>
                <includes>
                  <include>*.xml</include>
                </includes>
              </teiDir>
            </teiDirs>
          </oddSpec>
        </oddSpecs>
      </configuration>
    </plugin>

The `executions` element binds the schema generation and validation goals to
the appropriate project phases (`generate-resources` and `test` respectively).
If you do not wish the goals to run automatically, you can remove this element
and call the goals directly:

    mvn tei:odd-schema
    mvn tei:validate

The `configuration` element specifies one or more ODD files to process (and
possibly validate against). By default the derivative schemas are added to the
`target/generated-resources/schemas` directory. This can be changed by adding
an `outputDir` element to the `oddSpec` configuration. In this example the
following files would be generated:

    target/generated-resources/schemas/shelley-godwin-master.isosch
    target/generated-resources/schemas/shelley-godwin-master.rng
    target/generated-resources/schemas/shelley-godwin-master.simple.odd
    target/generated-resources/schemas/shelley-godwin-page.isosch
    target/generated-resources/schemas/shelley-godwin-page.rng
    target/generated-resources/schemas/shelley-godwin-page.simple.odd

When the `oddSpec` element does not contain a `teiDirs` child (as in the first
example above), no validation is performed. When one or more TEI directories
are specified, the plugin will validate the files against the
[RELAX NG](http://relaxng.org/) and [ISO Schematron](http://www.schematron.com/)
derivatives and the build will fail if there are any warnings or errors.

