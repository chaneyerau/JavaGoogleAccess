
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ApplicationProject {
    public static String main(String[] args) {
       
        String pageContents = "";
        String address = "https://www.geeksforgeeks.org/java/java-string-indexof/";

        try{
            if(address.indexOf("https://") != 0){
                throw new MalformedURLException("HTTPS is needed!");
            }
            URL pageUrl = new URL(address);
            HttpsURLConnection pageConnection = (HttpsURLConnection) pageUrl.openConnection();
            pageConnection.connect();

            try(BufferedReader pageReader = new BufferedReader(new InputStreamReader(pageConnection.getInputStream()))){
                String pageLine = pageReader.readLine();

                while(pageLine != null){
                    pageLine.replaceAll("<[^>]*>", "");
                    pageLine = pageLine.trim();
                    if(pageLine.length() > 0){
                        pageContents += pageLine;
                    }
                    pageLine = pageReader.readLine();
                }
                pageConnection.disconnect();
            }catch(IOException inputError){
                pageContents = "The page contents of address: <" + address + "> were not read! \n";
                pageContents += "Inner error:\n";
                pageContents = inputError.getMessage();
            }
        }catch(MalformedURLException badAddress){
                pageContents = "The page contents of address: <" + address + "> were not read! \n";
                pageContents += "URL error:\n";
                pageContents = badAddress.getMessage();
        }catch(IOException connectionError){
                pageContents = "The page contents of address: <" + address + "> were not read! \n";
                pageContents += "Connection error:\n";
                pageContents = connectionError.getMessage();
        }

        return pageContents;
    }
}


