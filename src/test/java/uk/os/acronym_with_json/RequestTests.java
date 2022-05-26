//This whole file will need explaining.
package uk.os.acronym_with_json;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RequestTests {

    @LocalServerPort
    private int port;

    @Autowired
    //Not really sure what this is doing.
    private TestRestTemplate restTemplate;


    @Test
    public void testNullAcronymRequestParameterGetsAllAcronyms() throws Exception{
        assertThat(restTemplate.getForObject("http://localhost:" + port + "?",
                String.class)).isEqualTo("OS: Ordnance Survey CRS: Coordinate Reference System " +
                "PSGA: Public Sector Geospatial Agreement BNG: British National Grid NGD: National Geographic Database ");
    }

    //Test works I believe, but way ive displayed data means it moves, probably need to mock the object im
    //hoping to turn it into to get it working.
    //Ended up using loop to display in order. Now works. Is it optimal? That is the question.
    @Test
    public void testAllRequestGetsAllAcronyms() throws Exception{
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/all",
                String.class)).isEqualTo("OS: Ordnance Survey CRS: Coordinate Reference System " +
                "PSGA: Public Sector Geospatial Agreement BNG: British National Grid NGD: National Geographic Database ");
    }

    @Test
    public void testValidAcronymRequestParameterGetsAcronymAndValue() throws Exception{
        assertThat(restTemplate.getForObject("http://localhost:" + port + "?acronym=OS",
                String.class)).isEqualTo("OS: Ordnance Survey");
    }

    @Test
    public void wrongAcronymRequestParameterCausesErrorMessage() throws Exception{
        assertThat(restTemplate.getForObject("http://localhost:" + port + "?acronym=wrong",
                String.class)).isEqualTo("This acronym doesn't exist");
    }
}


