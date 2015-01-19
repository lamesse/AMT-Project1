/*
 *****************************************************************************************
 * HEIG-VD // heig-vd.ch
 * Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud
 * School of Business and Engineering in Canton de Vaud
 *****************************************************************************************
 *
 * File                 : FactDTO.java
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
 * 1.0      20.11.2014    Jonathan Bischof, Antoine Messerli         Fact DTO
 *****************************************************************************************
 */
package ch.heigvd.amt.dto;

import ch.heigvd.amt.model.FactKey;

public class FactDTO {

    private FactKey key;
    private String factType;
    private String sensType;
    private double minimum;
    private double average;
    private double maximum;
    private boolean isPublic;

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

    public double getMinimum() {
        return minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getMaximum() {
        return maximum;
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }

    public boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
