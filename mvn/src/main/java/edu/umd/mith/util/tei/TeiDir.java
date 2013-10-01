/*
 * #%L
 * MITH TEI Maven Plugin
 * %%
 * Copyright (C) 2011 - 2012 Maryland Institute for Technology in the Humanities
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

/**
 * An instance of this class is used to specify a set of TEI files.
 */
public class TeiDir {
  private File dir;
  private String[] includes;
  private String[] excludes;

  /**
   * Returns a directory to be scanned for files to validate.
   */
  public File getDir() {
    return this.dir;
  }

  /**
   * Returns patterns of files that are to be excluded from
   * the validation set.
   */
  public String[] getExcludes() {
    return excludes;
  }

  /**
   * Returns patterns of files that are to be included in
   * the validation set.
   */
  public String[] getIncludes() {
    return includes;
  }
}

