package sk.elct.parkingapp.parking;

import java.util.Comparator;

public class PorovnavacTicketovPodlaCasu
        implements Comparator<Ticket> {
    @Override
    public int compare(Ticket t1, Ticket t2) {
        if (t1.getArrivalTime() < t2.getArrivalTime()) {
            return -1;
        }
        if (t1.getArrivalTime() > t2.getArrivalTime()) {
            return 1;
        }

        return 0;
    }
}
