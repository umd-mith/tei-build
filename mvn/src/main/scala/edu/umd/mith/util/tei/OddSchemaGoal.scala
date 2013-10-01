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
package edu.umd.mith.util.tei

import java.io.File
import org.apache.maven.plugin.MojoFailureException

abstract class OddSchemaGoal extends TransformingGoal {
  def perform() {
    val resolver = this.getResolver()
    val odd2Odd = this.getTransformer(resolver, this.getOdd2Odd)
    val odd2Rng = this.getTransformer(resolver, this.getOdd2Rng)
    val odd2Sch = this.getTransformer(resolver, this.getOdd2Sch)
    this.getOddSpecs.foreach { spec =>
      Option(spec.getSource).map { source =>
        val base = this.removeExtension(source.getName)
        val outDir = spec.getOutputDir(this.getProject)
        val rngDir = spec.getRngOutputDir(this.getProject)
        val schDir = spec.getSchOutputDir(this.getProject)
        outDir.mkdirs()
        rngDir.mkdirs()
        schDir.mkdirs()
        val odd = new File(outDir, base + ".simple.odd")
        val rng = new File(rngDir, base + ".rng")
        val sch = new File(schDir, base + ".isosch")
        this.transform(odd2Odd, source, odd)
        this.transform(odd2Rng, odd, rng)
        this.transform(odd2Sch, odd, sch)
      }.getOrElse(throw new MojoFailureException("No ODD source configured."))
    }
  }
}

