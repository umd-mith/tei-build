/*
 * #%L
 * MITH TEI Maven Plugin
 * %%
 * Copyright (C) 2011 Maryland Institute for Technology in the Humanities
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package edu.umd.mith.util.tei;

import java.io.File;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.resource.ResourceManager;
import org.codehaus.plexus.resource.loader.FileResourceLoader;

/**
 * The OddSchemaMojo is used for transforming a set of ODD files into schemas
 * and documentation.
 *
 * @goal odd-schema
 * @phase generate-resources
 */
public class OddSchemaMojo extends OddSchemaGoal {
  /**
   * Plexus resource manager used to obtain XSL.
   * 
   * @component
   * @required
   * @readonly
   */
  private ResourceManager locator;

  private boolean locatorInitialized = false;

  protected ResourceManager getLocator() {
    if (!this.locatorInitialized) {
      this.locator.addSearchPath(FileResourceLoader.ID, this.getBasedir().getAbsolutePath());
      this.locatorInitialized = true;
    }
    return this.locator;
  }

  /**
   * @parameter expression="${project}"
   * @required
   * @readonly
   */
  private MavenProject project;

  /**
   * The base directory, relative to which directory names are
   * interpreted.
   *
   * @parameter expression="${basedir}"
   * @required
   * @readonly
   */
  private File basedir;

  /**
   * Specifies one or more source ODD files.
   * @parameter
   */
  private OddSpec[] oddSpecs;

  /**
   * Transformer factory use. By default, the systems default transformer
   * factory is used.
   * <b>If you use this feature you must use at least jdk 1.6</b>
   * @parameter expression="${xml.transformerFactory}"
   */
  private String transformerFactory;

  @Override
  protected MavenProject getProject() {
    return this.project;
  }

  @Override
  protected File getBasedir() {
    return this.basedir;
  }

  public OddSpec[] getOddSpecs() {
    return this.oddSpecs;
  }

  public String getTransformerFactoryClassName() {
    return this.transformerFactory;
  }
}

