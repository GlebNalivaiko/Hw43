package by.nalivaiko.service;

import by.nalivaiko.entity.Order;
import by.nalivaiko.model.Status;
import by.nalivaiko.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.springframework.stereotype.Service;

import java.text.*;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class TimeOfOrderService {
    private final OrdersRepository repository;
    private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH/mm");

    public Map<String, String> getInfo(int id) throws ParseException {
        Order order = repository.getOrderById(id);
        DateTime timeNow = new DateTime();
        DateTime timeOfEndOrder = new DateTime(dateFormat.parse(order.getDataOfEndOrder()));
        DateTime timeOfOrder = new DateTime(dateFormat.parse(order.getDataOfOrder()));

        int daysBeforeEnd = Days.daysBetween(timeNow, timeOfEndOrder).getDays();
        int hoursBeforeEnd = Hours.hoursBetween(timeNow, timeOfEndOrder).getHours() % 24;
        int minutesBeforeOrder = Minutes.minutesBetween(timeNow, timeOfEndOrder).getMinutes() % 60;

        return new HashMap<>() {{
            put("time", hoursBeforeEnd < 0 || daysBeforeEnd < 0 || minutesBeforeOrder < 0 ? "00:00:00(Time Out!)" : daysBeforeEnd + " Days " + hoursBeforeEnd + " Hours " + minutesBeforeOrder + " Minutes ");
            put("partOfWay", getPartOfWay(timeOfOrder, timeOfEndOrder, timeNow));
            put("status", getStatusOfOrder(timeOfOrder, timeOfEndOrder, timeNow));
        }};
    }


    private String getPartOfWay(DateTime timeOfOrder, DateTime timeOfEndOrder, DateTime timeNow) {
        Hours diff = Hours.hoursBetween(timeOfOrder, timeOfEndOrder);
        Hours diff1 = Hours.hoursBetween(timeOfOrder, timeNow);

        int differentBetweenStartAndEnd = diff.getHours();
        int differentBetweenStartAndNow = diff1.getHours();

        log.error("partOfWay{}", (14 * differentBetweenStartAndNow / differentBetweenStartAndEnd));
        return String.valueOf(Math.min(14 * differentBetweenStartAndNow / differentBetweenStartAndEnd, 14));
    }

    private String getStatusOfOrder(DateTime timeOfOrder, DateTime timeOfEndOrder, DateTime timeNow) {
        log.error("timeOfOrder{},timeNow{}", timeOfOrder, timeNow);

        int hoursBetweenStartAndNow = Hours.hoursBetween(timeOfOrder, timeNow).getHours();
        int hoursBetweenAllTheWay = Hours.hoursBetween(timeOfOrder, timeOfEndOrder).getHours();

        String status;
        if (hoursBetweenStartAndNow > hoursBetweenAllTheWay) status = Status.ARRIVED.getTitle();
        else if (hoursBetweenStartAndNow < 10) status = Status.PACKING.getTitle();
        else if (hoursBetweenStartAndNow < 24) status = Status.BEIJING.getTitle();
        else if (hoursBetweenStartAndNow < 54) status = Status.EUROPE.getTitle();
        else status = Status.NEARBY.getTitle();
        return status;
    }
}
