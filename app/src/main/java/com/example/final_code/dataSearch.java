package com.example.final_code;

public class dataSearch {
    private String id;
    private String name;
    private String destination;
    private String date;
    private String risks;
    private String description;

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id=id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getDestination(){
        return destination;
    }
    public void setDestination(String destination){
        this.destination=destination;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date=date;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public String getRisks(){
        return risks;
    }
    public void setRisks(String risks){
        this.risks=risks;
    }

    public dataSearch(String id, String name, String destination, String date, String risks, String description ){
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.date = date;
        this.risks = risks;
        this.description = description;
    }

    public dataSearch(String id, String name, String destination, String date, String risks){
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.date = date;
        this.risks = risks;
    }

    public dataSearch(){

    }



}
