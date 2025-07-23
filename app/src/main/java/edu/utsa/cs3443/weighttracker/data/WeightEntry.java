package edu.utsa.cs3443.weighttracker.data;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class WeightEntry {

        private LocalDate date;
        private double weight;

        public WeightEntry(LocalDate date, double weight) {
            this.date = date;
            this.weight = weight;
        }
        public LocalDate getDate()   { return date; }
        public double    getWeight() { return weight; }
        public void      setDate(LocalDate date)   { this.date = date; }
        public void      setWeight(double weight)  { this.weight = weight; }

        public String formattedDate() {
            return date.format(DateTimeFormatter.ofPattern("MM/d/yyyy"));
        }
    }

