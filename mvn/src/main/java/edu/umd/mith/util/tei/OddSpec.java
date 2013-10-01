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
import org.codehaus.mojo.xml.validation.ValidationSet;
import org.codehaus.plexus.resource.ResourceManager;
import org.codehaus.plexus.resource.loader.FileResourceLoader;

/**
 * The OddSpec is a single ODD file that will be transformed into a set of
 * schemas.
 */
public class OddSpec {
  /**
   * Specifies the ODD file to be transformed.
   * @parameter
   */
  private File source;

  /**
   * Specifies the output directory for all schemas.
   * @parameter
   */
  private File outputDir;

  /**
   * Specifies the output directory for RelaxNG schema.
   * @parameter
   */
  private File rngOutputDir;

  /**
   * Specifies the output directory for Schematron schema.
   * @parameter
   */
  private File schOutputDir;

  /**
   * Specifies the directories to validate (if any).
   * @parameter
   */
  private TeiDir[] teiDirs;

  /**
   * Whether creating the transformed files should be forced. This is not
   * currently used! Derivative files will be produced on every invocation.
   * @parameter default-value="false"
   */
  private boolean forceCreation;

  public File getSource() {
    return this.source;
  }

  public File getOutputDir(MavenProject project) {
    return this.outputDir == null ? new File(project.getBuild().getDirectory(), "generated-resources/schemas") : this.outputDir;
  }

  public File getRngOutputDir(MavenProject project) {
    return this.rngOutputDir == null ? this.getOutputDir(project) : this.rngOutputDir;
  }

  public File getSchOutputDir(MavenProject project) {
    return this.schOutputDir == null ? this.getOutputDir(project) : this.schOutputDir;
  }

  public boolean getForceCreation() {
    return this.forceCreation;
  }

  public TeiDir[] getTeiDirs() {
    return this.teiDirs;
  }
}

