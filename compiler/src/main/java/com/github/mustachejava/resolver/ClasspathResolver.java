package com.github.mustachejava.resolver;

import com.github.mustachejava.MustacheResolver;
import com.google.common.base.Charsets;
import java.io.*;

/**
 * MustacheResolver implementation that resolves
 * mustache files from the classpath.
 */
public class ClasspathResolver implements MustacheResolver {

  private final String resourceRoot;

  public ClasspathResolver() {
    this.resourceRoot = null;
  }

  /**
   * Use the classpath to resolve mustache templates.
   *
   * @param resourceRoot
   */
  public ClasspathResolver(String resourceRoot) {
    if (!resourceRoot.endsWith("/")) {
      resourceRoot += "/";
    }
    this.resourceRoot = resourceRoot;
  }

  @Override
  public Reader getReader(String resourceName) {
    ClassLoader ccl = Thread.currentThread().getContextClassLoader();
    String name = (resourceRoot == null ? "" : resourceRoot) + resourceName;
    InputStream is = ccl.getResourceAsStream(name);
    if (is == null) {
      is = ClasspathResolver.class.getClassLoader().getResourceAsStream(name);
    }
    if (is != null) {
      return new BufferedReader(new InputStreamReader(is, Charsets.UTF_8));
    } else {
      return null;
    }
  }

  public String getResourceRoot() {
    return resourceRoot;
  }

}
