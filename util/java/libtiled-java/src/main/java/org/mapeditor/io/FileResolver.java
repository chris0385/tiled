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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Pattern;

public class FileResolver {

    private static final String URL_PATH_SEPARATOR = "/";
	private static final Pattern START_WITH_URL_SCHEME = Pattern.compile("^[a-z](?:[-a-z0-9+.])*:.*");
    private static final Pattern WINDOWS_PATH = Pattern.compile("^[a-z]+:\\\\.*");

	public static URL makeUrl(String filename) throws MalformedURLException {
		if (START_WITH_URL_SCHEME.matcher(filename).matches() && !WINDOWS_PATH.matcher(filename).matches()) {
			return new URL(filename);
		} else {
            return new File(filename).toURI().toURL();
        }
    }
	
	public static URL directoryOf(URL url) throws MalformedURLException, URISyntaxException {
		String str = url.toString();
		if (!str.endsWith(URL_PATH_SEPARATOR)) {
			return new URL(str.substring(0, str.lastIndexOf(URL_PATH_SEPARATOR) + 1));
		}
		return url;
	}
	
}
