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
import java.lang.reflect.InvocationTargetException
import javax.xml.transform.Source
import javax.xml.transform.Templates
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

trait TransformerHelper {
  def transform(t: Transformer, source: File, target: File) {
    t.transform(new StreamSource(source), new StreamResult(target))
  }

  @throws(classOf[NoSuchMethodException])
  @throws(classOf[IllegalAccessException])
  @throws(classOf[InvocationTargetException])
  def newTransformerFactory(className: Option[String]) =
    className.map { name =>
      classOf[TransformerFactory].getDeclaredMethod(
        "newInstance", classOf[String], classOf[ClassLoader]
      ).invoke(
        null,
        name,
        Thread.currentThread.getContextClassLoader
      ).asInstanceOf[TransformerFactory]
    }.getOrElse(TransformerFactory.newInstance)
}

