package native_tests;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.nio.file.Paths;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Class that allows sending request to install application under testing - with Apache HttpClient
 */
public class CloudClient {

    public static void remoteInstall(String cloudURL, String udid, String filePath) throws Exception {


        // Generates client that accepts self-signed certificates
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();

        HttpPost installRequest = new HttpPost(cloudURL + "/" + udid);

        //authorize with token
        installRequest.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + System.getenv("CLOUD_TOKEN"));

        //post a file (multipart)
        String fileName = Paths.get(filePath).getFileName().toString();

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("file", new File(filePath), ContentType.APPLICATION_OCTET_STREAM, fileName);

        HttpEntity multipart = builder.build();
        installRequest.setEntity(multipart);

        CloseableHttpResponse response = httpClient.execute(installRequest);

        //check if post request is successful
        int code = response.getStatusLine().getStatusCode();
        if (code != 201) {
            throw new Exception(response.getStatusLine().toString());
        }
        httpClient.close();
    }
}
