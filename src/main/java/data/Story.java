package data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This class is intended to save story about location in a database.
 * <br>Field bytes contains a sound track recorded in an arbitrary format.
 * @author Aleksey Shishkin
 *
 */
@Entity
@Table(schema = "ThreeDMap", name = "story")
public class Story extends AbstractObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -249617199667225345L;
	
	public Story() {
		
	}
	
	public Story(String name, byte[] bytes) {
		super();
		this.name = name;
		this.bytes = bytes;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Story))
			return false;
		Story other = (Story) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
