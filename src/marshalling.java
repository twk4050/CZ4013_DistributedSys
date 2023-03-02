import entity.Constant;

final class Marshalling {
    public static byte[] stringtobyte(String converting, int rnd) {
        byte[] converted = converting.getBytes(Constant.commonCharset);
        return converted;
    }

    public static String bytetoString(byte[] converting) {
        String converted = new String(converting, Constant.commonCharset);
        return converted;
    }

    // A utility method to convert the byte array data into a string representation.
    public static StringBuilder convertByteToStringBuilder(byte[] a) {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0) {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }

    public static int msglength(byte[] bytes) {
        String str = bytetoString(bytes);
        return str.length();
    }

    public static String getRequirementId(String s) {
        String[] splitColon = s.split(":");
        String[] caseAndFunctionArgs = splitColon[1].split("\\|");

        return caseAndFunctionArgs[0];
    }

    public static String getRequestId(String string) {
        String[] tokens = string.split(":");
        return tokens[0];
    }

    public static String[] getCaseAndFnArgs(String string) {
        String[] tokens = string.split(":");
        String[] caseAndFnArgs = tokens[1].split("\\|");

        return caseAndFnArgs;
    }

}
