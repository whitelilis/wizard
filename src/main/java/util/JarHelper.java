package util;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;

public class JarHelper {
    public static Logger logger = Logger.getLogger(JarHelper.class);
    public static void localOrJarFileToMap(String pathStr, HashMap<String, String> aim, String delimiter) throws IOException {
        BufferedReader br = getLocalOrJarReader(pathStr);
        String line = br.readLine();
        while(line != null){
            String[] parts = line.split(delimiter, -1);
            if (parts.length >1) {
                aim.put(parts[0], parts[1]);
            } else if (parts.length ==1){
                aim.put(parts[0], "");
            } else {
                throw new IOException(pathStr + " read error!");
            }
            line = br.readLine();
        }
        br.close();
    }

    public static BufferedReader getLocalOrJarReader(String pathStr) throws IOException {
        File localFile = new File(pathStr);
        InputStream jarStream = JarHelper.class.getClassLoader().getResourceAsStream(pathStr);
        if(jarStream != null){
            logger.info(pathStr + " using JAR resource file");
            return new BufferedReader(new InputStreamReader(jarStream));
        }else if(localFile.exists()){
            logger.info(pathStr + " using local file");
            return new BufferedReader(new FileReader(localFile));
        }else{ // no local file, and not in jar resource
            throw new IOException("file #" + pathStr + "# not exist either local and in jar resource");
        }
    }
}
