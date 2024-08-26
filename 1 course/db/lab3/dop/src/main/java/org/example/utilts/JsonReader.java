package org.example.utilts;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONObject;

public class JsonReader {

    public static JSONObject readJsonFromUrl(String url) throws Exception {
        // Создание HTTP клиента
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // Создание HTTP запроса
        HttpGet request = new HttpGet(url);

        // Отправка запроса и получение ответа
        CloseableHttpResponse response = httpClient.execute(request);

        try {
            // Извлечение сущности ответа
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                // Преобразование сущности в строку
                String result = EntityUtils.toString(entity);

                // Парсинг строки в JSONObject
                return new JSONObject(result);
            } else {
                throw new Exception("No response received");
            }
        } finally {
            // Закрытие ответа и клиента
            response.close();
            httpClient.close();
        }
    }
}
