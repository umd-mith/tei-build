TEI Maven Plugin Demo Project
=============================

This is a simple but complete working example that shows how
the [TEI Maven Plugin](https://github.com/umd-mith/tei-build/tree/master/mvn)
can be used to process ODD specifications and validate TEI files against the
resulting schemas.

Usage
-----

If you run the following command:

``` bash
mvn test
```

You will see the usual Maven build logging information, followed by several
errors resulting from invalid TEI. After the (failed) build finishes, you
can find the schema artifacts in the `target/generated-resources/schemas` directory.

