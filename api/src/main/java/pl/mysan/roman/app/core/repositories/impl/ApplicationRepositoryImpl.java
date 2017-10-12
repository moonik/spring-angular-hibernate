package pl.mysan.roman.app.core.repositories.impl;

import pl.mysan.roman.app.core.models.entities.Borrow;
import pl.mysan.roman.app.core.models.entities.Borrower;
import pl.mysan.roman.app.core.models.entities.Vehicle;
import pl.mysan.roman.app.core.repositories.ApplicationRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
public class ApplicationRepositoryImpl implements ApplicationRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Vehicle save(Vehicle vehicle) {
        em.persist(vehicle);
        return vehicle;
    }

    @Override
    public Vehicle getVehicle(Long id) {
        return em.find(Vehicle.class, id);
    }

    @Override
    public List<Vehicle> getAll() {
        Query query = em.createQuery("SELECT v FROM Vehicle v");
        return query.getResultList();
    }

    @Override
    public void delete(Long id) {
        em.remove(em.find(Vehicle.class, id));
    }

    @Override
    public Borrow borrow(Borrow borrow) {
        em.persist(borrow);
        return borrow;
    }

    @Override
    public Borrower getBorrower(Long id) {
        return em.find(Borrower.class, id);
    }

    @Override
    public Borrower save(Borrower borrower) {
        em.persist(borrower);
        return borrower;
    }

    @Override
    public Borrow getBorrowInfo(LocalDate date, Vehicle vehicle) throws ParseException {
        Query query = em.createQuery("SELECT b FROM Borrow b where b.borrowDate = ?1 AND b.vehicle = ?2");
        query.setParameter(1, date);
        query.setParameter(2, vehicle);
        return query.getResultList().size() > 0 ? (Borrow)query.getResultList().get(0) : null;
    }

    @Override
    public List<Borrower> getUsers() {
        Query query = em.createQuery("SELECT b FROM Borrower b");
        return query.getResultList();
    }

    @Override
    public void unborrow(Vehicle vehicle, LocalDate date) throws ParseException {
        Query query = em.createQuery("SELECT b FROM Borrow b where b.borrowDate = ?1 AND b.vehicle = ?2");
        query.setParameter(1, date);
        query.setParameter(2, vehicle);
        em.remove(query.getResultList().get(0));
    }

    @Override
    public Boolean ifExists(Long id) {
        return em.find(Vehicle.class, id) != null;
    }
}
