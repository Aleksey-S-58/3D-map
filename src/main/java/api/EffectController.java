package api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.EffectService;

/**
 * This controller provides an access to various sound and other art effect files.
 * 
 * @author Aleksey Shishkin
 *
 */
@Controller
public class EffectController {
	
	public static final String ALLOW = "Allow";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String CONTENT_LENGTH = "Content-Length";
	
	/**
	 * TODO move it to a separate file when api-service will be modified. 
	 */
	public static final String INFO = "{\"description\": \"This method returns a sound file as an array of bytes\"," + 
		"\"usage\": \"this sound tracs contains a description of a landmark etc ...\"," +
    	"\"urlPattern\": \"/map/story/{name}\"," +
    	"\"parameterType\": \"String\"," +
    	"\"contentType\": \"application/x-tgif\"," +
    	"\"method\": \"GET\"}";

	private EffectService effectService;
	
	public EffectController(EffectService effectService) {
		this.effectService = effectService;
	}
	
	private ResponseEntity<String> options(String allow, String info) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(ALLOW, allow);
		headers.add(CONTENT_TYPE, "text/html; charset=UTF-8");
		headers.add(CONTENT_LENGTH, String.valueOf(info.length()));
		ResponseEntity<String> response = new ResponseEntity<>(info, headers, HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(value = "/map/story", method = RequestMethod.OPTIONS, produces = "text/html")
	@ResponseBody
	public ResponseEntity<String> getSpriteOptions() {
		return options("GET,OPTIONS", INFO);
	}
	
	/**
	 * headers:
	 * <br>Accept-Ranges: bytes
	 * <br>Content-Length: ...
	 * <br>Content-Type: application/x-tgif, 
	 * audio/basic, audio/mpeg, audio/mp4, audio/vnd.wav
	 * @param name - a name of story
	 * @return an audio file
	 */	
	@RequestMapping(path = "/map/story/{name}", method = RequestMethod.GET, 
			produces = {"application/x-tgif", "audio/basic", "audio/mpeg", "audio/mp4", "audio/vnd.wav"})
	@ResponseBody
	public byte[] getSprite(@PathVariable("name") String name) {
		return effectService.getStory(name);
	}
}
