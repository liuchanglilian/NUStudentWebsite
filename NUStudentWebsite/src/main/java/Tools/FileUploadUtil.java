package Tools;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FileUploadUtil {
     public static final List<String> ALLOW_TYPES = Arrays.asList(
                "image/jpg","image/jpeg","image/png","image/gif"
        );
    public static String rename(String fileName){
            int i = fileName.lastIndexOf(".");
            String str = fileName.substring(i);
            return new Date().getTime()+""+ new Random().nextInt(99999999) +str;
        }
      public static boolean allowUpload(String postfix){
            return ALLOW_TYPES.contains(postfix);
        }
}
