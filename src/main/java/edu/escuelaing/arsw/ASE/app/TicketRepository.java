package edu.escuelaing.arsw.ASE.app;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class TicketRepository {
    @Autowired
    private StringRedisTemplate template;
    // inject the template as ListOperations
    @Resource(name = "stringRedisTemplate")
    private ListOperations<String, String> listTickets;
    private int ticketnumber;
    public TicketRepository() {
    }
    public synchronized Integer getTicket() {
        Integer a = ticketnumber++;
        listTickets.leftPush("ticketStore",a.toString());
        return a;
    }
    public boolean checkTicket(String ticket) {
        Long isValid = listTickets.getOperations().boundListOps("ticketStore").remove(0, ticket);
        return (isValid > 0l);
    }
    private void eviction() {
        // Delete tickets after timout or include this functionality in checkticket
    }
}