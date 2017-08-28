import com.cisco.ccbu.common.ids.client.*;
import com.cisco.ccbu.common.ids.client.impl.*;
import com.cisco.ccbu.common.ids.client.factory.IdSClientFactory;

public class SampleIdSClient {
	public static void main(String[] args) throws java.io.IOException,IdSClientException {
		// During SSO Service Startup
		// Load application specific IdS Client Configuration
		IdSClientConfigurationImpl config = new IdSClientConfigurationImpl("/tmp/idsclient.conf");
		config.load();

		// Instantiate IdS Client
		IdSClient client = IdSClientFactory.getIdSClient();
		client.setTLSContext(createSSLTrustManager(), createHostnameVerfier());

		// Init IdS Client with application specific Configuration loaded earlier
		client.init(config);
		System.out.println("config " + config.toString());
		System.out.println("from config - sso.mode=" + config.getProperty("sso.mode"));
		System.out.println("from client - sso.mode=" + client.getIdSClientConfiguration().getSSOMode());


		// Get Access Token for the received Authorization Code
		String redirectURI = config.getRedirectUri();
		AccessToken token = client.getAccessToken(authCode, redirectURI);

		String accessTokenString = token.getAccess_token();
		// pass the above access token to UniAgentDesktop
		

		// To refresh the token before expiry
		AccessToken refreshedToken = client.refreshAccessToken(token.getAccess_token(), token.getRefresh_token());

		// On agent logout, remove the OAuth token from ids client cache
		client.removeToken(accessToken);


		// Cleanup when SSO Service is shutting down
		((IdSStatusMonitorImpl)client.getIdSStatusMonitor()).cleanUp();
		client.setTokenCaching(false);
	}
}
