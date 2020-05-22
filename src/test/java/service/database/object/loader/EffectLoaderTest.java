package service.database.object.loader;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import data.Story;
import repository.StoryRepository;
import service.EffectService;

/**
 * Some non integration tests for EffectLoader.
 * @author Aleksey Shishkin
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class EffectLoaderTest {
	
	private static final String TEST_STORY_NAME = "Test-story";
	
	@Mock
	private StoryRepository storyRepository;
	
	private byte[] testBytes = {0, 1, 2, 3};
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFail() {
		EffectLoader loader = new EffectLoader(null);
	}
	
	@Test
	public void testConstructor() {
		EffectLoader loader = new EffectLoader(storyRepository);
		Assert.assertNotNull(loader);
		Assert.assertTrue(loader instanceof EffectService);
	}

	@Test
	public void getStoryTest() {
		Optional<Story> value = Optional.of(new Story(TEST_STORY_NAME, testBytes));
		Mockito.when(storyRepository.findById(TEST_STORY_NAME)).thenReturn(value);
		EffectLoader loader = new EffectLoader(storyRepository);
		Assert.assertArrayEquals(testBytes, loader.getStory(TEST_STORY_NAME));
	}
	
	@Test
	public void getStoryFailTest() {
		Optional<Story> value = Optional.empty();
		Mockito.when(storyRepository.findById(TEST_STORY_NAME)).thenReturn(value);
		EffectLoader loader = new EffectLoader(storyRepository);
		byte[] bytes = loader.getStory(TEST_STORY_NAME);
		Assert.assertNotNull(bytes);
		Assert.assertEquals(0, bytes.length);
	}
	
	@Test
	public void getStoryFailNameTest() {
		EffectLoader loader = new EffectLoader(storyRepository);
		byte[] bytes = loader.getStory("");
		Assert.assertNotNull(bytes);
		Assert.assertEquals(0, bytes.length);
	}
	
	@Test
	public void getStoryNullNameTest() {
		EffectLoader loader = new EffectLoader(storyRepository);
		byte[] bytes = loader.getStory(null);
		Assert.assertNotNull(bytes);
		Assert.assertEquals(0, bytes.length);
	}
	
}
