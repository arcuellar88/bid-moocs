package moocs.mongo.entity;

import org.bson.Document;

public class Courseware extends MoocsEntity{


	public Courseware()
	{
		super();
	}
	@Override
	public void loadEntity(Document doc) {
		
		addColumn(""+doc.getInteger("student_id"));
	}

}
