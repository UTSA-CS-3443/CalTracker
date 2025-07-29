package edu.utsa.cs3443.caltracker.model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
 public class WeightRepository {
        private final List<WeightEntry> store = new ArrayList<>();

        //TODO: make this.store = null, removing hardcode.
        public WeightRepository() {
            // Fake entries for testing
            store.add(new WeightEntry(LocalDate.now().minusDays(10), 155));
            store.add(new WeightEntry(LocalDate.now().minusDays(7), 156.2));
            store.add(new WeightEntry(LocalDate.now().minusDays(4), 157.6));
            store.add(new WeightEntry(LocalDate.now().minusDays(1), 158));
        }

        public List<WeightEntry> getAllWeights() {
            return new ArrayList<>(store);
        }
        public void addWeight(WeightEntry e) {
            store.add(e);
        }
        public void deleteWeight(WeightEntry e) {
            store.remove(e);
        }
        public void updateWeight(WeightEntry e, double w) {
            e.setWeight(w);
        }
        public void updateDate(WeightEntry e, LocalDate d) {
            e.setDate(d);
        }
    }

