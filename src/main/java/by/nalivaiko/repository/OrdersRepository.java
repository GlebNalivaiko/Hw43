package by.nalivaiko.repository;

import by.nalivaiko.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
@Slf4j
@RequiredArgsConstructor
public class OrdersRepository {
    private final SessionFactory factory;

    public List<Order> getOrders() {
        List<Order> orders;
        try (Session session = factory.openSession()) {
            orders = session
                    .createQuery("from Order ", Order.class)
                    .getResultList();
        }
        return orders.isEmpty() ? null : orders;
    }

    public void saveOrder(Order order) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            if (order.getId() == null) {
                session.persist(order);
            } else {
                session.merge(order);
            }
            transaction.commit();
        }
    }

    public Order getOrderById(Integer id) {
        log.error("id {}", id);
        Order order;
        try (Session session = factory.openSession()) {
            order = session.find(Order.class, id);
        }
        return order;
    }

    public void deleteOrderById(Integer id) {
        log.error("delete id {}", id);
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(getOrderById(id));
            transaction.commit();
        }
    }
}




