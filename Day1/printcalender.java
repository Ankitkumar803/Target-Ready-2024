public class CalendarPrinter {

    public static void main(String[] args) {
        printCalendar(8, 2018);
    }

    public static void printCalendar(int month, int year) {
        if (isValidInput(month, year)) {
            printHeader();
            printMonth(month, year);
        } else {
            System.out.println("Invalid input. Please provide a valid month (1-12) and year.");
        }
    }

    private static boolean isValidInput(int month, int year) {
        return month >= 1 && month <= 12;
    }

    private static void printHeader() {
        System.out.println("Su Mo Tu We Th Fr Sa");
    }

    private static void printMonth(int month, int year) {
        int daysInMonth = getDaysInMonth(month, year);
        int firstDayOfWeek = getFirstDayOfWeek(month, year);

        for (int i = 0; i < firstDayOfWeek; i++) {
            System.out.print("   ");
        }


        for (int day = 1; day <= daysInMonth; day++) {
            System.out.printf("%2d ", day);

            if ((day + firstDayOfWeek) % 7 == 0 || day == daysInMonth) {
                System.out.println();
            }
        }
    }

    private static int getDaysInMonth(int month, int year) {
        int[] daysInMonthArray = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (month == 2 && isLeapYear(year)) {
            return 29;
        }
        return daysInMonthArray[month];
    }

    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    private static int getFirstDayOfWeek(int month, int year) {

        if (month < 3) {
            month += 12;
            year--;
        }
        int k = year % 100;
        int j = year / 100;
        int dayOfWeek = (1 + 13 * (month + 1) / 5 + k + k / 4 + j / 4 + 5 * j) % 7;

        return (dayOfWeek + 5) % 7;
    }
}
