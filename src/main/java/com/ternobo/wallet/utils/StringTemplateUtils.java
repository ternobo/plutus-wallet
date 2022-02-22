package com.ternobo.wallet.utils;

import org.thymeleaf.templateresource.ClassLoaderTemplateResource;

public class StringTemplateUtils {
    public static String baseDir = "templates";

    /**
     * Check if a template exists in subfolder templates.
     *
     * @param templateName relative name of template below the basedir.
     * @return true if exists, otherwise false
     */
    public static boolean templateExists(final String templateName) {
        final ClassLoaderTemplateResource iTemplateResource =
                new ClassLoaderTemplateResource(baseDir + "/" + templateName, "UTF8");
        return iTemplateResource.exists();
    }

}
