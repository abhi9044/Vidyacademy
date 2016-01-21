package com.holamed.meddapp;

//created by prabhat

import android.content.Intent;

public class TestsTableSqlite {
	
	//private variables
	int _id;
	String _name;
	String _key;
    String _type;
    Integer _fre;
String _aliases;
    // Empty constructor
	public TestsTableSqlite(){
		
	}
	// constructor
	public TestsTableSqlite(int id, String name, String _key,String _type,Integer _fre,String _aliases){
		this._id = id;
		this._name = name;
		this._key = _key;
        this._type = _type;
        this._fre=_fre;
        this._aliases=_aliases;

	}
	
	// constructor
/*	public TestsTableSqlite(String name, String _key){
		this._name = name;
		this._key = _key;
	}*/
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	
	// getting name
	public String getName(){
		return this._name;
	}
	
	// setting name
	public void setName(String name){
		this._name = name;
	}
	
	// getting key
	public String getKey(){
		return this._key;
	}
	
	// setting key
	public void setKey(String key){
		this._key = key;
	}

    // getting type
    public String getType(){
        return this._type;
    }

    // setting type
    public void setType(String type){
        this._type = type;
    }
    // getting fre
    public Integer getfre(){
        return this._fre;
    }

    // setting fre
    public void setfre(Integer fre){
        this._fre =fre;
    }
    // getting aliases
    public String getaliases(){
        return this._aliases;
    }

    // setting aliases
    public void setaliases(String aliases){
        this._aliases = aliases;
    }



}
