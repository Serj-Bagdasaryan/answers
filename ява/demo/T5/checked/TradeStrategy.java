package com.example.demo.T5.checked;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

class SeriesAnalyser {
    private final TrendFactory trendFactory;
    private final DataValidator validator;

    public SeriesAnalyser() {
        this.trendFactory = new TrendFactory();
        this.validator = new DataValidator();
    }

    public List<String> processingInputLines(List<String> inputLines) {
        if (inputLines == null) {
            List<String> none = new ArrayList<>();
            none.add("none");
            return none;
        }

        List<Integer> prices = new ArrayList<>(inputLines.size());
        for (String line : inputLines) {
            Integer price = validator.parsePriceIfValid(line);
            if (price != null) {
                prices.add(price);
            }
        }

        if (prices.isEmpty()) {
            List<String> none = new ArrayList<>();
            none.add("none");
            return none;
        }

        int n = prices.size();
        List<String> output = new ArrayList<>();

        final int MIN_UP_POINTS = 3;
        final int MIN_DOWN_POINTS = 4;

        int direction = 0;
        int start = 0;

        for (int i = 1; i < n; i++) {
            int prev = prices.get(i - 1);
            int cur = prices.get(i);
            if (cur > prev) {
                if (direction == 1) {
                    continue;
                } else {
                    if (direction == -1) {
                        int runLength = (i - start);
                        if (runLength >= MIN_DOWN_POINTS) {
                            output.add(trendFactory.createDown(start, i - 1, prices));
                        }
                    }
                    start = i - 1;
                    direction = 1;
                }
            } else if (cur < prev) {
                if (direction == -1) {
                    continue;
                } else {
                    if (direction == 1) {
                        int runLength = (i - start);
                        if (runLength >= MIN_UP_POINTS) {
                            output.add(trendFactory.createUp(start, i - 1, prices));
                        }
                    }
                    start = i - 1;
                    direction = -1;
                }
            } else {
                if (direction == 1) {
                    int runLength = (i - start);
                    if (runLength >= MIN_UP_POINTS) {
                        output.add(trendFactory.createUp(start, i - 1, prices));
                    }
                } else if (direction == -1) {
                    int runLength = (i - start);
                    if (runLength >= MIN_DOWN_POINTS) {
                        output.add(trendFactory.createDown(start, i - 1, prices));
                    }
                }
                direction = 0;
                start = i;
            }
        }

        if (direction == 1) {
            int runLength = (n - start);
            if (runLength >= MIN_UP_POINTS) {
                output.add(trendFactory.createUp(start, n - 1, prices));
            }
        } else if (direction == -1) {
            int runLength = (n - start);
            if (runLength >= MIN_DOWN_POINTS) {
                output.add(trendFactory.createDown(start, n - 1, prices));
            }
        }

        if (output.isEmpty()) {
            output.add("none");
        }
        return output;
    }

    static class TrendFactory {
        String createUp(int startIdx, int endIdx, List<Integer> prices) {
            int n = endIdx - startIdx + 1; // количество точек
            int startPrice = prices.get(startIdx);
            int endPrice = prices.get(endIdx);
            int diff = endPrice - startPrice;
            return String.format("UP %d %d", n, diff);
        }

        String createDown(int startIdx, int endIdx, List<Integer> prices) {
            int n = endIdx - startIdx + 1;
            int startPrice = prices.get(startIdx);
            int endPrice = prices.get(endIdx);
            int percent = (int) ((long) (startPrice - endPrice) * 100L / startPrice);
            return String.format("DOWN %d %d", n, percent);
        }
    }

    static class DataValidator {
        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        Integer parsePriceIfValid(String line) {
            if (line == null) {
                return null;
            }
            String[] parts = line.split(";", -1);
            if (parts.length != 2) {
                return null;
            }
            String datePart = parts[0].trim();
            String pricePart = parts[1].trim();
            if (datePart.isEmpty() || pricePart.isEmpty()) {
                return null;
            }

            try {
                LocalDate.parse(datePart, formatter);
            } catch (DateTimeParseException e) {
                return null;
            }

            int price;
            try {
                price = Integer.parseInt(pricePart);
            } catch (NumberFormatException e) {
                return null;
            }
            if (price < 1 || price > 9999) {
                return null;
            }
            return price;
        }
    }
}



public class TradeStrategy {
    public static void main(String[] args) {
        SeriesAnalyser sa = new SeriesAnalyser();
        List<String> input = List.of(
                /*"01-01-2024;100",
                "02-01-2024;110",
                "bad-line-no-semi",       // невалидная -> игнорируется
                "03-01-2024;120",
                "32-01-2024;130",         // неверная дата -> игнорируется
                "04-01-2024;130",
                "05-01-2024;125",
                "06-01-2024;120",
                "07-01-2024;115",
                "08-01-2024;110a",        // нечисловая цена -> игнорируется
                "09-01-2024;105"*/

                /*"01-11-2024;300",
                "02-11-2024;290",
                "03-11-2024;280",
                "04-11-2024;270",
                "05-11-2024;260",
                "06-11-2024;invalid",
                "07-11-2024;250",
                "08-11-2024;240",
                "09-11-2024;230",
                "10-11-2024;220",
                "11-11-2024;210",
                "12-11-2024;215",
                "13-11-2024;220",
                "14-11-2024;10000"*/

                /*"01-10-2024;100",
                "02-10-2024;105",
                "03-10-2024;110",
                "04-10-2024;115",
                "05-10-2024;120",
                "06-10-2024;invalid",
                "07-10-2024;118",
                "08-10-2024;116",
                "09-10-2024;114",
                "10-10-2024;112",
                "11-10-2024;110",
                "12-10-2024;115",
                "13-10-2024;120",
                "14-10-2024;10"*/

                "01-07-2024;300",
                "02-07-2024;290",
                "03-07-2024;280",
                "04-07-2024;270",
                "05-07-2024;260",
                "06-07-2024;250",
                "07-07-2024;240",
                "08-07-2024;230"
        );

        List<String> res = sa.processingInputLines(input);
        for (String r : res) {
            System.out.println(r);
        }
    }
}
