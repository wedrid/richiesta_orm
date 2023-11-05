package com.caribu.richiesta_orm.service;

import org.hibernate.reactive.stage.Stage.SessionFactory;

public class RichiestaService {
    private SessionFactory sessionFactory;
    public RichiestaService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
