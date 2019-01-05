package net.abc.explore.entity.service;

import net.abc.explore.entity.Repositorie;
import net.abc.explore.entity.dao.RepositorieDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

/**
 * @author: zhangwei
 * @date: 11:39/2019-01-05
 */
@Component
public class RepositorieDaoService {

    @Autowired
    private RepositorieDao repositorieDao;

    public int add(Repositorie repositorie){
        try {
            return repositorieDao.add(repositorie);
        }catch (Throwable ex){
            /**
             * repositorie 在其他线程中已经新增,此时重新查询即可
             *
             * 主要由于 trending 与 repositorie 一对多的关系导致
             */
            if(ex instanceof DuplicateKeyException){
                if(ex.getMessage().contains("Duplicate entry")){
                    Repositorie exits = getRepositorie(repositorie.getOwner(), repositorie.getName());
                    repositorie.setId(exits.getId());
                    return 1;
                }
            }
            throw ex;
        }
    }

    public int update(Repositorie repositorie){
        return repositorieDao.update(repositorie);
    }

    public Repositorie getRepositorie(String onwer, String name){
        return repositorieDao.getRepositorie(null, onwer, name);
    }

    public Repositorie getRepositorie(Integer id){
        return repositorieDao.getRepositorie(id, null, null);
    }
}
