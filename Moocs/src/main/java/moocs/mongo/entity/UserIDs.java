package moocs.mongo.entity;

import org.bson.Document;

public class UserIDs extends MoocsEntity{
	
	public UserIDs()
	{
		super();
	}
	
	@Override
	public void loadEntity(Document doc) {
		
		addColumn(getColumn(doc,"hash_id"));
		addColumn(""+doc.getInteger("id"));
		addColumn(getColumn(doc,"username"));
		
	}

}
