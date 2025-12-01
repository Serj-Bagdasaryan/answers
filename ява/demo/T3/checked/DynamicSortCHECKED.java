package com.example.demo.T3.checked;

import java.time.LocalDate;
import java.util.*;


class Product {
    private String name;
    private int price;
    private int stock;
    private String category;
    private LocalDate releaseDate;

    public Product(String name, int price, int stock, String category, LocalDate releaseDate) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.releaseDate = releaseDate;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getStock() { return stock; }
    public String getCategory() { return category; }
    public LocalDate getReleaseDate() { return releaseDate; }


    @Override
    public String toString() {
        return String.join(";", name, Integer.toString(price), Integer.toString(stock), category, releaseDate.toString());
    }
}


class QueryableExtensions {
    public static Comparator<Product> createComparator(String fieldName, boolean ascending) {
        Comparator<Product> primary;
        switch (fieldName.toLowerCase().trim()) {
            case "category":
                primary = Comparator.comparing(Product::getCategory);
                break;
            case "name":
                primary = Comparator.comparing(Product::getName);
                break;
            case "stock":
                primary = Comparator.comparingInt(Product::getStock);
                break;
            case "price":
                primary = Comparator.comparingInt(Product::getPrice);
                break;
            case "releasedate":
                primary = Comparator.comparing(Product::getReleaseDate);
                break;
            default:
                primary = Comparator.comparing(Product::getName);
                break;
        }
        if (!ascending) {
            primary = primary.reversed();
        }
        return primary.thenComparing(Product::getName);
    }
}


class ProcessingProductSorting{
    public List<String> processingInputLines(List<String> inputLines) {
        String[] firstLine = inputLines.get(0).trim().split(" ");
        Comparator<Product> comparator = QueryableExtensions.createComparator(firstLine[0], firstLine[1].equals("asc"));
        List<String> result =  inputLines.stream().skip(1).map(o -> {
            String[] string = o.trim(). split(";");
            int first = 0, second = 0;
            try {
                first = Integer.parseInt(string[1]);
            } catch (Exception e) {
            }
            try {
                second = Integer.parseInt(string[2]);
            } catch (Exception e) {
            }
            Product pr = new Product(string[0],
                    first,
                    second,
                    string[3],
                    LocalDate.parse(string[4].trim())
            );
            return pr;
        }).sorted(comparator).map(Objects::toString).toList();
        return result;
    }
}

public class DynamicSortCHECKED {
    public static void main(String[] args) {
        List<String> strings = List.of(/*"Price desc",
                "MidRange;500000;5000;Standard;2024-05-05",
                "Economy;100;7500;Basic;2024-09-09",
                "Expensive;750000;100;Luxury;2024-11-11",
                "Cheap;10;9000;Basic;2024-02-02",
                "Premium;999999;10;Luxury;2024-07-04",
                "Budget;1;9999;Basic;2024-03-03"*/

        /*"Stock desc",
                "Monitor;249.99;15;Electronics;2023-02-20",
                "Keyboard;49.99;50;Electronics;2023-04-05",
                "Mouse;29.99;75;Electronics;2023-01-30"*/

                "Price asc",
                "Laptop;999;10;Electronics;2023-01-15",
                "Phone;699;25;Electronics;2023-03-10",
                "Book;19;100;Books;2022-11-05");
        ProcessingProductSorting pPS = new ProcessingProductSorting();
        strings = pPS.processingInputLines(strings);
        strings.forEach(System.out::println);
    }
}