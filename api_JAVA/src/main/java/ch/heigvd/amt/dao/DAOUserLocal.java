/*
 *****************************************************************************************
 * HEIG-VD // heig-vd.ch
 * Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud
 * School of Business and Engineering in Canton de Vaud
 *****************************************************************************************
 *
 * File                 : DAOUserLocal.java
 * Author               : Jonathan Bischof
 *                        Antoine Messerli
 * Email                : jonathan.bischof@heig-vd.ch
 *                        antoine.messerli@heig-vd.ch
 * Date                 : 04 dec. 2014
 * Project              : Project 1 AMT
 *
 *****************************************************************************************
 * Modifications :
 * Ver      Date          Engineer                                   Comments
 * 1.0      04.12.2014    Jonathan Bischof, Antoine Messerli         Interface user DAO
 *****************************************************************************************
 */
package ch.heigvd.amt.dao;

import ch.heigvd.amt.model.User;
import java.util.List;
import javax.ejb.Local;

@Local
public interface DAOUserLocal {

    public abstract boolean create(User t);

    public abstract boolean update(User t);

    public abstract boolean delete(User t);

    public abstract User find(Long id);
    
    public abstract List<User> findByOrg(Long orgId);
    
    public abstract List<User> findByOrg(Long orgId, Long userId);
}
