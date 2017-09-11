package com.capone.ccapi;

import com.capone.ccapi.resources.*;
import com.capone.ccapi.core.*;
import com.capone.ccapi.db.*;
import com.capone.ccapi.util.*;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.hibernate.SessionFactory;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import com.washingtonpost.dropwizard.exceptions.mappers.JsonProcessingExceptionMapper;
import com.washingtonpost.dropwizard.exceptions.mappers.RuntimeExceptionMapper;

public class CreditCardApplication extends Application<CreditCardServiceConfiguration> {

    private final HibernateBundle<CreditCardServiceConfiguration> hibernateBundle =
        new HibernateBundle<CreditCardServiceConfiguration>(Account.class, Journal.class, Ledger.class) {
            @Override
            public DataSourceFactory getDataSourceFactory(CreditCardServiceConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        };

    private final MigrationsBundle<CreditCardServiceConfiguration> migrationBundle =
       new MigrationsBundle<CreditCardServiceConfiguration>() {
        @Override
        public DataSourceFactory getDataSourceFactory(CreditCardServiceConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    public static void main(final String[] args) throws Exception{
        new CreditCardApplication().run(args);
    }
 
    @Override
    public String getName() {
        return "CreditCardService";
    }

    @Override
    public void initialize(final Bootstrap<CreditCardServiceConfiguration> bootstrap) {

        bootstrap.setConfigurationSourceProvider(
            new SubstitutingSourceProvider(
                bootstrap.getConfigurationSourceProvider(),
                new EnvironmentVariableSubstitutor(false)
            ));

        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(migrationBundle);
    }

    @Override
    public void run(final CreditCardServiceConfiguration configuration,
                    final Environment environment) {

        /* Prepare all the DAOs*/
        SessionFactory session = hibernateBundle.getSessionFactory();
        final AccountDAO accountDao = new AccountDAO(session);
        final JournalDAO journalDao = new JournalDAO(session);
        final LedgerDAO ledgerDao = new LedgerDAO(session);

        /*Init all services*/
        LedgerService.createLedgerService(ledgerDao);
        JournalService.createJournalService(journalDao);

        environment.jersey().register(new HealthResource());
        environment.jersey().register(new AccountResource(accountDao));
        environment.jersey().register(new JournalResource(journalDao));
        environment.jersey().register(new JsonProcessingExceptionMapper());
        environment.jersey().register(new RuntimeExceptionMapper());
    }
}
