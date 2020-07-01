package com.aliTao.service;

import android.text.TextUtils;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 在发起网络请求时，用来存放请求参数的容器  
 * @author
 */
public class RequestParameters {

	private ArrayList<String> mKeys = new ArrayList<String>();
	private ArrayList<String> mValues=new ArrayList<String>();


	private ArrayList<String> mFileKeys = new ArrayList<String>();
	private ArrayList<File> mFileValues=new ArrayList<File>();


	public RequestParameters(){
		
	}
	
	/**
	 * 由于原有 add方法不支持value为空字符串的情况    所以  现将add方法修改如下
	 * 
	 * 去掉  TextUtils.isEmpty(value)  对value字段空字符串的验证
	 * 
	 * @param key
	 * @param value
	 */
	public void add(String key, String value){
	    if(!TextUtils.isEmpty(key)){
	        this.mKeys.add(key);
	        mValues.add(value);
	    }
	}

	public void add(String key, boolean value){
		this.mKeys.add(key);
		this.mValues.add(String.valueOf(value));
	}
	public void add(String key, int value){
	    this.mKeys.add(key);
        this.mValues.add(String.valueOf(value));
	}
	public void add(String key, long value){
	    this.mKeys.add(key);
        this.mValues.add(String.valueOf(value));
    }
	public void add(String key, double value){
		this.mKeys.add(key);
		this.mValues.add(String.valueOf(value));
	}

	public void add(String key, float value){
		this.mKeys.add(key);
		this.mValues.add(String.valueOf(value));
	}


	/**
	 * 添加文件参数
	 * @param key
	 * @param value
	 */
	public void addFile(String key, String value){
		if(!TextUtils.isEmpty(key)&&!TextUtils.isEmpty(value)){
			this.mFileKeys.add(key);
			File f = new File(value);
			mFileValues.add(f);
		}
	}


	public void remove(String key){
	    int firstIndex=mKeys.indexOf(key);
	    if(firstIndex>=0){
	        this.mKeys.remove(firstIndex);
	        this.mValues.remove(firstIndex);
	    }
	}
	
	public void remove(int i){
	    if(i<mKeys.size()){
	        mKeys.remove(i);
	        this.mValues.remove(i);
	    }
	}
	
	
	private int getLocation(String key){
		if(this.mKeys.contains(key)){
			return this.mKeys.indexOf(key);
		}
		return -1;
	}



	private int getFileLocation(String key){
		if(this.mKeys.contains(key)){
			return this.mFileKeys.indexOf(key);
		}
		return -1;
	}

	public String getKey(int location){
		if(location >= 0 && location < this.mKeys.size()){
			return this.mKeys.get(location);
		}
		return "";
	}


	public String getFileKey(int location){
		if(location >= 0 && location < this.mFileKeys.size()){
			return this.mFileKeys.get(location);
		}
		return "";
	}
	
	
	public String getValue(String key){
	    int index=getLocation(key);
	    if(index>=0 && index < this.mKeys.size()){
	        return  this.mValues.get(index);
	    }
	    else{
	        return null;
	    }
		
		
	}
	
	public String getValue(int location){
	    if(location>=0 && location < this.mKeys.size()){
	        String rlt = this.mValues.get(location);
	        return rlt;
	    }
	    else{
	        return null;
	    }
	}


	public File getFileValue(String key){
		int index = getFileLocation(key);
		if(index>=0 && index < this.mFileKeys.size()){
			return  this.mFileValues.get(index);
		}
		else{
			return null;
		}


	}

	public File getFileValue(int location){
		if(location>=0 && location < this.mFileKeys.size()){
			File rlt = this.mFileValues.get(location);
			return rlt;
		}
		else{
			return null;
		}
	}
	
	
	public int size(){
		return mKeys.size();
	}
	
	public void addAll(RequestParameters parameters){
		for(int i = 0; i < parameters.size(); i++){
			this.add(parameters.getKey(i), parameters.getValue(i));
		}
		
	}


	public Map<String, String> getMapParameters(){
		Map<String, String> maps = new HashMap<>();
		for (int loc = 0; loc < mKeys.size(); loc++) {
			maps.put(getKey(loc), getValue(loc));
		}
		return maps;
	}



	public Map<String, File> getMapFileParameters(){
		Map<String, File> maps = new HashMap<>();
		for (int loc = 0; loc < mFileKeys.size(); loc++) {
			maps.put(getFileKey(loc), getFileValue(loc));
		}
		return maps;
	}




	public void clear(){
		this.mKeys.clear();
		this.mValues.clear();
	}


	/**
	 * 获取指定路径的文件列表
	 * @param photoPaths
	 * @return
	 */
	ArrayList<File> getFilesList(ArrayList<String> photoPaths){
		ArrayList<File> files = new ArrayList<>();
		for (int i=0;i<photoPaths.size();i++){
			File f = new File(photoPaths.get(i));
			files.add(f);
		}
		return files;
	}


	public ArrayList<File> getFilesList(String photoPath){
		ArrayList<String> photoPaths = new ArrayList<>();
		photoPaths.add(photoPath);
		return getFilesList(photoPaths);
	}

	public String getFileName(String path){
		return path.substring(path.lastIndexOf("/") + 1 , path.length());
	}

}
