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

    public static String[] decodedString(String string){
        
    }

}
