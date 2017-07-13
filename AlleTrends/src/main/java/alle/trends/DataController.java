package alle.trends;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import connection.ApiConnector;

@RestController
@RequestMapping("/dataController")
public class DataController {

	ApiConnector ac;

	@PostConstruct
	public void init() {
		ac = new ApiConnector();
	}

	@RequestMapping("/gratis")
	public List<String> chuj(@RequestParam(value = "session") String sessionId) {
		return ac.getGratisItems(sessionId);
	}
}
