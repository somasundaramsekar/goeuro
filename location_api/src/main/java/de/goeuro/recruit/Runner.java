package de.goeuro.recruit;

import de.goeuro.recruit.application.LocationApplicationService;
import de.goeuro.recruit.read.model.Location;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by somasundaram.s on 24-06-2016.
 */
@Component
public class Runner implements CommandLineRunner {

    private final ApplicationContext applicationContext;
    private final CSVFormat csvFormat;

    @Autowired
    public Runner(ApplicationContext applicationContext) throws IOException{
        this.applicationContext = applicationContext;
        csvFormat = CSVFormat.RFC4180.withDelimiter(',');
    }

    @Override
    public void run(String... strings) throws Exception {

        String cityName = Stream.of(strings).findFirst().get();

        List<Location> locations = applicationContext.getBean(LocationApplicationService.class).locationInfoByCityName(cityName);

        try(CSVPrinter csvPrinter = new CSVPrinter(new FileWriter("output.csv"), csvFormat.withDelimiter(','))) {
                locations.forEach(
                        location -> {
                            try {
                                csvPrinter.printRecord(
                                        Arrays.asList(location.get_id(),
                                                location.getName(),
                                                location.getType(),
                                                location.getCoordinate().getLatitude(),
                                                location.getCoordinate().getLongitude()
                                        )
                                );
                            }catch (IOException e) {
                                throw new RuntimeException("Error writing to a file");
                            }

                        }
                );
        }

    }
}
