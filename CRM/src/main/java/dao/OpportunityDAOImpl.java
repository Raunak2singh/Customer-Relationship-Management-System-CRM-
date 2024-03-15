package dao;

import entities.Customer;
import entities.Opportunity;
import entities.Product;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class OpportunityDAOImpl implements OpportunityDAO {
    private final SessionFactory sessionFactory;

    public OpportunityDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Opportunity opportunity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(opportunity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void update(Opportunity opportunity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(opportunity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Opportunity opportunity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(opportunity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Opportunity getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Opportunity.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Opportunity> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Opportunity", Opportunity.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Opportunity> getByCustomer(Customer customer) {
        try (Session session = sessionFactory.openSession()) {
            Query<Opportunity> query = session.createQuery("FROM Opportunity WHERE customer = :customer", Opportunity.class);
            query.setParameter("customer", customer);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
     @Override
    public List<Opportunity> getByProduct(Product product) {
        try (Session session = sessionFactory.openSession()) {
            Query<Opportunity> query = session.createQuery("SELECT o FROM Opportunity o JOIN o.products p WHERE p = :product", Opportunity.class);
            query.setParameter("product", product);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addOpportunityForProduct(Opportunity opportunity, Product product) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Opportunity managedOpportunity = session.get(Opportunity.class, opportunity.getId());
            managedOpportunity.getProducts().add(product);
            
            session.persist(managedOpportunity);
            
            transaction.commit();
            System.out.println("Product added to the opportunity successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.out.println("Failed to add product to the opportunity.");
        }
    }
}
