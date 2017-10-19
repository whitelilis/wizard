package util;

public class ADHelper {
    /**
     * Raw data, like
     * E6F83B68-B052-44B3-8EF5-79136FAA033A
     * @param key
     * @return
     */
    public static boolean isIDFA(String key) {
        if (key == null || key.length() == 0) {
            return false;
        } else {
            boolean validFormat =  key.length() == 36 && key.split("-", -1).length == 5;

            if (validFormat) {
                String idfa = key.replaceAll("-", "");
                return allHex(idfa);
            } else {
                return false;
            }
        }
    }



    /**
     * hex string with length 40 or 36
     * 4585380591B60CAB56A85523A8106DAABD5A8765
     * @param key
     * @return
     */
    public static boolean isUDID(String key) {
        if (key == null || key.length() == 0) {
            return false;
        } else  {
            boolean validFormat =  key.length() == 40 || key.length() == 36;
            if (validFormat) {
                return allHex(key);
            } else {
                return false;
            }
        }
    }


    /**
     * string with length  14 or 15 ( last for checksum )
     * @param key
     * @return
     */
    public static boolean isIMEI(String key) {
        return key.length() == 14 || key.length() == 15;
    }


    public static boolean isAndroidId(String key) {
        return isMd5(key);
    }


    public static boolean isMd5(String key) {
        return key.matches("[a-fA-F0-9]{32}");
    }

    public static boolean allHex(String str) {
        return str.matches("[a-fA-F0-9]+");
    }

}
