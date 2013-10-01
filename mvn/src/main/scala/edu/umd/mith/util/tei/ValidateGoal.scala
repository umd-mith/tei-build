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

import edu.umd.mith.util.xml._
import edu.umd.mith.util.xml.schematron.SchematronValidator
import java.io.File
import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory
import javax.xml.validation.Validator
import org.apache.maven.plugin.MojoExecutionException
import org.apache.maven.plugin.MojoFailureException
import scala.collection.mutable.Buffer

abstract class ValidateGoal extends TransformingGoal {
  def perform() {
    System.setProperty(
      classOf[SchemaFactory].getName() + ":" + XMLConstants.RELAXNG_NS_URI,
      "com.thaiopensource.relaxng.jaxp.XMLSyntaxSchemaFactory"
    )

    this.getOddSpecs.foreach { spec =>
      Option(spec.getSource).map { source =>
        val base = this.removeExtension(source.getName)
        val rng = new File(spec.getRngOutputDir(this.getProject), base + ".rng")
        val sch = new File(spec.getSchOutputDir(this.getProject), base + ".isosch")

        println(rng)
        val rngValidator = SchemaFactory.newInstance(XMLConstants.RELAXNG_NS_URI)
          .newSchema(new StreamSource(rng))
          .newValidator

        val schValidator = new SchematronValidator(new StreamSource(sch), this.getResolver)

        val handler = new ValidationErrorHandler
        rngValidator.setErrorHandler(handler)

        this.getTeiFiles(Option(spec.getTeiDirs)).foreach(_.foreach { file =>
          try {
            handler.start()
            rngValidator.validate(new StreamSource(file))
            if (!handler.lastFailed)
              handler.addErrors(schValidator.validate(new StreamSource(file)))
          } catch {
            case e: Exception => throw(e)
          }
        })

        if (handler.getErrors.nonEmpty) {
          throw new MojoExecutionException(handler.getErrors.map { error =>
            "File: %s\n  Location: %s\n  Message: %s\n".format(
              error.uri,
              error.location,
              error.message.replaceAll("\\s+", " ")
            )
          }.mkString("\n"))
        }
      }.getOrElse(throw new MojoFailureException("No ODD source configured."))
    }
  }
}

