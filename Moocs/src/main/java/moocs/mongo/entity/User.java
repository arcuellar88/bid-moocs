package moocs.mongo.entity;

import org.bson.Document;

public class User extends MoocsEntity{

	

	
	public User()
	{
		super();
	}
	
	@Override
	public void loadEntity(Document doc) {
		
		addColumn(getColumn(doc,"username"));
		addColumn(getColumn(doc,"first_name"));
		addColumn(getColumn(doc,"last_name"));
		addColumn(getColumn(doc,"email"));
		//addColumn(""+doc.getString("password"));
		addColumn("");//Hide password
		addColumn(""+doc.getInteger("is_staff"));
		addColumn(""+doc.getInteger("is_active"));
		addColumn(""+doc.getInteger("is_superuser"));
		addColumn(getColumn(doc,"last_login"));
		addColumn(getColumn(doc,"date_joined"));
		addColumn(getColumn(doc,"status"));
		addColumn(getColumn(doc,"email_key"));
		addColumn(getColumn(doc,"avatar_type"));
		addColumn(getColumn(doc,"country"));
		addColumn(""+doc.getInteger("show_country"));
		addColumn(getColumn(doc,"date_of_birth"));
		addColumn(getColumn(doc,"interesting_tags"));
		addColumn(""+doc.getInteger("email_tag_filter_strategy"));	
		addColumn(""+doc.getInteger("display_tag_filter_strategy"));	
		addColumn(""+doc.getInteger("consecutive_days_visit_count"));	
	}

}
