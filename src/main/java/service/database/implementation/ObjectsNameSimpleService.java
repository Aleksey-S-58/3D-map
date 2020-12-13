package service.database.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.NameDescription;
import repository.NameDescriptionRepository;
import service.ObjectNameService;

/**
 * The service provides object description by it's name
 * @author ivan
 *
 */
@Service
public class ObjectsNameSimpleService implements ObjectNameService{
	
	@Autowired
	private NameDescriptionRepository repository;
	@Override
	public String getObjectsDescriptionByName(String name) {
		
		NameDescription description = repository.findById(name).orElse(null);
		if (description != null) {
			return description.getDescription();
		}
		return null;
	}



}
