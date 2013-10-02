TEI Maven Plugin
================

This project is a Maven plugin that provides [TEI ODD specification](http://www.tei-c.org/Guidelines/Customization/odds.xml)
processing and validation. It allows you to create [RELAX NG](http://relaxng.org/) and [ISO Schematron](http://www.schematron.com/)
schemas from your ODD files as part of your ordinary Maven build process,
and to validate your TEI files against these schemas along with your other tests.

Please see the TEI Build System Plugins [project description](https://github.com/umd-mith/tei-build)
for a high-level overview of this project.

Warning
-------

There are some differences in the way that Javadoc annotations for Mojos work between Maven 3.0 and 3.1,
and since we're using 3.1 we've opted to go with the newer annotations to avoid deprecation warnings.
This unfortunately means that the plugin will not work out of the box with 3.0. If there is interest in
a 3.0-compatible version, the changes are minor, and we could probably be persuaded to put up with the
warnings.

Installation
------------

First check out the TEI Build System Plugins repository:

``` bash
git clone --recursive git@github.com:umd-mith/tei-build.git
```

Note that you must include the `--recursive` flag or the Git submodule for the TEI
stylesheets project will not be properly initialized. If you've already cloned the
repository, you can manually check out the submodule with the following commands:

``` bash
git submodule init
git submodule update
```

Next navigate to this directory:

``` bash
cd mvn/
```

And then build and install locally:

``` bash
mvn install
```

This may take a few minutes to download dependencies the first time you run it, but then you're done!

Usage
-----

Add a section like the following to the plugins section of your `pom.xml` file:

``` xml
    <plugin>
      <groupId>edu.umd.mith</groupId>
      <artifactId>tei-maven-plugin</artifactId>
      <version>0.3.0</version>
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
        <dependencies>
          <dependency>
            <groupId>net.sf.saxon</groupId>
            <artifactId>saxon</artifactId>
            <version>8.7</version>
          </dependency>
          <dependency>
            <groupId>net.sf.saxon</groupId>
            <artifactId>saxon-dom</artifactId>
            <version>8.7</version>
          </dependency>
        </dependencies>
      </configuration>
    </plugin>
```

Note that you must specify the XSLT 2.0 library you wish to use in the dependencies section
([Saxon](http://saxon.sourceforge.net/) 8.7 is a simple and convenient choice).

The `executions` element binds the schema generation and validation goals to
the appropriate project phases (`generate-resources` and `test` respectively).
If you do not wish the goals to run automatically, you can remove this element
and call the goals directly:

``` bash
mvn tei:odd-schema
mvn tei:validate
```

The `configuration` element specifies one or more ODD files to process (and
possibly validate against). By default the derivative schemas are added to the
`target/generated-resources/schemas` directory, but this can be changed by adding
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

Examples
--------

Please see the [`examples/mvn`](https://github.com/umd-mith/tei-build/tree/master/examples/mvn)
directory for a complete working example.

Continuous Integration
----------------------

If you are using a continuous integration system such as [Jenkins](http://jenkins-ci.org/),
you will now get automatic reporting of errors in your ODD specifications or TEI files.

