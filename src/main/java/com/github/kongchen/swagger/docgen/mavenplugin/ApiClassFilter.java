/*
 * Copyright (c) 2015 EidosMedia S.p.A.. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of EidosMedia S.p.A.. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with EidosMedia.
 * 
 * EIDOSMEDIA MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGEMENT. EIDOSMEDIA SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package com.github.kongchen.swagger.docgen.mavenplugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import com.google.common.base.Predicate;

public class ApiClassFilter implements Predicate<Class<?>> {

    private final ClassLoader classLoader;
    
    public ApiClassFilter(List<?> classpathElements) throws MalformedURLException {
        URL[] classpathElementsUrls = new URL[classpathElements.size()];

        for (int i = 0; i < classpathElementsUrls.length; i++) {
            String classpathElement = String.valueOf(classpathElements.get(i));
            File classpathElementFile = new File(classpathElement);
            URL classpathElementUrl = classpathElementFile.toURI()
                    .toURL();
            classpathElementsUrls[i] = classpathElementUrl;
        }

        classLoader = new URLClassLoader(classpathElementsUrls, null);
    }

    @Override
    public boolean apply(Class<?> input) {
        String className = input.getName();
        try {
            Class.forName(className, true, classLoader);
            return false;
        } catch (ClassNotFoundException e) {
            return true;
        }
    }

}
