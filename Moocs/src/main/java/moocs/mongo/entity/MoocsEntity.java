package moocs.mongo.entity;

import org.bson.Document;

public abstract class MoocsEntity {

	public final static String SEP=",";
	
	String line;
	protected int n;
	
	
	
	public MoocsEntity()
	{
		n=1;
		line="";
	}
	
	public int getRowNum()
	{
		return n;
	}
	
	protected String getColumn(Document doc,String id)
	{
		String answer="";
		try
		{
			answer=doc.getString(id);
		}
		catch(Exception e)
		{
			try
			{
				answer=""+doc.getInteger(id);

			}
			catch (Exception e1)
			{
				try{
					answer=""+doc.getLong(id);
				}
				catch(Exception e2)
				{
					answer=""+doc.getDouble(id);

				}
			
			}
		}
		return answer;
	}
	
	
	public void addColumn(String c)
	{
		String fc="";
		if(c != null)
		{
			if(c.length()>1)
			{
				fc=formatString(c);
			}
			else
			{
				fc=c;
			}
			
		}
		
		
		line+=SEP+fc;
	}
	
	public abstract void loadEntity(Document doc);
	
	public String formatString(String s)
	{
		return "\""+s.replaceAll("\"", "'")+"\"";
	}
	
	public String getLine()
	{
		String l=(n++)+line;
		line="";
		return l;
		
	}

	public void setRowNul(int i) {
		n=i;
	}
}
