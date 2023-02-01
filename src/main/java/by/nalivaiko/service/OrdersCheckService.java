package by.nalivaiko.service;

import by.nalivaiko.entity.Order;
import by.nalivaiko.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrdersCheckService {
    private final OrdersRepository repository;

    public void saveOrder(Order order) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH/mm");
        Calendar cal = Calendar.getInstance();
        order.setDataOfOrder(dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, getDateOfEndOrder(order.getCategoryOfOrder()));
        String end = dateFormat.format(cal.getTime());
        order.setDataOfEndOrder(end);
        repository.saveOrder(order);
        log.info("Order{}", order);
    }

    public List<Order> getOrders() {
        return repository.getOrders();
    }

    public Order getOrderById(Integer id) {
        return repository.getOrderById(id);
    }

    public void deleteOrderById(Integer id) {
        repository.deleteOrderById(id);
    }

    private Integer getDateOfEndOrder(String categoryOfOrder) {
        int i;
        switch (categoryOfOrder) {
            case "Fishing And Hunt" -> i = 6;
            case "Toys" -> i = 3;
            case "Gadgets" -> i = 7;
            case "Home things" -> i = 9;
            case "Materials for building" -> i = 14;
            case "Cloth" -> i = 5;
            default -> i = 2;
        }
        return i;
    }
}
