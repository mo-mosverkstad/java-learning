package Sorting;

public class SortingMain {

    public static void main(String[] args) {
        sortPersons();
        sortCars();
    }

    private static void sortPersons() {
        MyList<Person> list = new MyList<>();
        list.append(new Person("Bob", 30))
            .append(new Person("Alice", 10))
            .append(new Person("Charlie", 27))
            .append(new Person("David", 20))
            .append(new Person("Eve", 40));
        list.sort();
        System.out.println(list);
    }

    private static void sortCars() {
        MyList2<Car> list = new MyList2<>();
        list.append(new Car("Toyota", 2010))
            .append(new Car("BMW", 2015))
            .append(new Car("Mercedes", 2012))
            .append(new Car("Volkswagen", 2018))
            .append(new Car("Ford", 2017));
        list.sortList((car1, car2) -> {return car2.getProducedYear() - car1.getProducedYear();});
        System.out.println(list);
    }
}
