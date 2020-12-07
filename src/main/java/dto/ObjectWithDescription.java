package dto;

/**
 * This class is a Story which contains description which should be 
 * represented on front-end
 * @author ivan
 *
 */
public class ObjectWithDescription extends ThreeDObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3922815287256152178L;
	/**
	 * 
	 */
	public ObjectWithDescription() {}
	public ObjectWithDescription(ThreeDObject object, String description) {
		super.setAlphaX(object.getAlphaX());
		super.setAlphaY(object.getAlphaY());
		super.setAlphaZ(object.getAlphaZ());
		super.setHight(object.getHight());
		super.setLatitude(object.getLatitude());
		super.setLongitude(object.getLongitude());
		super.setName(object.getName());
		super.setType(object.getType());
		this.setDescription(description);
	}
	
	private String description;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}