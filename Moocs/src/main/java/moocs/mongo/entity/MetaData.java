package moocs.mongo.entity;

import org.bson.Document;

public class MetaData extends MoocsEntity{

	
	public MetaData()
	{
		super();
	}
	
	@Override
	public void loadEntity(Document doc) {
		
		//int userId=doc.get("user_id");
		Document d=(Document)doc.get("metadata");
		
		addColumn(""+d.getString("name"));
		addColumn(""+d.getString("sme"));
		addColumn(""+d.getString("start_date"));
		addColumn(""+d.getString("end_date"));
		addColumn(""+doc.getString("_id"));
		
		
//		metadata
//		name:Gestion de projets de développement
//		sme:KNL/SDI
//		start_date:9/6/2016
//		end_date:10/11/2016
//		
//		addColumn(""+userId);
//		addColumn(""+doc.getString("course_id"));
//		addColumn(""+doc.getString("created"));
//		addColumn(""+doc.getInteger("is_active"));
//		addColumn(""+doc.getString("mode"));	
		
	
	}

}
