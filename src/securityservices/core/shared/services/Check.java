package securityservices.core.shared.services;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
  Returns
  0 correcte
  -1 no acompleix amb el patro
  -2 el resultat no es correcte
 */
public class Check {

    //Pattern for date
    protected static Pattern patternDate = Pattern.compile("^(\\d{1,2})([-/])(\\d{1,2})([-/])(\\d{4})$");

    public static int checkExample(String request) {
        int valor1, valor2, result;
        String signo;
        Pattern pattern = Pattern.compile("(\\d+)([+-/*])(\\d+)\\=(\\d+)$");
        Matcher matcher = pattern.matcher(request);

        if (matcher.matches()) {
            //les 3 línees següents es mostren només a nivell didactic
            System.out.println("Primer valor numeric = " + matcher.group(1));
            System.out.println("Segon valor numeric = " + matcher.group(3));
            System.out.println("Resultat numeric = " + matcher.group(4));

            valor1 = Integer.valueOf(matcher.group(1));
            signo = matcher.group(2);
            valor2 = Integer.valueOf(matcher.group(3));
            result = Integer.valueOf(matcher.group(4));
            switch (signo) {
                case "+":
                    if (valor1 + valor2 == result) {
                        return 0;
                    }
                    break;
                case "-":
                    if (valor1 - valor2 == result) {
                        return 0;
                    }
                    break;
                case "*":
                    if (valor1 * valor2 == result) {
                        return 0;
                    }
                    break;
                case "/":
                    if (valor1 / valor2 == result) {
                        return 0;
                    }
                    break;
            }
        } else {
            return -1;
        }
        return -2;
    }

    public static int checkDNI(String dni) {
        Pattern pattern = Pattern.compile("^(\\d{7,8})([-_.,/]?)([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])$");
        Matcher matcher = pattern.matcher(dni);
        if (matcher.matches()) {
            //save letter
            String letter = matcher.group(3);
            //creating letter of dni on a variable
            String letters = "TRWAGMYFPDXBNJZSQVHLCKE";
            //Get numbers
            int i = Integer.parseInt(matcher.group(1));
            //Get the number of the letter
            i = i % 23;
            //Getting the exact letter with the numbers
            String finalLetter = letters.substring(i, i + 1);
            //Check if it's the same letter, doesn't matter if it's lower or upper case
            if (finalLetter.equalsIgnoreCase(letter)) {
                return 0;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    //Para minimizar codigo lo separo en funciones pequeñas, por que en el diffdate se repite
    //Funcion para saber si el año es bisiesto
    public static boolean isLeap(int year) {
        if ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0))) {
            return true;
        } else {
            return false;
        }
    }

    //Funcion para obtener los dias del mes
    public static int getMaxDayMonth(int month, int year) {
        //Creating an Array and adding the days of the month in order
        List<String> maxDays = new ArrayList<String>();
        maxDays.add("31");
        //en Febrero miramos si es bisiesto o no, si lo es ponemos 29
        if (isLeap(year) == true) {
            maxDays.add("29");
        } else {
            maxDays.add("28");
        }
        maxDays.add("31");
        maxDays.add("30");
        maxDays.add("31");
        maxDays.add("30");
        maxDays.add("31");
        maxDays.add("31");
        maxDays.add("30");
        maxDays.add("31");
        maxDays.add("30");
        maxDays.add("31");
        //Giving back the max days of the month
        return Integer.parseInt(maxDays.get(month - 1));
    }

    public static int checkDate(String date) {
        Matcher matcher = patternDate.matcher(date);
        //Check if pattern is correct
        if (matcher.matches()) {
            //Saving dates into variables
            int day = Integer.parseInt(matcher.group(1));
            int month = Integer.parseInt(matcher.group(3));
            int year = Integer.parseInt(matcher.group(5));
            //check if month is correct
            if (month > 0 && month <= 12) {
                //Getting the max day of the month
                int maxDayIndex = getMaxDayMonth(month, year);
                //Check if the day is between 0 and maxDays
                if (day <= maxDayIndex && day > 0) {
                    return 0;
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        }
        return -1;
    }

    public static boolean EasyDiffDates(String date1, String date2) {
        //Compruebo la fecha
        if (checkDate(date1) == 0 && checkDate(date2) == 0) {
            //Hago el match otra vez pero para poder coger bien los datos sin slipts
            Matcher matcherDate1 = patternDate.matcher(date1);
            Matcher matcherDate2 = patternDate.matcher(date2);
            if (matcherDate1.matches() && matcherDate2.matches()) {
                //Guardo los datos
                int day1 = Integer.parseInt(matcherDate1.group(1));
                int month1 = Integer.parseInt(matcherDate1.group(3));
                int year1 = Integer.parseInt(matcherDate1.group(5));

                int day2 = Integer.parseInt(matcherDate2.group(1));
                int month2 = Integer.parseInt(matcherDate2.group(3));
                int year2 = Integer.parseInt(matcherDate2.group(5));

                if (year1 > year2 || month1 > month2 && year1 == year2 || day1 > day2 && month1 == month2 && year1 == year2) {
                    return false;
                } 
                return true;
            }
        }
        return false;
    }

    public static int diffDates(String date1, String date2) {
        //Compruebo la fecha
        if (checkDate(date1) == 0 && checkDate(date2) == 0) {
            //Hago el match otra vez pero para poder coger bien los datos sin slipts
            Matcher matcherDate1 = patternDate.matcher(date1);
            Matcher matcherDate2 = patternDate.matcher(date2);
            if (matcherDate1.matches() && matcherDate2.matches()) {
                //Guardo los datos
                int day1 = Integer.parseInt(matcherDate1.group(1));
                int month1 = Integer.parseInt(matcherDate1.group(3));
                int year1 = Integer.parseInt(matcherDate1.group(5));

                int day2 = Integer.parseInt(matcherDate2.group(1));
                int month2 = Integer.parseInt(matcherDate2.group(3));
                int year2 = Integer.parseInt(matcherDate2.group(5));

                int minYear;
                int maxYear;
                int maxMonth;
                int minMonth;
                int maxDays;
                int minDays;

                int daysDiff = 0;

                //Ordenamos las fechas por si no pasa la pequeña delante
                if (year1 > year2 || month1 > month2 && year1 == year2) {
                    maxYear = year1;
                    minYear = year2;
                    maxMonth = month1;
                    minMonth = month2;
                    maxDays = day1;
                    minDays = day2;
                } else {
                    maxYear = year2;
                    minYear = year1;
                    maxMonth = month2;
                    minMonth = month1;
                    maxDays = day2;
                    minDays = day1;
                }

                //A partir de aqui vamos con un for y añadimos dias
                for (int y = minYear; y <= maxYear; y++) {
                    for (int m = 1; m <= 12; m++) {
                        if (m <= minMonth && y == minYear) {
                            if (m == minMonth) {
                                daysDiff += getMaxDayMonth(m, y);
                                daysDiff -= minDays;
                                if (m == maxMonth && y == maxYear) {
                                    m = 13;
                                }
                            }
                        } else if (m == maxMonth && y == maxYear) {
                            daysDiff += maxDays;
                            m = 13;
                        } else {
                            daysDiff += getMaxDayMonth(m, y);
                        }
                    }
                }
                return daysDiff;
            }
        }
        return -1;
    }

    public static int checkEmail(String email) {
        //Creating pattern does'nt matter if its caps or lows cases
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        //Check if it's correct
        if (matcher.matches()) {
            return 0;
        }
        return -1;
    }

}
