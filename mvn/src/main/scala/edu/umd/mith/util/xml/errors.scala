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
package edu.umd.mith.util.xml

import org.xml.sax.ErrorHandler
import org.xml.sax.SAXParseException
import scala.collection.mutable.Buffer

sealed trait XmlError {
  def uri: String
  def message: String
  def location: String
}

trait SaxError extends XmlError {
  def line: Int
  def column: Int
  def location = "line %d; column %d".format(this.line, this.column)
}

case class FatalError(
  uri: String, message: String, line: Int, column: Int
) extends SaxError

case class NonFatalError(
  uri: String, message: String, line: Int, column: Int
) extends SaxError

case class Warning(
  uri: String, message: String, line: Int, column: Int
) extends SaxError

case class SchematronError(
  uri: String, message: String, location: String
) extends XmlError

class ValidationErrorHandler extends ErrorHandler {
  private val errors = Buffer.empty[XmlError]
  private var failed = false

  def getErrors: Seq[XmlError] = this.errors
  def start() { this.failed = false }
  def lastFailed = this.failed

  def addErrors(errors: Seq[XmlError]) {
    this.errors ++= errors
  }

  def error(e: SAXParseException) {
    this.errors += NonFatalError(
      e.getSystemId, e.getMessage, e.getLineNumber, e.getColumnNumber
    )
  }

  def fatalError(e: SAXParseException) {
    this.failed = true
    this.errors += FatalError(
      e.getSystemId, e.getMessage, e.getLineNumber, e.getColumnNumber
    )
  }

  def warning(e: SAXParseException) {
    this.errors += Warning(
      e.getSystemId, e.getMessage, e.getLineNumber, e.getColumnNumber
    )
  }
}

