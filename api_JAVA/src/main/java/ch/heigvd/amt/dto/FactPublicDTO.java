/*
 *****************************************************************************************
 * HEIG-VD // heig-vd.ch
 * Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud
 * School of Business and Engineering in Canton de Vaud
 *****************************************************************************************
 *
 * File                 : FactPublicDTO.java
 * Author               : Jonathan Bischof
 *                        Antoine Messerli
 * Email                : jonathan.bischof@heig-vd.ch
 *                        antoine.messerli@heig-vd.ch
 * Date                 : 20 nov. 2014
 * Project              : Project 1 AMT
 *
 *****************************************************************************************
 * Modifications :
 * Ver      Date          Engineer                                   Comments
 * 1.0      20.11.2014    Jonathan Bischof, Antoine Messerli         Fact Public DTO
 *****************************************************************************************
 */
package ch.heigvd.amt.dto;

import ch.heigvd.amt.model.FactKey;

public class FactPublicDTO {

    private FactKey key;
    private String factType;
    private String sensType;
    private double average;

    public FactKey getKey() {
        return key;
    }

    public void setKey(FactKey key) {
        this.key = key;
    }

    public String getFactType() {
        return factType;
    }

    public void setFactType(String factType) {
        this.factType = factType;
    }

    public String getSensType() {
        return sensType;
    }

    public void setSensType(String sensType) {
        this.sensType = sensType;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }
}
