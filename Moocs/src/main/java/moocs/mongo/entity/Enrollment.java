package moocs.mongo.entity;

import org.bson.Document;

public class Enrollment extends MoocsEntity{

	
	public Enrollment()
	{
		super();
	}
	
	@Override
	public void loadEntity(Document doc) {
		
		int userId=doc.getInteger("user_id");
		
		addColumn(""+userId);
		addColumn(""+doc.getString("course_id"));
		addColumn(""+doc.getString("created"));
		addColumn(""+doc.getInteger("is_active"));
		addColumn(""+doc.getString("mode"));	
		
	
	}

}
