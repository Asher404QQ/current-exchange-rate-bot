package ru.kors.currentexchangeratebot.client;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import ru.kors.currentexchangeratebot.exception.ServiceException;

import java.io.IOException;

@Configuration
public class CbrClient {
    @Autowired
    private OkHttpClient okHttpClient;
    @Value("${cbr.current.rate.xml.url}")
    private String url;

    public String getCurrentRate() throws ServiceException {
        var request = new Request.Builder().url(url).build();
        try (var response = okHttpClient.newCall(request).execute()) {
            var body = response.body();
            return body == null ? null : body.string();
        } catch (IOException e) {
            throw new RuntimeException("Unable connect to cbr.ru", e);
        }
    }
}
