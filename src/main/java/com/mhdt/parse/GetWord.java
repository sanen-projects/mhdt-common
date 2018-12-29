package com.mhdt.parse;

import java.util.ArrayList;
import java.util.List;
/**
 * One to many kewords.
 * @author 懒得出风头 <br>
 * Date: Date： 2016/06/06
 */
public class GetWord{
	private String name;
	private List<String> params;
	
	public GetWord(){
		params = new ArrayList<String>();
	}
	
	public synchronized boolean addParam(String s){
		return params.add(s);
	}
	
	public synchronized boolean remove(String name){
		return params.remove(name);
	}
	
	public synchronized void remove(int index){
		 params.remove(index);
	}
	
	public synchronized void setParam(int index,String value){
		 params.set(index, value);
	}
	
	public  String getParam(int index){
		return params.get(index);
	}
	
	public List<String> getParams(){
		return params;
	}
	
	public synchronized void clear(){
		if(params!=null && !params.isEmpty())params.clear();
	}
	
	public  int getInt(int index){
		return Integer.parseInt(params.get(index));
	}
	
	public double getDouble(int index){
		return Double.parseDouble(params.get(index));
	}
	
	public boolean getBoolean(int index){
		return Boolean.parseBoolean(params.get(index));
	}
	
	public String getFirst(){
		if(!params.isEmpty())return params.get(0);
		return null;
	}
	
	public String getLast(){
		if(!params.isEmpty())return params.get(params.size()-1);
		return null;
	}
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "GetWord [name=" + name + ", params=" + params + "]\r\n";
	}
	
	
	
	
}