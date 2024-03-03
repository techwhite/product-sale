package com.ecom.productsale.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.util.Base64;

/*
This class defines few security method for calling to visa service, like getSSLSocketFactory, getAuthorization info, and
generate ResponseContent
 */
@Component
public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    // username for authentication when calling visa service
    @Value("${visa.service.username}")
    private String userName;

    // password for authentication when calling visa service
    @Value("${visa.service.password}")
    private String passWord;

    // The path of keystore which saves key and certificate used to call visa service
    @Value("${visa.service.path.ks.p12}")
    private String keyStorePath;

    // password used to read keystore file
    @Value("${visa.service.password.ks.p12}")
    private String keyStorePassword;

    /*
    Used to get SSLSocketFactory by loading keystore from file and initing keyManagerFactory&SSLContext
     */
    public SSLContext getSSLContext() throws Exception {
        String keystorePath = keyStorePath;
        String keystorePassword = keyStorePassword;

        // Make a KeyStore from the PKCS-12 file
        KeyStore ks = KeyStore.getInstance("PKCS12");
//        KeyStore ks = KeyStore.getInstance("jks");
        try (FileInputStream fis = new FileInputStream(keystorePath)) {
            ks.load(fis, keystorePassword.toCharArray());
        }

        // Make a KeyManagerFactory from the KeyStore
//        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, keystorePassword.toCharArray());

        // Now make an SSL Context with our Key Manager and the default Trust Manager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, null);

        return sslContext;
    }

    /*
    Util method used to generate auth string based on username and password
     */
    public String getAuthorization() {
        String userId = userName;
        String password = passWord;

        // encode auth string generated from userid and password
        String auth = userId + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));

        return "Basic " + new String(encodedAuth);
    }

    /*
    Util method used to get response content from http response
     */
    public String getResponseContent(HttpURLConnection con, int status) throws Exception {
        if (con == null) {
            return null;
        }

        // convert stream reader into buffer reader
        BufferedReader in;
        if (status == 200) {
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {
            in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }

        // read response into a string buffer
        String response;
        StringBuffer content = new StringBuffer();
        while ((response = in.readLine()) != null) {
            content.append(response);
        }
        in.close();

        return content.toString();
    }
}
