package com.kelmorgan.service;

import com.kelmorgan.controller.Controller;
import com.kelmorgan.customService.CompleteWorkItem;
import com.kelmorgan.customService.CreateWorkItem;
import com.kelmorgan.initializer.ServiceHandler;
import com.kelmorgan.utils.Constants;
import com.kelmorgan.utils.LoadProp;
import com.kelmorgan.utils.LogGen;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Set;

public class Service implements Constants, ServiceHandler {
    private  String sessionId;
    private final LoadProp loadProp;
    private final Logger logger;
    private final Controller controller;



    public Service(String configPath)  {
        this.loadProp = new LoadProp(configPath);
        this.logger = new LogGen(loadProp).getLoggerInstance(logName);
        this.controller = new Controller(logger,loadProp);
        this.sessionId = getSessionId();
    }

    public Service(String configPath,String username, String password) {
        this.loadProp = new LoadProp(configPath);
        this.logger = new LogGen(loadProp).getLoggerInstance(logName);
        this.controller = new Controller(logger,loadProp);
        this.sessionId = getSessionId(username,password);
    }

    @Override
    public String completeWorkItem (String wiName){
       return new CompleteWorkItem(sessionId,wiName,controller).completeWorkItem();
    }
    @Override
    public String completeWorkItem (String wiName, String tableName, String attribute, String value, String condition){
       return new CompleteWorkItem(sessionId,wiName,tableName,attribute,value,condition, controller).completeWorkItemWithAttribute();
    }
    @Override
    public String createWorkItem(String attributes,String processDefId,String queueId, String initiateFlag){
        return new CreateWorkItem(sessionId,attributes,processDefId,queueId,initiateFlag,controller).getCreatedWorkItem();
    }
    @Override
    public void disconnectCabinet(){
        controller.disconnectSession(sessionId);
    }
    @Override
    public Set<Map<String,String>> getRecords (String query){
        return controller.getRecords(query);
    }
    @Override
    public String updateRecord (String tableName, String columnName,String values, String condition){
        int update = controller.updateRecords(sessionId,tableName,columnName,values,condition);
        return (update == 1) ? apiSuccess : apiFailed;
    }
    @Override
    public String insertRecords (String tableName, String column,String values){
        int insertStatus = controller.insertRecords(sessionId,tableName,column,values);
        return (insertStatus == 1) ? apiSuccess : apiFailed;
    }

    @Override
    public  String getSessionId(String userName,String passWord){
        return controller.getSessionId(userName,passWord);
    }
    @Override
    public String getSessionId(){
        return controller.getSessionId();
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}
