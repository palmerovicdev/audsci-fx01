package com.suehay.audscifx.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * La clase FileLocator proporciona métodos para cargar archivos desde el sistema de archivos.
 *
 * @author Bikoru
 */
public class FileLocator {

    /**
     * Obtiene la ruta completa de un archivo dado su nombre, buscando en el directorio donde
     * se encuentra el archivo .jar si se está ejecutando desde un archivo de este tipo, y en
     * el directorio que contiene los archivos pom.xml, src y target si se está ejecutando desde un IDE.
     *
     * @param filename el nombre del archivo que se desea obtener la ruta, incluyendo su extensión.
     * @return la ruta completa del archivo, incluyendo el nombre del archivo y su extensión, en caso de encontrarlo.
     * @throws URISyntaxException si no se puede obtener correctamente la URI del archivo.
     * @throws IOException        si no se puede obtener correctamente la ruta canónica del archivo.
     */
    public static String getPath(String filename) throws URISyntaxException, IOException {
        String DirectoryPath;
        var path_separator = System.getProperty("file.separator");
        String JarURL;
        File auxiliar = new File(FileLocator.class
                                         .getProtectionDomain()
                                         .getCodeSource()
                                         .getLocation()
                                         .toURI()
                                         .getPath());

        // Si es un directorio es que estamos ejecutando desde el IDE. En este caso
        // habrá que buscar el fichero en la carperta abuela(junto a las carpetas "src" y "target·
        if (auxiliar.isDirectory()) {
            auxiliar = new File(auxiliar.getParentFile().getParentFile().getPath());
            DirectoryPath = auxiliar.getCanonicalPath() + path_separator;
        } else {
            JarURL = auxiliar.getCanonicalPath();
            DirectoryPath = JarURL.substring(0, JarURL.lastIndexOf(path_separator) + 1);
        }

        System.out.println(DirectoryPath + filename);
        return DirectoryPath + filename;
    }
}