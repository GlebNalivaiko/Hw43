package by.nalivaiko.controller;

import by.nalivaiko.entity.Order;
import by.nalivaiko.service.OrdersCheckService;
import by.nalivaiko.service.TimeOfOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.text.ParseException;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("view")
public class OrdersController {
    private final OrdersCheckService service;
    private final TimeOfOrderService timeService;

    @GetMapping
    public String getOrdersPage(Order order, Model model) {
        model.addAttribute("orders", service.getOrders());
        log.error("list{}", service.getOrders());
        model.addAttribute("order", order);
        return "ordersPage";
    }

    @GetMapping("change/{id}")
    public String changePage(@PathVariable Integer id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("order", service.getOrderById(id));
        return "change_order";
    }


    @PostMapping("set/{id}")
    public RedirectView setOrder(@PathVariable Integer id, Order order) {
        Order order1 = service.getOrderById(id);
        order1.setNameOfOrder(order.getNameOfOrder());
        order1.setCountry(order.getCountry());
        order1.setPrice(order.getPrice());
        log.error("Order{}", order);
        service.saveOrder(order1);
        return new RedirectView("/view");
    }

    @GetMapping("delete")
    public String deleteOrder(Order order, @RequestParam(value = "id") Integer id, Model model) {
        service.deleteOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("orders", service.getOrders());
        return "ordersPage";
    }

    @GetMapping("info")
    public String viewMyOrders(Integer id, Model model, Map<String, String> infoAboutOrder) throws ParseException {
        log.info("id {}", id);
        Map<String, String> info = timeService.getInfo(id);
        Order order = service.getOrderById(id);
        model.addAttribute("order", order);
        log.error("Order{}", order);
        infoAboutOrder.put("time", info.get("time"));
        infoAboutOrder.put("status", info.get("status"));
        String[] way = new String[15];
        way[Integer.parseInt(info.get("partOfWay"))] = "0";
        model.addAttribute("array", way);
        return "orderInfo";
    }


    @PostMapping
    public String getInfoOfOrder(@Valid Order order, Model model) {
        log.info("Order{}", order);
        model.addAttribute("order", order);
        service.saveOrder(order);
        model.addAttribute("orders", service.getOrders());
        return "ordersPage";
    }
}
