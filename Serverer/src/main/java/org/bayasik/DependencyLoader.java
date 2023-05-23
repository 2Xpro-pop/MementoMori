package org.bayasik;

import javax.persistence.*;

public class DependencyLoader{

    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("bayasik");

    public static EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
}