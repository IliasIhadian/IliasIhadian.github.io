package com.ilias.bored.services;

import com.ilias.bored.models.LocationStats;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class CoronaVirusDataService {

    private static String VIRUS_DATA = "https://raw.githubusercontent.com/CSSEGISandData/"
            + "COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/"
            + "time_series_covid19_confirmed_global.csv";

    List<LocationStats> allStats = new ArrayList<>();

    @SuppressFBWarnings("EI_EXPOSE_REP")
    public List<LocationStats> getAllStats() {
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA)).build();
        HttpResponse<String> httpResponse = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        StringReader csvBodyReader = new StringReader(httpResponse.body());

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(csvBodyReader);
        for (CSVRecord record : records) {
            LocationStats localstats = new LocationStats();
            localstats.setState(record.get("Province/State"));
            localstats.setCountry(record.get("Country/Region"));
            int latestTotalCases = Integer.parseInt(record.get(record.size() - 1));
            int prevTotalCases = Integer.parseInt(record.get(record.size() - 2));
            localstats.setLatestTotalCases(latestTotalCases); // latestdaycases
            localstats.setDiffFromPrevDay(latestTotalCases - prevTotalCases); //for prevdaycases
            newStats.add(localstats);

        }
        this.allStats = newStats;

    }
}
