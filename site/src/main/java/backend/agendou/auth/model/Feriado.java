package backend.agendou.auth.model;

import java.util.List;

public class Feriado {
    private String name;
    private String description;
    private Country country;
    private Date date;
    private List<String> type;
    private String primary_type;
    private String canonical_url;
    private String urlid;
    private String locations;
    private String states;

    public static class Country {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Date {
        private String iso;
        private Datetime datetime;

        public String getIso() {
            return iso;
        }

        public void setIso(String iso) {
            this.iso = iso;
        }

        public Datetime getDatetime() {
            return datetime;
        }

        public void setDatetime(Datetime datetime) {
            this.datetime = datetime;
        }
    }

    public static class Datetime {
        private int year;
        private int month;
        private int day;

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public String getPrimary_type() {
        return primary_type;
    }

    public void setPrimary_type(String primary_type) {
        this.primary_type = primary_type;
    }

    public String getCanonical_url() {
        return canonical_url;
    }

    public void setCanonical_url(String canonical_url) {
        this.canonical_url = canonical_url;
    }

    public String getUrlid() {
        return urlid;
    }

    public void setUrlid(String urlid) {
        this.urlid = urlid;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    @Override
    public String toString() {
        return "Feriado{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", country=" + country +
                ", date=" + date +
                ", type=" + type +
                ", primary_type='" + primary_type + '\'' +
                ", canonical_url='" + canonical_url + '\'' +
                ", urlid='" + urlid + '\'' +
                ", locations='" + locations + '\'' +
                ", states='" + states + '\'' +
                '}';
    }
}
