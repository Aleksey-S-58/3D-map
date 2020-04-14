package service.database.object.loader;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import data.EffectType;
import repository.StoryRepository;
import service.EffectService;

/**
 * This service provides loading of sound tracks 
 * and other various art effects from a database via {@link AbstractLoader}.
 * 
 * @author Aleksey Shishkin
 *
 */
@Service
public class EffectLoader extends AbstractLoader implements EffectService {
	
	private static final String MESSAGE_FORMAT = "storyRepository: %1$s";
	
	private StoryRepository storyRepository;
	
	public EffectLoader(StoryRepository storyRepository) {
		super();
		logger = LoggerFactory.getLogger(EffectLoader.class);
		if (storyRepository == null) {
			String message = String.format(MESSAGE_FORMAT, storyRepository);
			throw new IllegalArgumentException(message);
		}
		this.storyRepository = storyRepository;
	}

	@Override
	public byte[] getStory(String name) {
		return loadBytes(name, storyRepository, EffectType.STORY.name());
	}

}
