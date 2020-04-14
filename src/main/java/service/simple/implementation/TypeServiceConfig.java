package service.simple.implementation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import data.EffectType;
import data.ObjectType;
import service.TypeService;

/**
 * This configuration bean adjusts a custom configuration of a {@link TypeService}
 * @author Aleksey Shishkin
 *
 */
@Configuration
public class TypeServiceConfig {

	/**
	 * @return SimpleTypeService with {@link ObjectType} and {@link EffectType} values as argument.
	 */
	@Bean("customTypeService")
	public TypeService buildSimpleTypeService() {
		return new SimpleTypeService(ObjectType.values(), EffectType.values());
	}
}
