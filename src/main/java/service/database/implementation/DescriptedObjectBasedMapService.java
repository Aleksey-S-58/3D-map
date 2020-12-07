package service.database.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import data.EffectType;
import data.NameDescription;
import dto.ObjectWithDescription;
import dto.ThreeDObject;
import repository.DescriptionRepository;
import repository.LocationRepository;
import repository.NameDescriptionRepository;
import service.Localization;

/**
 * The service is intended to get object with description by it's coordinates.
 * @author ivan
 *
 */
@Service
public class DescriptedObjectBasedMapService extends ORMBasedMap {
	
	
	protected NameDescriptionRepository nameRepository;
    
	public DescriptedObjectBasedMapService(LocationRepository locationsRepository,
			DescriptionRepository descriptionsRepository, Localization localization,
			NameDescriptionRepository nameRepository) {
		super(locationsRepository, descriptionsRepository, localization);
		this.nameRepository = nameRepository;
	}
	
	@Transactional
	@Override
	public List<ThreeDObject> getObjects(double hight, double latitude, double longitude) {
		List<ThreeDObject> list = super.getObjects(hight, latitude, longitude);
		List<ThreeDObject> result = new ArrayList<>(list.size());
		List<String> names = getNames(list);
		Iterable<NameDescription> nameDescriptions = nameRepository.findAllById(names);
		
		for (ThreeDObject object : list) {
			if (EffectType.STORY.name().equals(object.getType())) {
				result.add(getObjectWithDescription(object, nameDescriptions));
			} else {
				result.add(object);
			}			
		}
		return result;
	}
	
	protected ObjectWithDescription getObjectWithDescription(ThreeDObject object, Iterable<NameDescription> nameDescriptions) {
		for(NameDescription description : nameDescriptions) {
			if (description.getName().equals(object.getName())) {
				return new ObjectWithDescription(object, description.getDescription());
			}
		}
		return new ObjectWithDescription(object, object.getName());
	}
	
	protected List<String> getNames (List<? extends ThreeDObject> list) {
		return list.stream().
				filter(object-> EffectType.STORY.name().equals(object.getType())).
				map(obj-> obj.getName()).
				collect(Collectors.toList());
	}

}
