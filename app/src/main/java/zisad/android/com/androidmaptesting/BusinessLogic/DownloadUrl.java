package zisad.android.com.androidmaptesting.BusinessLogic;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadUrl {

    public String readUrl(String myUrl) throws Exception{
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(myUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            //read data from url
            inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();

            //read each line one by one
            String line = "";
            while((line = bufferedReader.readLine())!=null){
                stringBuffer.append(line);
            }

            data = stringBuffer.toString();
            bufferedReader.close();

        }
        finally {
            inputStream.close();
            connection.disconnect();
        }

        return data;
    }
}
