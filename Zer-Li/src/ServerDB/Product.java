package ServerDB;

import java.io.Serializable;

public class Product implements Serializable
{
	//variables
	private String id;
	private String Name;
	private String Type;

	//Constructor
	public Product(String id, String name, String Type) 
	{
		super();
		this.id = id;
		this.Name = name;
		this.Type = Type;
	}

	//getters and setters:
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		System.out.println("ID set to "+id);
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
		System.out.println("Last name set to "+name);
	}

	
	public String getType() {
		return Type;
	}

	
	public void setType(String type) {
		this.Type = type;
		System.out.println("Product type set to "+type);
	}
	
	//tostring method: (product details in string form)
	public String toString(){
		return String.format("\nID: %s\t %s\t %s\n",id,Name,Type);
	}
}
