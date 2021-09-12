package com.td.springassignment.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.td.springassignment.model.Record;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

@Component
public class Processor implements ItemProcessor<Record, List<Record>> {

    private static final Map<Integer, String> TERM_BUCKET =
            new TreeMap<>();

    public Processor() {
    	TERM_BUCKET.put(90,"3M");
    	TERM_BUCKET.put(180,"6M");
    	TERM_BUCKET.put(365,"1Y");
    	TERM_BUCKET.put(730,"2Y");
    	TERM_BUCKET.put(1825,"5Y");
    	TERM_BUCKET.put(3650,"10Y");
    }

    @Override
    public List<Record> process(Record record) throws Exception {    	
    	
    	int termdays = Integer.parseInt(record.getTerm());
    	List<Record> recordList = new ArrayList<Record>();
    	String termName = "";
    	
    		while(termdays>3650)
    		{
    			//create a new record
    			
    			int rTermDays = termdays-3650;
    		
    			//System.out.println(record.toString());
    			int tradeValue = Integer.parseInt(record.getTradeValue()); 
    			int newTradeValue = (int) Math.round((double)3650/termdays * tradeValue);
    			int rTradeValue =  tradeValue - newTradeValue;

    			termName = findTerm(3650);
    			termdays=termdays-3650;
    		
    			//create a new record
    			Record new_record = new Record(record.getTradeId(),3650+"",newTradeValue+"",record.getCurrency(),termName);
    			recordList.add(new_record);
    			
    			record.setTerm(rTermDays+"");
    			record.setTradeValue(rTradeValue+"");
    			
    		}
    		
    		termName = findTerm(termdays);
			Record new_record = new Record(record.getTradeId(),record.getTerm(),record.getTradeValue(),record.getCurrency(),termName);
			recordList.add(new_record);

    	return recordList;
    }
    
    private String findTerm(int termdays)
    {
    	String termName = "";
    	for (Entry<Integer, String> entry : TERM_BUCKET.entrySet()) {
    		
    		if(termdays<=entry.getKey())
    		{
    			termName =  entry.getValue();
    			break;
    		}
    	}
    	
    	return termName;
    	
    }
    
}
