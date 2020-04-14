package service;

/**
 * This interface defines methods to load "effects" from a database.
 * @author Aleksey Shishkin
 *
 */
public interface EffectService {

	/**
	 * Load story.
	 * @param name - sound track name.
	 * @return sound track file as an array of byte.
	 */
	public byte[] getStory(String name);

}
