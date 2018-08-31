package com.raghav.thoughtworkschallenge;

import java.util.Comparator;
import java.util.UUID;

public class Beer {
    /*
    Abv:  The alcoholic content by volume with 0 being no alcohol and 1 being pure alcohol

    Ibu: International bittering units, which describe how bitter a drink is.

    Id: Unique ID

    Name: Name of the beer.

    Style: Beer style (lager, ale, IPA, etc.)

    Ounces: Size of beer in ounces.*/

    private String Abv;
    private String Ibu;
    private String Name;
    private String Style;
    private Double Ounces;
    private UUID Id;

    public Beer() {
    }

    public Beer(String abv, String ibu, String name, String style, Double ounces, UUID id) {
        Abv = abv;
        Ibu = ibu;
        Name = name;
        Style = style;
        Ounces = ounces;
        Id = id;
    }

    public String getAbv() {
        return Abv;
    }

    public void setAbv(String abv) {
        Abv = abv;
    }

    public String getIbu() {
        return Ibu;
    }

    public void setIbu(String ibu) {
        Ibu = ibu;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStyle() {
        return Style;
    }

    public void setStyle(String style) {
        Style = style;
    }

    public Double getOunces() {
        return Ounces;
    }

    public void setOunces(Double ounces) {
        Ounces = ounces;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public static Comparator<Beer> AbvComparator = new Comparator<Beer>() {

        @Override
        public int compare(Beer beer1, Beer beer2) {

            int compare = 0;
            if(GlobalClass.isAlphabeticallySort()) {
                String name1 = beer1.getName().toUpperCase();
                String name2 = beer2.getName().toUpperCase();
                compare = name1.compareTo(name2);
            }
            else {
                String Abv1 = beer1.getAbv().toUpperCase();
                String Abv2 = beer2.getAbv().toUpperCase();

                //ascending order
                if (GlobalClass.getSortingOrder().equalsIgnoreCase("Ascending"))
                    compare = Abv1.compareTo(Abv2);

                //descending order
                if (GlobalClass.getSortingOrder().equalsIgnoreCase("Descending"))
                    compare = Abv2.compareTo(Abv1);
            }
            return compare;
        }
    };
}
