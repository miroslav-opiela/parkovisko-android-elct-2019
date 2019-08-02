package sk.elct.parkingapp.parking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Trieda poskytuje pristup k datam.
 */
public class ListParkingLotDAO implements FileBasedStorage {

    private File file;
    /**
     * Zoznam listkov pre zaparkovane auta.
     */
    private List<Ticket> tickets;

    public ListParkingLotDAO(File file) {
        this.file = file;
        tickets = new ArrayList<>();
        load();
    }

    public void add(Ticket ticket) {
        tickets.add(ticket);
        save();
    }

    public int numberOfTickets() {
        return tickets.size();
    }

    public void delete(Ticket ticket) {
        tickets.remove(ticket);
        save();
    }

    /*public Ticket getTicket(String ecv) {
        for (int i = 0; i < tickets.size(); i++) {
            Ticket ticketFromList = tickets.get(i);
            if (ticketFromList.getEcv().equals(ecv)) {
                return ticketFromList;
            }
        }
        return null;
    }*/

    public Ticket getTicket(String ecv) {
        for (Ticket ticketFromList : tickets) {
            if (ticketFromList.getEcv().equals(ecv)) {
                return ticketFromList;
            }
        }
        return null;
    }

    public void load() {
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Ticket ticket = parseTicket(line);
                tickets.add(ticket);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        // try-catch with resources
        try (PrintWriter pw = new PrintWriter(file)) {
            for (Ticket ticketFromList : tickets) {
                pw.println(ticketFromList.toString());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }

    }

    /**
     * @param s vo formate ecv,cas
     */
    private Ticket parseTicket(String s) {
        // da sa urobit s.split(",")
        Scanner sc = new Scanner(s);
        sc.useDelimiter(",");
        String ecv = sc.next();
        long time = sc.nextLong();
        if (sc.hasNext()) {
            String company = sc.next();
            return new CompanyTicket(ecv, time, company);
        } else {
            return new Ticket(ecv, time);
        }
    }


    public List<Ticket> getAllTickets() {
        // nechcem dat referenciu na zoznam, ale vyrobit kopiu
        return new ArrayList<>(tickets);
    }
}
