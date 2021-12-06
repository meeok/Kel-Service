package com.kelmorgan.customService;

import com.kelmorgan.controller.Controller;
import com.kelmorgan.utils.LoadProp;
import org.apache.log4j.Logger;

public class CompleteWorkItem {

        private final String wiName;
        private String attribute;
        private String value;
        private final String sessionId;
        private String condition;
        private String tableName;
    private final Controller controller;



        public CompleteWorkItem(String sessionId, String wiName, Logger logger, LoadProp loadProp) {
            this.sessionId = sessionId;
            this.wiName = wiName;
            this.controller = new Controller(logger,loadProp);
        }

        public CompleteWorkItem(String sessionId, String wiName, String tableName, String attribute, String value, String condition, Logger logger, LoadProp loadProp){
            this.wiName = wiName;
            this.attribute = attribute;
            this.value = value;
            this.sessionId = sessionId;
            this.tableName = tableName;
            this.condition = condition;
            this.controller = new Controller(logger,loadProp);
        }

        public   String completeWorkItem(){
            controller.unlockWorkItem(sessionId,wiName);
            controller.lockWorkItem(sessionId,wiName);
            return  controller.completeWorkItem(sessionId,wiName);
        }
        public   String completeWorkItemWithAttribute(){
            controller.unlockWorkItem(sessionId,wiName);
            controller.lockWorkItem(sessionId,wiName);
            controller.updateRecords(sessionId, tableName,attribute,value,condition);
            return controller.completeWorkItem(sessionId,wiName);
        }

}