package api.modules.response;

import org.apache.logging.log4j.Logger;

import io.restassured.response.Response;
import logging.LoggerUtils;

public class APIResponse<T> {
	
	protected final Logger log = LoggerUtils.getLogger(getClass());
	  private final Response rawResponse;
	  private final T body;

	  public APIResponse(Response rawResponse, Class<T> clazz) {
	      this.rawResponse = rawResponse;
	      this.body = deserializeBody(rawResponse, clazz);
	  }
	  
	  private T deserializeBody(Response response, Class<T> clazz) {
		    try {
		        String contentType = response.getContentType();

		        if (contentType == null || !contentType.contains("json")) {
		            log.warn("Skipping deserialization. Response is not JSON. Content-Type: {}", contentType);
		            return null;
		        }

		        return response.as(clazz);

		    } catch (Exception e) {
		        throw new RuntimeException(
		            "Failed to deserialize response to " + clazz.getSimpleName() +
		            "\nResponse body:\n" + response.getBody().asString(), e);
		    }
	  }

	  public int getStatusCode() {
	      return rawResponse.getStatusCode();
	  }

	  public T getBody() {
	      return body;
	  }

	  public Response getRawResponse() {
	      return rawResponse;
	  }
}
