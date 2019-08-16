package native_tests;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.X509Certificate;
import java.util.UUID;

/**
 * Class that allows sending request to install application under testing
 */
public class CloudClient {

    public static void remoteInstall(String cloudURL, String udid, String filePath) throws Exception {

        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);


        URL url = new URL(cloudURL + "/" + udid);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestProperty("Authorization", "Bearer " + System.getenv("CLOUD_TOKEN"));
        String boundary = UUID.randomUUID().toString();
        con.setRequestMethod("POST");
        con.setDoOutput(true);

        con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

        //prepare wrapper to send file in a request
        try (DataOutputStream request = new DataOutputStream(con.getOutputStream())) {
            Path path = Paths.get(filePath);

            request.writeBytes("--" + boundary + "\r\n");
            request.writeBytes("Content-Disposition: form-data; name=\"description\"\r\n\r\n");
            request.writeBytes(path.getFileName() + "\r\n");

            request.writeBytes("--" + boundary + "\r\n");
            request.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + filePath + "\"\r\n\r\n");

            byte[] fileContent = Files.readAllBytes(path);
            request.write(fileContent);
            request.writeBytes("\r\n");

            request.writeBytes("--" + boundary + "--\r\n");
            request.flush();
        }

        //check response
        int code = con.getResponseCode();

        if (code != 201) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                throw new Exception(response.toString());
            }
        }


    }

}
