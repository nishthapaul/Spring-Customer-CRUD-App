package springdemo.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDao {

	// inject sessionFactory;
	@Autowired
	public SessionFactory sessionFactory; 
	
	@Override
	public List<Customer> getCustomers() {
		Session session = sessionFactory.getCurrentSession();
		List<Customer> customersList = session.createQuery("from Customer order by lastName", Customer.class).getResultList();
		return customersList;
	}

	@Override
	public void saveCustomer(Customer customer) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(customer);
	}

	@Override
	public Customer getCustomer(int id) {
		Session session = sessionFactory.getCurrentSession();
		Customer customer = session.get(Customer.class, id);
		return customer;
	}

	@Override
	public void deleteCustomer(int id) {
		Session session = sessionFactory.getCurrentSession();
		Customer customer = session.get(Customer.class, id);
		session.delete(customer);
	}

	@Override
	public List<Customer> searchCustomers(String searchName) {
		// case insensitive
		Session session = sessionFactory.getCurrentSession();
		Query theQuery = null;
		if(searchName!=null && searchName.trim().length()>0) {
			theQuery = session.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
			theQuery.setParameter("theName", "%" + searchName.toLowerCase() + "%");
		} else {
			theQuery = session.createQuery("from Customer", Customer.class);
		}
		List<Customer> customersList = theQuery.getResultList();
		return customersList;
	}

}
