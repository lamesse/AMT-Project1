/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Embeddable;

/**
 *
 * @author bischof
 */
@Embeddable
public class FactKey implements Serializable {
    
    
    private String factType;
    private String sensType;
    private Date date;
    
    public FactKey(){
        factType = "";
        sensType = "";
        date =  new Date(System.currentTimeMillis());
    }
    
    public FactKey(String factType, String sensType, Date date) {
        this.factType = factType;
        this.sensType = sensType;
        this.date = date;
    }

    public String getFactType() {
        return factType;
    }

    public String getSensType() {
        return sensType;
    }

    public Date getDate() {
        return date;
    }
    
    public void setFactType(String factType) {
        this.factType = factType;
    }

    public void setSensType(String sensType) {
        this.sensType = sensType;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
}
