package sk.elct.parkingapp.parking;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {

    /**
     * Kapacita parkoviska. Pocet parkovacich miest spolu.
     */
    private int capacity;

    /**
     * Pristup k datam - zoznamu listkov.
     */
    private ListParkingLotDAO data;

    /**
     * Cena za hodinu parkovania v centoch.
     */
    public static final int PRICE_PER_HOUR = 100;

    /**
     * Pre kazdu firmu pocet hodin parkovania.
     */
    private Map<String, Integer> companyHistory;

    public ParkingLot(int capacity) {
        this.capacity = capacity;
        data = new ListParkingLotDAO();
        companyHistory = new HashMap<>();
    }

    /**
     * Prijazd vozidla
     *
     * @param ecv spz vozidla
     * @return referencia na objekt triedy ticket so zadanym casom.
     * alebo null ak sa nepodarilo zaparkovat napr. pre nedostatok miesta.
     */
    public Ticket checkIn(String ecv, String company) {
        if (isFull()) {
            return null;
        }
        if (data.getTicket(ecv) != null) {
            // ma zmysel tu vyhodit vlastnu vynimku
            return null;
        }
        Ticket ticket;
        if (company != null) {
            // vyrobim company ticket
            ticket = new CompanyTicket(ecv, company);
        } else {
            ticket = new Ticket(ecv);
        }
        data.add(ticket);
        return ticket;
    }

    /**
     * Zisti ci je parkovisko naplnene.
     *
     * @return true ak nie je volne miesto.
     */
    private boolean isFull() {
        return data.numberOfTickets() >= capacity;
    }

    public int freeLots() {
        return capacity - data.numberOfTickets();
    }

    /**
     * Urobi odjazd vozidla
     *
     * @param ecv spz vozidla
     * @return cena v centoch
     */
    public int checkOut(String ecv) {
        Ticket ticket = data.getTicket(ecv);
        return checkOut(ticket);
    }

    /**
     * Urobi odjazd vozidla
     *
     * @param ticket ticket
     * @return cena v centoch
     */
    public int checkOut(Ticket ticket) {
        if (ticket == null) {
            throw new TicketNotFoundException();
        }
        // vypocitat sumu
        int price = ticket.computePrice();
        // vymazat z listu/db/suboru
        data.delete(ticket);

        if (ticket instanceof CompanyTicket) {
            CompanyTicket t = (CompanyTicket) ticket;
            String companyName = t.getCompany();
            if (!companyHistory.containsKey(companyName)) {
                companyHistory.put(companyName, 1);
            } else {
                int value = companyHistory.get(companyName);
                companyHistory.put(companyName, 1 + value);
            }
        }

        return price;
    }

    /**
     * Vrati zoznam listkov zoradenych podla ECV
     */
    public List<Ticket> getAllTickets() {
        List<Ticket> allTickets = data.getAllTickets();
        Collections.sort(allTickets);
        return allTickets;
    }

    public List<Ticket> getAllTicketsSortedByTime() {
        List<Ticket> allTickets = data.getAllTickets();
        Collections.sort(allTickets, new PorovnavacTicketovPodlaCasu());
        return allTickets;
    }


}
