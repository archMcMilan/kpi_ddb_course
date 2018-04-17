package kpi.tef.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import kpi.tef.dto.ItemResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class Controller {

    @FXML
    private TextField idText;
    @FXML
    private TextField descText;
    @FXML
    private TextField barcodeText;
    @FXML
    private TextArea outputText;

    @FXML
    public void create(ActionEvent event) {
        try {
            URL url = new URL(getBaseUrl() + "/item/add");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("content-type", "application/json");
            conn.setDoOutput(true);
            String body = "{" +
                    "    \"barcode\": \"" + barcodeText.getText() + "\",\n" +
                    "    \"description\" : \"" + descText.getText() + "\"\n" +
                    "}";
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(body.getBytes());
            outputStream.flush();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            getOutput(conn);
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void update(ActionEvent event) {
        try {
            URL url = new URL(getBaseUrl() + "/item/update/" + idText.getText());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("content-type", "application/json");
            conn.setDoOutput(true);
            String body = "{" +
                    "    \"barcode\": \"" + barcodeText.getText() + "\",\n" +
                    "    \"description\" : \"" + descText.getText() + "\"\n" +
                    "}";
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(body.getBytes());
            outputStream.flush();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            getOutput(conn);
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void delete(ActionEvent event) {
        try {
            URL url = new URL(getBaseUrl() + "/item/delete/" + idText.getText());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("content-type", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            getOutput(conn);
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void getAll(ActionEvent event) {
        try {
            URL url = new URL(getBaseUrl() + "/item");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("content-type", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            getOutput(conn);
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getOutput(HttpURLConnection conn) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output = bufferedReader.readLine();
        System.out.println(output);

        ObjectMapper mapper = new ObjectMapper();
        ItemResponse itemResponse = mapper.readValue(output, ItemResponse.class);

        StringBuffer outputTextBuffer = new StringBuffer();
        itemResponse.getItemDtos().forEach(item->outputTextBuffer.append(item.toString()+"\n"));

        outputText.setText(outputTextBuffer.toString());

    }

    private String getBaseUrl() {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties.getProperty("url");
    }

}
