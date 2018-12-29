package com.mhdt.parse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mhdt.Print;
import com.mhdt.io.FileIO;
import com.mhdt.parse.GetWord;

/**
 * Parsing a one-to-many text File.
 * @author 懒得出风头 <br>
 * Date： 2016/06/06
 */
public class Forest {
	
	Map<String,GetWord> map;
	
	public Forest(InputStream in){
		String s = FileIO.getContent(in);
		init(s);
	}
	
	public Forest(File file){
		if(!file.exists()){
			Print.info(file.getAbsolutePath());
		}
		String s = FileIO.getContent(file);
		init(s);
	}
	
	public Forest(String path) throws FileNotFoundException{
		String s = FileIO.getContent(path);
		init(s);
	}
	
	private void init(String content){
		map = new HashMap<String,GetWord>();
		String[] rows = content.split("\r\n");
		
		for(String temp:rows){
			GetWord word = new GetWord();
			String[] params = temp.split("\\s");
			
			if(params.length>0){
				word.setName(params[0]);
			}else{
				word = null;
			}
			
			if(params.length>1){
				for(int i=1;i<params.length;i++){
					String param = params[i];
					word.addParam(param);
				}
			}
			
			if(word!=null)map.put(word.getName(),word);
			
		}
		
	}
	
	
	public List<GetWord> contains(String content){
		List<GetWord> result = new ArrayList<GetWord>();
		for(Map.Entry<String, GetWord> entry : map.entrySet()){
			if(content.indexOf(entry.getKey())!=-1){
				result.add(entry.getValue());
			}
		}
		return result;
		
	}
	
	public Set<String> getKeys(){
		return map.keySet();
	}
	
	public GetWord get(int index){
		return map.get(String.valueOf(index));
	}
	
	public GetWord get(String name){
		return map.get(name);
	}
	
	public Collection<GetWord> getAll(){
		return map.values();
	}
	
	public synchronized void add(GetWord word){
		 map.put(word.getName(), word);
	}
	
	public synchronized  void addAll(Collection<GetWord> list){
		for(GetWord word : list){
			add(word);
		}
	}
	
	public synchronized void remove(String key){
		map.remove(key);
	}
	
	public void save(File desc){
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, GetWord> entry : map.entrySet()){
			sb.append(entry.getKey()+" ");
			for(String param : entry.getValue().getParams()){
				sb.append(param+" ");
			}
			sb.append("\r\n");
		}
		FileIO.write(desc, sb.toString(), false);
	}
	
	
	
	
	
	@Override
	public String toString() {
		return "Forest [" + map.values() + "]";
	}


}
