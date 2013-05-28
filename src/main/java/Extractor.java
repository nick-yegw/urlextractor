import com.google.common.base.Preconditions;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Extractor {

    public static void main(String[] args) {
        Preconditions.checkState(args.length == 2);

        try {
            URL url = new URL(args[0]);
            URLConnection conn = url.openConnection();
            String encode = getURLEncode(url);

            FileOutputStream fos = new FileOutputStream(new File(args[1]));
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), encode));

            String line;
            while ((line = reader.readLine()) != null){
                fos.write(line.getBytes(encode));
                fos.write("\n".getBytes());
            }
            reader.close();
            fos.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getURLEncode(URL url) throws IOException {
        Preconditions.checkNotNull(url);
        URLConnection conn = url.openConnection();
        String encode = conn.getContentEncoding();
        if(encode != null){
            return encode;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        String reg = "meta http-equiv=\"Content-Type\" content=\".*?charset=(.*?)\"";
        Pattern p = Pattern.compile(reg);

        while((line = reader.readLine())!=null){
            Matcher m = p.matcher(line);
            if(m.find()){
                return m.group(1);
            }
        }
        return "UTF-8";
    }
}
