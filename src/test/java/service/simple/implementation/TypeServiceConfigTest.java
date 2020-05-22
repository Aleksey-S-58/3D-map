package service.simple.implementation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import data.EffectType;
import data.ObjectType;
import data.TypeMarker;
import service.TypeService;

/**
 * This tests check that whole ObjectTypes will be returned by a constructed TypeService.
 * @author Aleksey Shishkin
 *
 */
public class TypeServiceConfigTest {

	private TypeServiceConfig config = new TypeServiceConfig();
	
	/**
	 * This test checks that the service annotated with @Configuration
	 */
	@Test
	public void checkAnnotations() {
		Class<?> clazz = config.getClass();
		Annotation annotation = clazz.getAnnotation(Configuration.class);
		Assert.assertNotNull(annotation);
	}
	
	/**
	 * This method checks that this configuration bean creates a TypeService bean
	 * with name "customTypeService" 
	 */
	@Test
	public void checkDefinedBeans() {
		Class<?> clazz = config.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		boolean methodFound = false;
		for (Method method : methods) {
			Class<?> returnType = method.getReturnType();
			if (returnType == TypeService.class 
					&& method.getAnnotation(Bean.class) != null) {
				methodFound = true;
			}
		}
		Assert.assertTrue(methodFound);
	}
	
	/**
	 * The method checks that whole ObjectTypes will be returned by a constructed TypeService.
	 */
	@Test
	public void checkBuildedTypeService() {
		TypeService typeService = config.buildSimpleTypeService();
		Enum<? extends TypeMarker>[] types = typeService.getObjectTypes();
		for (ObjectType type : ObjectType.values()) {
			Assert.assertTrue(contains(types, type));
		}
		for (EffectType type : EffectType.values()) {
			Assert.assertTrue(contains(types, type));
		}
	}

	private boolean contains(Enum<? extends TypeMarker>[] types, Enum<? extends TypeMarker> markedType) {
		for (Enum<? extends TypeMarker> type : types) {
			if (type == markedType) {
				return true;
			}
		}
		return false;
	}
	
}
