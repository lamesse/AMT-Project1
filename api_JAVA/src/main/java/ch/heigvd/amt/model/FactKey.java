/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.model;

import java.sql.Date;

/**
 *
 * @author bischof
 */
public class FactKey {
    
    
    private final String factType;
    private final String sensType;
    private final Date date;
    
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
}
