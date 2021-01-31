package br.ufu.facom.apl.gui;

import br.ufu.facom.apl.core.ActiveLearningStrategy;
import br.ufu.facom.apl.core.MetaCategorizer;
import br.ufu.facom.apl.core.interceptor.Interceptable;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Service {

    public static HashMap<Class<?>, HashMap<String, Object>> digestClasspathArray(final File[] classpathArray)
            throws ServiceException {

        final URLClassLoader classLoader = buildClassLoader(classpathArray);
        final HashMap<Class<?>, HashMap<String, Object>> instancesMapByClass = new HashMap<>();

        instancesMapByClass.put(Interceptable.class, new HashMap<>());
        instancesMapByClass.put(MetaCategorizer.class, new HashMap<>());
        instancesMapByClass.put(ActiveLearningStrategy.class, new HashMap<>());

        for (final File classpath : classpathArray) {

            final File[] files;

            if (classpath.isFile()) {
                files = new File[]{classpath};
            } else {
                files = classpath.listFiles();
                if (files == null) {
                    continue;
                }
            }

            for (final File file : files) {

                final List<String> classesNames = discoverClasses(file);

                for (String className : classesNames) {

                    final Class<?> classReference;
                    try {
                        classReference = classLoader.loadClass(className);
                    } catch (NoClassDefFoundError | ClassNotFoundException e) {
                        throw new ServiceException("Failed to load class " + className, e);
                    }

                    final Object instance;

                    try {
                        Constructor<?> constructor = classReference.getConstructor();
                        instance = constructor.newInstance();
                    } catch (NoClassDefFoundError e) {
                        throw new ServiceException("Failed to load class " + className, e);
                    } catch (Exception ex) {
                        continue;
                    }

                    if (instance instanceof Interceptable) {
                        instancesMapByClass.get(Interceptable.class).put(className, instance);
                    } else if (instance instanceof MetaCategorizer) {
                        instancesMapByClass.get(MetaCategorizer.class).put(className, instance);
                    } else if (instance instanceof  ActiveLearningStrategy) {
                        instancesMapByClass.get(ActiveLearningStrategy.class).put(className, instance);
                    }

                }
            }
        }

        if (instancesMapByClass.get(Interceptable.class).isEmpty()) {
            throw new ServiceException("No Interceptable interface implementation.");
        }
        if (instancesMapByClass.get(MetaCategorizer.class).isEmpty()) {
            throw new ServiceException("No MetaCategorizer interface implementation.");
        }
        if (instancesMapByClass.get(ActiveLearningStrategy.class).isEmpty()) {
            throw new ServiceException("No ActiveLearningStrategy interface implementation.");
        }

        return instancesMapByClass;

    }

    private static URLClassLoader buildClassLoader(final File[] classpathArray) throws ServiceException {

        final List<URL> urls = new ArrayList<>();

        for (final File classpath : classpathArray) {

            if (classpath.getName().endsWith(".jar")) {

                try {
                    urls.add(new URL(toJarPath(classpath.getAbsolutePath())));
                } catch (MalformedURLException e) {
                    throw new ServiceException("Failed to load jar " + classpath.getAbsolutePath(), e);
                }

            } else if (classpath.isDirectory()) {

                try {
                    urls.add(classpath.toURI().toURL());
                } catch (MalformedURLException e) {
                    throw new ServiceException("Failed to load " + classpath.getAbsolutePath(), e);
                }

                final File[] files = classpath.listFiles();
                if (files == null) {
                    continue;
                }

                final List<String> jarsPaths = discoverJarsInRepository(classpath);

                for (final String jarPath : jarsPaths) {
                    try {
                        urls.add(new URL(toJarPath(jarPath)));
                    } catch (MalformedURLException e) {
                        throw new ServiceException("Failed to load " + classpath.getAbsolutePath(), e);
                    }
                }
            }

        }

        return URLClassLoader.newInstance(urls.toArray(new URL[]{}));

    }

    private static List<String> discoverJarsInRepository(final File repository) {
        final List<String> paths = new ArrayList<>();
        discoverJarsInRepository(repository, paths);
        return paths;
    }

    private static void discoverJarsInRepository(final File repository, final List<String> paths) {

        if (repository.isDirectory()) {

            final File[] files = repository.listFiles();

            if(files != null) {
                for (final File file : files) {
                    discoverJarsInRepository(file, paths);
                }
            }

        } else if (repository.getName().endsWith(".jar")) {
            paths.add(repository.getAbsolutePath());
        }

    }

    private static List<String> discoverClasses(final File root) throws ServiceException {
        final List<String> classesNames = new ArrayList<>();
        discoverClasses(root, "", classesNames);
        return classesNames;
    }

    private static void discoverClassesInJar(final JarFile jar, final List<String> classesNames) {


        Enumeration<JarEntry> enumeration = jar.entries();

        while (enumeration.hasMoreElements()) {

            final JarEntry je = enumeration.nextElement();

            if (je.isDirectory() || !je.getName().endsWith(".class")) {
                continue;
            }

            final String className = je.getName().substring(0, je.getName().length() - ".class".length());
            classesNames.add(className.replace('/', '.'));

        }

    }

    private static void discoverClasses(final File root, String prefix, final List<String> classesNames)
            throws ServiceException {

        if (!prefix.isEmpty()) {
            prefix += ".";
        }

        if (root.isDirectory()) {

            prefix += root.getName();

            final File[] files = root.listFiles();

            if(files != null) {
                for (final File file : files) {
                    discoverClasses(file, prefix, classesNames);
                }
            }

        } else if (root.getName().endsWith(".jar")) {

            try {
                discoverClassesInJar(new JarFile(root), classesNames);
            } catch (IOException e) {
                throw new ServiceException("Failed to read " + root.getAbsolutePath(), e);
            }

        } else if (root.getName().endsWith(".class")) {

            classesNames.add(prefix + root.getName().replace(".class", ""));

        }

    }

    private static String toJarPath(final String path) {
        return "jar:file:" + path + "!/";
    }

}
