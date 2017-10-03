package fr.intech.s5.tp.ws_rest_android.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpHelper {

    /**
     * Returns le contenu de votre URL sous format String
     *
     * @param requestPackage
     * @return
     * @throws IOException
     */
    public static String downloadFromFeed(RequestPackage requestPackage) throws IOException {
        // Récupération de l'adresse url du service
        String address = requestPackage.getEndpoint();
        //Récupération des paramètres de l'adresse
        String encodedParams = requestPackage.getEncodedParams();

        //Si c'est une méthode GET et contient des paramètres, on reconstruit l'adresse
        if (requestPackage.getMethod().equals("GET") &&
                encodedParams.length() > 0) {
            //Réecriture de l'adresse en concaténant le endpoint avec les paramètres
            address = String.format("%s?%s", address, encodedParams);
        }

        //déclaration d'un client okhttp
        OkHttpClient client = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(address);

        if (requestPackage.getMethod().equals("POST")) {
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            Map<String, String> params = requestPackage.getParams();
            for(String key : params.keySet()) {
                builder.addFormDataPart(key, params.get(key));
            }
            RequestBody requestBody = builder.build();
            requestBuilder.method("POST", requestBody);
        }

        Request request = requestBuilder.build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            return response.body().string();
        }else {
            throw new IOException("Exception : code retour " + response.code());
        }
    }
}
