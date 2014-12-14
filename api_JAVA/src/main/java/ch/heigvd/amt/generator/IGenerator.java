/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.generator;

import javax.ejb.Local;

/**
 *
 * @author bischof
 */
@Local
public interface IGenerator {
    public void generate();
}
