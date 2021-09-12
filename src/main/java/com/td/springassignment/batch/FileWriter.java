package com.td.springassignment.batch;

import com.td.springassignment.model.Record;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FileWriter implements ItemWriter<List<Record>> {

	private static final String TEMP_DIRECTORY = System.getProperty("user.dir");

	
    private Map<String,Resource> outputResources ;
    private Map<String,FlatFileItemWriter> outputWriters;
	


    public FileWriter()
    {
    	outputResources = new HashMap<String,Resource>();
    	outputWriters = new HashMap<String,FlatFileItemWriter>();
    	deleteDirectory();
    }
    
    public static void deleteDirectory()
    {
        String filepath = TEMP_DIRECTORY+File.separator+"Output";
        File file = new File(filepath);

    	try {
        for (File subfile : file.listFiles()) {
        	//System.out.println(subfile.getAbsolutePath());
            subfile.delete();
        }
        }
    	catch(Exception ex)
    	{
    		
    	}
    }

    
    private Resource createResource(String currency)
    {
    	Resource outputResource = new FileSystemResource(TEMP_DIRECTORY+File.separator+"Output"+File.separator+"output_"+currency+".csv");
    	System.out.println(TEMP_DIRECTORY+File.separator+"output_"+currency+".csv");
    	outputResources.put(currency,outputResource);
    	return outputResource;
    }
    
    
    private void createWriter(String currency, Resource resource)
    {
    	FlatFileItemWriter<Record> writer = new FlatFileItemWriter<>();
    	
        writer.setAppendAllowed(true);
        
        BeanWrapperFieldExtractor<Record> fieldExtractor =
        	      new BeanWrapperFieldExtractor<>();
        	  fieldExtractor.setNames(new String[] {"tradeId", "termName","tradeValue","currency"});
        	  fieldExtractor.afterPropertiesSet();
        	 
        	  DelimitedLineAggregator<Record> lineAggregator =
        		      new DelimitedLineAggregator<>();
        		  lineAggregator.setDelimiter(",");
        		  lineAggregator.setFieldExtractor(fieldExtractor);
        		  
        writer.setLineAggregator(lineAggregator);
        writer.setResource(resource);
        
        outputWriters.put(currency, writer);

    }
    
    
    private FlatFileItemWriter<Record> getWriter(String currency)
    {
    	return outputWriters.get(currency);
    }
    
    
	@Override
	public void write(List<? extends List<Record>> items) throws Exception {
		
		for(List<Record> records : items)
		{
			
			for(Record this_record : records)
			{
				Resource this_resource = null;
				if(!outputResources.containsKey(this_record.getCurrency()))
				{
					Resource outputResource = createResource(this_record.getCurrency());
					createWriter(this_record.getCurrency(), outputResource);
				}
				
				this_resource = outputResources.get(this_record.getCurrency());
				FlatFileItemWriter<Record> writer = getWriter(this_record.getCurrency());
				List<Record> thisList = new ArrayList<Record>();
				thisList.add(this_record);
				writer.open(new ExecutionContext());
				writer.write(thisList);
				//writer.close();

			}
						
		}
	}
}
