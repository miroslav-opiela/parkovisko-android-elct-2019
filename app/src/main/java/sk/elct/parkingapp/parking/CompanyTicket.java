package sk.elct.parkingapp.parking;

import java.io.Serializable;
import java.util.Objects;

public class CompanyTicket extends Ticket implements Serializable {

    private String company;

    public CompanyTicket(String ecv, String company) {
        super(ecv);
        this.company = company;
    }

    public CompanyTicket(String ecv, long arrivalTime,
                         String company) {
        super(ecv, arrivalTime);
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    @Override
    public String toString() {
        return super.toString() + "," + company;
    }

    @Override
    public int computePrice() {
        int hours = getElapsedTime();
        if (hours < 5) {
            return hours * ParkingLot.PRICE_PER_HOUR;
        } else {
            // fixna suma pre dlhodobe parkovanie
            // idealne mat tiez v konstante
            return 10;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CompanyTicket that = (CompanyTicket) o;
        return Objects.equals(company, that.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), company);
    }
}
