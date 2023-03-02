
public class Marshalling {
    public Marshalling() {
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

    // <requestId:requirementId|flightId>
    public static String getRequestId(String s) {
        String[] tokens = s.split(":");
        return tokens[0];
    }

    public static String getRequirementId(String s) {
        String[] tokens = s.split(":");
        String[] caseAndFnArgs = tokens[1].split("\\|");

        return caseAndFnArgs[0];
    }

    public static String[] getCaseAndFnArgs(String string) {
        String[] tokens = string.split(":");
        String[] caseAndFnArgs = tokens[1].split("\\|");

        return caseAndFnArgs;
    }

}
