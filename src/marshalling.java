import entity.Constant;

public class marshalling {
    public static byte[] stringtobyte(String converting, int rnd) {
        byte[] converted = converting.getBytes(Constant.commonCharset);
        return converted;
    }

    public static String bytetoString(byte[] converting) {
        String converted = new String(converting, Constant.commonCharset);
        return converted;
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
