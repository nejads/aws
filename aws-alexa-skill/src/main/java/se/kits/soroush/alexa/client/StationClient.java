package se.kits.soroush.alexa.client;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import se.kits.soroush.alexa.client.model.Station;
import se.kits.soroush.alexa.client.model.StationBoardWrapper;

public class StationClient {

    private static final Logger log = LoggerFactory.getLogger(StationClient.class);
    private static final String URL = "http://api.tagtider.net/v1/stations.json";
    private static final String username;
    private static final String password;

    private HttpHost target;
    private HttpGet httpget;
    private CloseableHttpClient httpClient;
    private UsernamePasswordCredentials credentials;

    static {
        username = System.getenv("API_USERNAME");
        password = System.getenv("API_PASSWORD");
    }

    public StationClient() {
        httpClient = HttpClients.createDefault();
        httpget = new HttpGet(URL);
        target = new HttpHost(httpget.getURI().getHost(), 80, "http");
        credentials = new UsernamePasswordCredentials(username, password);
    }

    public List<Station> getStations() throws Exception {
        try {
            Map<String, String> realm = getRealmFromServer();
            HttpClientContext context = getClientContext(realm);
            CloseableHttpResponse response = httpClient.execute(target, httpget, context);

            int responseHttpCode = response.getStatusLine().getStatusCode();
            if (responseHttpCode == HttpStatus.SC_OK) {
                ObjectMapper mapper = new ObjectMapper();
                StationBoardWrapper wrapper = mapper.readValue(response.getEntity().getContent(),
                        StationBoardWrapper.class);
                log.info("Stations: {}", getStations());
                return wrapper.getStationBoard().getStationList();

            } else {
                throw new Exception("Http status code from the server was " + responseHttpCode);
            }

        } finally {
            httpClient.close();
        }
    }

    private HttpClientContext getClientContext(Map<String, String> wwwAuth) throws AuthenticationException {
        HttpClientContext localContext = HttpClientContext.create();
        DigestScheme digestAuth = getDigestScheme(wwwAuth);
        AuthCache authCache = new BasicAuthCache();
        authCache.put(target, digestAuth);
        localContext.setAuthCache(authCache);

        Header authenticate = digestAuth.authenticate(credentials, httpget, localContext);
        httpget.addHeader(authenticate);
        return localContext;
    }

    private DigestScheme getDigestScheme(Map<String, String> wwwAuth) {
        DigestScheme digestAuth = new DigestScheme();
        digestAuth.overrideParamter("qop", "auth");
        digestAuth.overrideParamter("nc", "0");
        digestAuth.overrideParamter("cnonce", DigestScheme.createCnonce());
        digestAuth.overrideParamter("opaque", wwwAuth.get("opaque"));
        digestAuth.overrideParamter("nonce", wwwAuth.get("nonce"));
        digestAuth.overrideParamter("realm", wwwAuth.get("Digest realm"));
        return digestAuth;
    }

    //Send a request to get Realm from server.
    private Map<String, String> getRealmFromServer() throws IOException {
        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(new AuthScope(target.getHostName(), target.getPort()), credentials);

        CloseableHttpResponse response = httpClient.execute(target, httpget);
        return Arrays
                .stream(response.getHeaders("WWW-Authenticate")[0].getElements())
                .collect(Collectors.toMap(HeaderElement::getName, HeaderElement::getValue));

    }

}
