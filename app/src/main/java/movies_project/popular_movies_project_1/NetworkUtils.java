package movies_project.popular_movies_project_1;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    private NetworkUtils(){

    }

    public static String httpConnectionResponse(URL url) throws IOException{
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream inputStream = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");
            boolean input = scanner.hasNext();
            if(input){
                return scanner.next();
            }
            else{
                return null;
            }
        }   finally {
            httpURLConnection.disconnect();
        }
    }
}
