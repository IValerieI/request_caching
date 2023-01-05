package uni2022.samarchenko_v_l.task_4;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ReadWebPage {

    public static String getHTML(String URL) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .GET() // GET is default
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

//        HttpResponse<Path> response = client.send(request,
//                HttpResponse.BodyHandlers.ofFile(Paths.get("example.html")));

        return response.body();
    }
}
