package moocs.mongo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Properties;

import moocs.mongo.entity.Certificate;
import moocs.mongo.entity.Courseware;
import moocs.mongo.entity.Demographic;
import moocs.mongo.entity.Enrollment;
import moocs.mongo.entity.MetaData;
import moocs.mongo.entity.MoocsEntity;
import moocs.mongo.entity.User;
import moocs.mongo.entity.UserIDs;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.ListDatabasesIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

/**
 * Moocs
 *
 */
public class Moocs 
{
	public final static String MDB_CERTIFICATES="certificates";
	public final static String MDB_ENROLLMENTS="enrollments";
	public final static String MDB_DEMOGRAPHICS="demographics";
	public final static String MDB_USERS="users";
	public final static String MDB_USERIDS="userIDs";
	public final static String MDB_COURSEWARE="courseware";
	
	private MongoClient mongo;
	private HashMap<String,String> cmoocs;
	private MongoDatabase db;
	private Properties prop;
	
	public Moocs(String config)
	{
		//Read the connection properties
		initialize(config);
    	
		if(prop.getProperty("username")!=null && !prop.getProperty("username").equals(""))
		{ 
			//Connect to mongodb without credentials
			mongo = new MongoClient( prop.getProperty("mongo_ip") , Integer.parseInt(prop.getProperty("mongo_port") ));
		}
		else
		{
			MongoCredential credential = MongoCredential.createCredential(prop.getProperty("username"), prop.getProperty("database"),prop.getProperty("password").toCharArray());
			//Connect to mongodb
			mongo = new MongoClient(Arrays.asList(new ServerAddress(prop.getProperty("mongo_ip"), Integer.parseInt(prop.getProperty("mongo_port")))),Arrays.asList(credential));
		
		}
	    	
		db = mongo.getDatabase("edx");
    
    	cmoocs= new HashMap<String, String>();
    	
    	
    	//Read the collections in MongoDB
    	MongoIterable<String> l =db.listCollectionNames();
    	 for(String c:l)
    	 {
    		String name=c.split("\\..")[0];
            cmoocs.put(name,name);
    	 }
    	 
	}
	
	public PrintWriter createPW(String fname,boolean append) throws IOException
	{

	     OutputStream os = new FileOutputStream(fname,append);
         
	    PrintWriter out = new PrintWriter(new OutputStreamWriter(
	    		os, StandardCharsets.UTF_8), true);
	    
	    return out;
	}
	/**
	 * Export data from MongoDB:
	 * Certificates
	 * Enrollments
	 * Demographics
	 * Users
	 * UserIDs
	 * Courseware (only distinct student_id)
	 */
	public void exportData()
	{

   	 try
   	 {
   		Collection<String> collectionMoocs= cmoocs.values();
   		MoocsEntity mcer= new Certificate();
   		MoocsEntity menr= new Enrollment();
   		MoocsEntity mdem= new Demographic();
   		MoocsEntity muse= new User();
   		MoocsEntity muid= new UserIDs();
   		MoocsEntity mcou= new Courseware();


   		for (String mongocourse_id : collectionMoocs) {
   			if(mongocourse_id.startsWith("idb"))
   			{
   				System.out.println("Reading: "+mongocourse_id);
   	       		export(mongocourse_id,mcer,MDB_CERTIFICATES);
   				export(mongocourse_id,menr,MDB_ENROLLMENTS);
   				export(mongocourse_id,mdem,MDB_DEMOGRAPHICS);
   				exportDistinct(mongocourse_id,mcou,MDB_COURSEWARE,"student_id");
   				//export(mongocourse_id,muse,MDB_USERS);
   				//export(mongocourse_id,muid,MDB_USERIDS);    		
   				}
   		}
   		
   		MoocsEntity meta= new MetaData();
   		exportMetadata(meta);

   		
   	 }
   	 catch (Exception e)
   	 {
   		 System.out.println(e.getMessage());
   		 e.printStackTrace();
   	 }
	}
	
	private void initialize(String fileName)
	{
		 prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(fileName);

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			System.out.println(prop.getProperty("database"));
			System.out.println(prop.getProperty("dbuser"));
			System.out.println(prop.getProperty("dbpassword"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void export(String m_course_id, MoocsEntity m,String table) throws IOException
	{
		String table_id=m_course_id+"."+table;
		//Print collection_id
		System.out.println(table_id);
		
		FindIterable<Document> elements =db.getCollection(table_id).find();
		
		PrintWriter pw = createPW(table+".csv", m.getRowNum()==1?false:true);
				
		
		for (Document doc : elements) {
		m.loadEntity(doc);
		pw.println(m.getLine()+MoocsEntity.SEP+m.formatString(m_course_id));
		}
		
		pw.close();
		
	}
	public void exportDistinct(String m_course_id, MoocsEntity m,String table, String field) throws IOException
	{
		String table_id=m_course_id+"."+table;
		System.out.println(table_id);
		
		DistinctIterable<Integer> elements =db.getCollection(table_id).distinct(field,Integer.class);
		
		PrintWriter pw = createPW(table+".csv", m.getRowNum()==1?false:true);
				
		
		for (Integer id : elements) 
		{
			pw.println(m.formatString(""+id)+MoocsEntity.SEP+m.formatString(m_course_id));
		}
		
		m.setRowNul(2);
		pw.close();
		
	}
	
	public void exportMetadata(MoocsEntity m) throws IOException
	{
		
		String table_id="reports";
		System.out.println(table_id);
		
		FindIterable<Document> elements =db.getCollection(table_id).find();
		
		PrintWriter pw = createPW(table_id+".csv", false);
				
		
		for (Document doc : elements) {
		m.loadEntity(doc);
		pw.println(m.getLine());
		}
		
		pw.close();
		
	}
	
    public static void main( String[] args )
    {
    	String config="mongo.properties";
    	if(args.length>0)
    		{
    		config=args[0];
    		}
    	
    	Moocs m=new Moocs(config);
    	m.exportData();
    	
    }
    
    public void printDB()
    {
    	ListDatabasesIterable<Document> dbs = mongo.listDatabases();
    	for(Document db : dbs){
    		System.out.println(db);
    	}
    }
    
    
}
