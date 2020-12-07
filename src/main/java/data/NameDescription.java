package data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "ThreeDMap", name = "object_name_description")
public class NameDescription implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7084981024235754958L;

	@Id
	@Column(name = "name")
	protected String name;
	
	@Column(name = "description")
	protected String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
