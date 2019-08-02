package sk.elct.parkingapp.parking;

import java.io.Serializable;
import java.util.Objects;

/**
 * Reprezentuje jedno zaparkovanie.
 */
public class Ticket implements Serializable, Comparable<Ticket>{

    /**
     * SPZ auta
     */
    private String ecv;

    /**
     * Cas prijazdu auta na parkovisko.
     */
    private long arrivalTime;

    public Ticket(String ecv) {
        this.ecv = ecv;
        this.arrivalTime = System.currentTimeMillis();
    }

    public Ticket(String ecv, long arrivalTime) {
        this.ecv = ecv;
        this.arrivalTime = arrivalTime;
    }

    // tento konstruktor sa doplni automaticky
    // ak nemam napisany ziaden
    /*public Ticket() {

    }*/

    public String getEcv() {
        return ecv;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Vypocita cenu za parkovanie od prijazdu po teraz
     */
    public int computePrice() {
        // kazda zacata hodina
        int hours = getElapsedTime();
        return hours * ParkingLot.PRICE_PER_HOUR;
    }

    /**
     * Vrati pocet zacatych hodin od prijazdu.
     */
    public int getElapsedTime() {
        // cas medzi prijazdom a odjazdom v ms
        long elapsedTime =
                System.currentTimeMillis() - arrivalTime;

        // celociselne delenie, problem ak je presne 60 minut
        //long hours = 1 +(elapsedTime / (1000 * 60 * 60));
        //return (int)hours;

        double hours = elapsedTime / (1000.0 * 60 * 60);
        hours = Math.ceil(hours);
        return (int) hours;
    }

    @Override
    public String toString() {
        return ecv + "," + arrivalTime;
    }

    @Override
    public int compareTo(Ticket o) {
        return ecv.compareTo(o.ecv);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return arrivalTime == ticket.arrivalTime &&
                Objects.equals(ecv, ticket.ecv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ecv, arrivalTime);
    }
}
