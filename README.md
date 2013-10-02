TEI Build System Plugins
========================

This repository will hold a collection of tools for projects using the
[Text Encoding Initiative](http://www.tei-c.org/index.xml) [Guidelines](http://www.tei-c.org/Guidelines/),
with a specific focus on integrating [ODD ("One Document Does it All") specification](http://www.tei-c.org/Guidelines/Customization/odds.xml)
processing and validation with widely used build systems such as [Maven](http://maven.apache.org/) and [SBT](http://www.scala-sbt.org/).

Why?
----

The [TEI Roma](http://www.tei-c.org/Roma/) project provides several ways to create and process ODD specifications,
including both web and command-line applications.
These tools work well for many projects, particularly when only one or two developers are primarily responsible
for the development of the ODD specifications, and when the specifications do not change frequently.

In some cases, however, it may be useful for ODD processing and validation to be more closely integrated
with generalized build and testing systems.
When specifications are under active development or are being edited by several
users, for example, making sure that artifacts (such as RELAX NG schemas generated from ODD specifications) are up-to-date can be difficult
and error-prone.
If the creation of these artifacts can be hooked into a build process that all contributors are already running locally,
these files do not need to be kept under version control, and every user is guaranteed to
have up-to-date local versions.

This approach also has the benefits of being platform independent,
unlike the current command-line application,
and of not requiring a network connection and a running server to perform ODD processing, unlike the web version.

The plugins provided here are not designed to replace existing versions of Roma,
but to provide developers with another option for creating P5-compatible schemas
and managing validation in the project management frameworks they may already be using.

Third-Party Stylesheets
-----------------------

In previous versions of the TEI Maven Plugin project,
the relevant subset of the official TEI stylesheets (which are also used by Roma, for example)
were redistributed in the project's repository.
Now that these files are available in a [public Git repository](https://github.com/TEIC/Stylesheets)
they are included here as a [Git submodule](http://git-scm.com/book/en/Git-Tools-Submodules) in the `lib/tei` directory.
This greatly simplifies the process of keeping these stylesheets up-to-date,
and the use case is straightforward enough to avoid most of the problems associated with submodules in Git.

Rick Jelliffe's "skeleton" [implementation](/home/travis/tmp/build/schematron2) of ISO Schematron is
redistributed here for convenience (also in the `lib` directory). These files do not change often,
and will be updated manually when they do.

Repository Structure
--------------------

All third-party stylesheets (and potentially other files) are included in the `lib` directory.
Plugin projects live in directories with names like `mvn`, and the `example` directory holds example
projects (currently based on ODD specifications and TEI files from the [Shelley-Godwin Archive](http://shelleygodwinarchive.org/)).

Projects
--------

### TEI Maven Plugin

[Apache Maven](http://maven.apache.org/) is one of the most widely used build managers
for projects written in Java or other Java Virtual Machine languages.
Maven's mature integrated development environment (IDE) support,
its emphasis on "convention over configuration", and its strongly community-supported
approach to dependency management have made it an attractive choice for a wide range of
digital humanities projects.

Maven's plugin architecture makes it possible to bind "goals" to "phases" in a project's lifecycle.
In the specific case of our plugin for ODD processing and TEI validation,
the creation of [RELAX NG](http://relaxng.org/) and [Schematron](http://www.schematron.com/) schemas
from an ODD specification is bound to the `generate-resources` phase of the default lifecycle,
while validation of the TEI files associated with the project is bound to the `test` phase.
When the plugin is used to support a transcription and encoding workflow,
it provides a guarantee that all derivative schemas are automatically kept in sync with the project's ODD specifications,
and that all of the project's TEI files validate against these schemas.

A Maven-aware continuous integration system (such as [Hudson](http://hudson-ci.org/) or [Jenkins](http://jenkins-ci.org/))
can make the process even more efficient and convenient,
by generating artifacts and creating validation reports without any manual intervention.

Please see [the introduction](https://github.com/umd-mith/tei-build/tree/master/mvn) to the TEI Maven Plugin
project for more information about how to incorporate it into your project workflow.

Licensing
---------

All code developed for these projects has been released under the
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).
Please consult the individual license files in project directories for details.

Rick Jelliffe's [ISO Schematron implementation](http://www.schematron.com/implementation.html) (redistributed in this repository) is dual-licensed under the
[Zlib License](http://opensource.org/licenses/zlib-license.php) and the
Apache License.

The TEI stylesheets (included as a Git submodule in this repository) are
[dual-licensed](https://github.com/TEIC/Stylesheets/blob/master/LICENCE) under
the [Creative Commons Attribution-ShareAlike 3.0 Unported License](http://creativecommons.org/licenses/by-sa/3.0/)
and the [BSD 2-Clause License](http://opensource.org/licenses/BSD-2-Clause).

Contributors
------------

The TEI Maven Plugin was developed by [Travis Brown](https://twitter.com/travisbrown),
[Assistant Director of Research and Development](http://mith.umd.edu/people/person/travis-brown/)
at the [Maryland Institute for Technology in the Humanities](http://mith.umd.edu/).

We welcome pull requests.

Support
-------

This work was supported in part by a Community Initiative Grant from the [Text Encoding Initiative](http://www.tei-c.org/index.xml).

