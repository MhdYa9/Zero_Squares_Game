package structure;

import java.util.Comparator;
import java.util.List;

public class Pair<X extends Comparable<X>, Y> implements Comparable<Pair<X, Y>> {
    public final X first;
    public final Y second;

    public Pair(X first, Y second) {
        this.first = first;
        this.second = second;
    }

    public X getFirst() {
        return first;
    }

    public Y getSecond() {
        return second;
    }

    public static <X extends Comparable<X>, Y extends Comparable<Y>> void sort(
            List<Pair<X, Y>> list,String sort_order ,boolean first_then_second){

        Comparator<Pair<X, Y>> comparator;

        // Define comparator based on criteria
        if (first_then_second) {
            comparator = Comparator.<Pair<X, Y>, X>comparing(p -> p.getFirst()).thenComparing(p -> p.getSecond());
        } else {
            comparator = Comparator.<Pair<X, Y>, Y>comparing(p -> p.getSecond()).thenComparing(p -> p.getFirst());
        }

        // Apply reverse order if needed
        if (sort_order.equalsIgnoreCase("DESC")) {
            comparator = comparator.reversed();
        }

        // Sort the list using the constructed comparator
        list.sort(comparator);
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return first.equals(pair.first) && second.equals(pair.second);
    }

    @Override
    public int hashCode() {
        return 31 * first.hashCode() + second.hashCode();
    }

    @Override
    public int compareTo(Pair<X,Y> other) {
        return this.first.compareTo(other.first);
    }
}
