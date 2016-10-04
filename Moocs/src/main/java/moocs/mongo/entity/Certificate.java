package moocs.mongo.entity;

import org.bson.Document;

public class Certificate extends MoocsEntity{

//	id:14216880
//	user_id:5431
//	grade:0
//	course_id:course-v1:IDBx+IDB10x+2015_T4
//	key:
//	distinction:0
//	status:notpassing
//	verify_uuid:
//	download_uuid:
//	created_date:2015-12-29 16:49:14
//	modified_date:2015-12-29 16:49:14
//	error_reason:
//	mode:honor
	
	public Certificate()
	{
		super();
	}
	@Override
	public void loadEntity(Document doc) {
		
		addColumn(""+doc.getInteger("user_id"));
		try
		{
			addColumn(""+doc.getDouble("grade"));
		}
		catch(Exception e)
		{
			addColumn(""+doc.getString("grade"));
		}
		addColumn(""+doc.getString("course_id"));
		addColumn(""+doc.getString("key"));
		addColumn(""+doc.getInteger("distinction"));
		addColumn(""+doc.getString("status"));
		addColumn(""+doc.getString("verify_uuid"));
		addColumn(""+doc.getString("download_uuid"));
		addColumn(""+doc.getString("created_date"));
		addColumn(""+doc.getString("modified_date"));
		addColumn(""+doc.getString("error_reason"));
		addColumn(""+doc.getString("mode"));		
	}

}
