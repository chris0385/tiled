/*-
 * #%L
 * libtiled
 * %%
 * Copyright (C) 2004 - 2018 Thorbjørn Lindeijer <thorbjorn@lindeijer.nl>
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package org.mapeditor.io;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

import static org.junit.Assert.*;
import org.junit.Test;

public class FileResolverTest {

	static {
		URLStreamHandlerFactory fac = new URLStreamHandlerFactory() {

			@Override
			public URLStreamHandler createURLStreamHandler(String protocol) {
				return new URLStreamHandler() {

					@Override
					protected URLConnection openConnection(URL u) throws IOException {
						// Irrelevant for the test
						throw new RuntimeException();
					}
				};
			}
		};
		URL.setURLStreamHandlerFactory(fac);
	}

	@Test
	public void testClasspathScheme() throws MalformedURLException {
		URL url = FileResolver.makeUrl("classpath:map/map.tmx");
		assertEquals("map/map.tmx", url.getPath());
		assertEquals("classpath", url.getProtocol());
	}

	@Test
	public void testDirectoryLookup() throws MalformedURLException, URISyntaxException {
		URL url = FileResolver.makeUrl("classpath:map/map.tmx");

		URL directory = FileResolver.directoryOf(url);
		assertEquals(new URL("classpath:map/"), directory);
		assertEquals(new URL("classpath:map/"), FileResolver.directoryOf(directory));
	}
	

}
