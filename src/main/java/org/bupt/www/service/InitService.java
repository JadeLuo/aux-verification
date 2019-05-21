package org.bupt.www.service;

import org.bupt.www.repository.EntityMarkRepository;
import org.bupt.www.repository.RelationMarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

@Service
public class InitService {

    @Value("${init-for-test}")
    private boolean shouldInit;

    @Autowired
    private EntityMarkRepository entityMarkRepository;

    @Autowired
    private RelationMarkRepository relationMarkRepository;

    @Autowired
    private EntityManager entityManager;
    @Autowired
    @Qualifier("transactionManager")
    protected PlatformTransactionManager txManager;

    @PostConstruct
    @Transactional
    public void init() {
        System.out.println(shouldInit);
        if (shouldInit) {
            System.out.println("init database");
            TransactionTemplate tmpl = new TransactionTemplate(txManager);
            tmpl.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
//                    entityMarkRepository.initForTest(null, null, null, null);
//                    relationMarkRepository.initForTest(null, null, null, null);
                    Query entityQuery = entityManager.createNativeQuery("UPDATE relation_mark SET reviewed = b'0', ver_date = NULL, user_id = NULL " +
                            "WHERE passed = b'0'");
                    Query relationQuery = entityManager.createNativeQuery("UPDATE entity_mark SET reviewed = b'0', ver_date = NULL, user_id = NULL " +
                            "WHERE passed = b'0'");
                    entityQuery.executeUpdate();
                    relationQuery.executeUpdate();
                }
            });
        }
    }

}
