package moocs.mongo.entity;

import org.bson.Document;

public class Demographic extends MoocsEntity{

//	id:1499455
//	user_id:2742945
//	language:
//	location:
//	courseware:course.xml
//	gender:m
//	year_of_birth:1957
//	level_of_education:p
//	goals:I am part of the IDBx team who will develop MOOCs in edX
//	allow_certificate:1
//	country:NULL
//	city:NULL

	public Demographic()
	{
		super();
	}
	@Override
	public void loadEntity(Document doc) {
		
		addColumn(""+doc.getInteger("user_id"));
		addColumn(""+doc.getString("language"));
		addColumn(""+doc.getString("location"));
		addColumn(""+doc.getString("courseware"));
		addColumn(""+doc.getString("gender"));
		
		addColumn(getColumn(doc,"year_of_birth"));
		
		addColumn(""+doc.getString("level_of_education"));
		
		addColumn(getColumn(doc,"goals"));
		
		addColumn(""+doc.getInteger("allow_certificate"));
		addColumn(""+doc.getString("country"));
		addColumn(""+doc.getString("city"));
		
	}

}
