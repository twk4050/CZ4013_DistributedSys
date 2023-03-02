import entity.Constant;


public class marshalling {
    public static byte[] stringtobyte(String converting, int rnd){
        byte[] converted = converting.getBytes(Constant.commonCharset);
        return converted;
    }    
    public static String bytetoString(byte[] converting){
        String converted = new String(converting, Constant.commonCharset);
        return converted;
    }

    public static String[] decodeMessage(String string){
        String[] token = string.split("\\|");
        return token;
    }

    public static String[] decodedString(String string){
        String[] tokens = string.split(":");
        return tokens;
    }

    public static int msglength(byte[] bytes){
        String str = bytetoString(bytes);
        return str.length();
    }

}
